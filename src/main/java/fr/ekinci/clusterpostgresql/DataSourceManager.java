package fr.ekinci.clusterpostgresql;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import fr.ekinci.corbawrapper.CORBAClient;
import fr.ekinci.distributedpool.corba.SqlConnectionLicenseService;
import fr.ekinci.distributedpool.corba.SqlConnectionLicenseServiceHelper;
import fr.ekinci.distributedpool.corba.SqlNodeInformation;
import fr.ekinci.distributedpool.multicast.checkalive.ClientMulticastCheckAlive;
import fr.ekinci.distributedpool.multicast.discovery.ClientMulticastDiscovery;
import fr.ekinci.distributedpool.multicast.discovery.ServerMulticastDiscoveryResponse;
import fr.ekinci.abstractdatasource.AbstractDataSource;
import fr.ekinci.abstractdatasource.DBCPDataSource;
import fr.ekinci.multicastwrapper.MulticastActionMessage;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * DataSourceManager - Manage Master (for write) and Slave (for read) connections
 * Static class - Singleton pattern is applied
 * 
 * Advancement : 90%
 * Tests : Need database connection, complete version of tests in another project : mock-postgresql-rendez_vous
 * 
 * @author Gokan EKINCI
 */ 
public abstract class DataSourceManager {
    private final static Logger LOG = Logger.getLogger(DataSourceManager.class);
    
    
    /* Utility attributes for runInitializeDataSources() */
    private final static Lock dataSourcesInitializationLock = new ReentrantLock();
    private final static Lock testingDataSourcesLock = new ReentrantLock();
    private final static int MULTICAST_DISCOVERY_TIME_OUT = 2_000; // unit time is millisecond
    private final static int DATASOURCE_VALIDATION_TIME_OUT = 3; // unit time is second
    
    
    /* When server check if current client is alive */
    private volatile static ClientMulticastCheckAlive cmca = null; // Contains listener
    
    
    /* Configuration for connection */
    private volatile static SqlNodeInformation[] sqlNodeInformations;  
    
    
    // Current Master Node
    private volatile static DataSource masterDataSource;
    
    
    // All attributes which concerns current Slave Nodes
    private final static Lock slaveLock = new ReentrantLock();
    private volatile static List<DataSource> slaveDataSources;
    private volatile static int maxSlaveIndex; // Number of slaves minus one 
    private volatile static int currentSlaveIndex;
    
    
    
    /**
     * Initialize (or re-initialize) datasources
     * This method is called at SQLCluster's first instanciation, and you have to use it when you have an SQLException
     * 1. For beginning, this method is first checking if only one thread is calling it 
     * 2. Then it checks if all datasources are valid (double check) because a thread may quickly access to this method after the first caller
     * If step 1. and 2. are ok, initialization of datasources is accepted but a license checking method called "isTotalNumberOfSqlConnectionsValid()"
     * is launched first in order to know if number of connection is not exceeded  
     * 
     * @return true if this method has runned fine, false if this method is already launched by another thread.
     * @throws SQLException 
    */
    public static boolean runInitializeDataSources() throws SQLException {
        boolean hasRunned = false;        
        
        if(dataSourcesInitializationLock.tryLock()){
            try {
                // Begin critic section
                if(!areAllCurrentDataSourcesValid()){
                    LOG.info("DataSourceManager.runInitializeDataSources() has started"); 
                    
                    initClientMulticastCheckAliveIfItsNull();
                    
                    sqlNodeInformations = checkLicenseForSqlNodeInformation(); // May launch exception
                    if(sqlNodeInformations != null){
                        cleanOldDataSources();
                        initializeNewDataSources(sqlNodeInformations);
                    }
                    
                    LOG.info("DataSourceManager.runInitializeDataSources() is now finished");
                    hasRunned = true;
                }
                // End of critic section
            } finally {
                dataSourcesInitializationLock.unlock();
            }
        } // end of tryLock 
        
        return hasRunned;
    }
    
    
    
    /**
     * A checking method in order to know if all current datasources are valid
     * 
     * This method contains a lock in order to be sure if all current datasources are
     * valid before to reinitialize all of them
     * 
     * @return true if all datasources are valid, an exception is launched otherwise
     */
    public static boolean areAllCurrentDataSourcesValid() {
        testingDataSourcesLock.lock();
        try {
            
            // Test Master DataSource
            if(masterDataSource == null) {
                return false;
            } else if (!isDataSourceValid(masterDataSource)){
                return false;
            }
            
            // Test Slave DataSources
            if(slaveDataSources == null){
                return false;
            } else {
                for(DataSource slaveDataSource : slaveDataSources){
                    if(!isDataSourceValid(slaveDataSource)){
                        return false;
                    }
                }
            }
            
            return true;
        } finally {
            testingDataSourcesLock.unlock();
        }
    }
    
    
    
    /**
     * A method for checking one datasource validity
     * 
     * @return true if datasource is valid, false otherwise
     */
    private static boolean isDataSourceValid(final DataSource datasource){
        boolean isDataSourceValid = false;
        try (Connection connection = datasource.getConnection()) {
            isDataSourceValid = connection.isValid(DATASOURCE_VALIDATION_TIME_OUT);
        } catch (SQLException | NullPointerException e) {
            LOG.info("DataSourceManager.areAllDataSourcesValid : one DataSource is not operational", e);
            isDataSourceValid = false;
        }
        return isDataSourceValid;
    }

    

    /**
     * A license checking method
     * Cannot initialize new connections if maximum number in the SQLCluster is reached
     * 
     * @return true if the maximum number of connection in the sql cluster is not reached, false otherwise
     * @throws MasterSQLNodeNotFoundException 
     * @throws LicenseCheckingInternalErrorException 
     * @throws MaximumNumberOfConnectionsInSQLClusterIsReachedException 
     */
    private static SqlNodeInformation[] checkLicenseForSqlNodeInformation() 
        throws MasterSQLNodeNotFoundException, 
        LicenseCheckingInternalErrorException, 
        MaximumNumberOfConnectionsInSQLClusterIsReachedException 
    {
        
        // Discover Master Node using Multicast
        ServerMulticastDiscoveryResponse mdsr = null;
        try {
            ClientMulticastDiscovery cmd = new ClientMulticastDiscovery();
            cmd.sendMessage(new MulticastActionMessage(), MULTICAST_DISCOVERY_TIME_OUT);
            mdsr = cmd.getMulticastDiscoveryServerResponse();
        } catch (IOException e) {
            LOG.error("A ClientMulticastDiscovery error happened", e);
            throw new LicenseCheckingInternalErrorException(e);
        }

        if(mdsr == null){
            throw new MasterSQLNodeNotFoundException();
        }
        
        // Get connection information about SQLNodes with CORBA service
        SqlNodeInformation[] sqlNodeInformations = null;        
        try {
            LOG.info("Launching CORBA with host : " + mdsr.getServiceHost() + " and port : " + mdsr.getServicePort() + " ...");
            
            /* RMI (Old) 
            Registry registry = LocateRegistry.getRegistry(mdsr.getServiceHost(), mdsr.getServicePort());
            SqlConnectionLicenseService stub = (SqlConnectionLicenseService) registry.lookup(mdsr.getServiceName());
            sqlNodeInformations = stub.getConnectionInformation(
                System.getProperty("pw_sql_username"), 
                System.getProperty("pw_sql_password")        
            );
            */
            
            CORBAClient corba = new CORBAClient(mdsr.getServiceHost(), mdsr.getServicePort());
            SqlConnectionLicenseService service = 
                corba.<SqlConnectionLicenseService, SqlConnectionLicenseServiceHelper>
                lookup(mdsr.getServiceName(), SqlConnectionLicenseServiceHelper.class);
            sqlNodeInformations = service.getConnectionInformation(
                System.getProperty("pw_sql_username"), 
                System.getProperty("pw_sql_password")
            );
            
            if(sqlNodeInformations == null){
                throw new MaximumNumberOfConnectionsInSQLClusterIsReachedException();
            }
            
        } catch (InvalidName 
                | SecurityException 
                | NoSuchMethodException 
                | InvocationTargetException 
                | IllegalAccessException 
                | NotFound 
                | CannotProceed 
                | org.omg.CosNaming.NamingContextPackage.InvalidName e) {
            LOG.error("A CORBAClient error happened", e);
            throw new LicenseCheckingInternalErrorException(e);          
        } 

        return sqlNodeInformations;
    }
    
    
    
    /**
     * Close all datasources
     * 
     * @throws SQLException
     */
    public static void closeDataSources() throws SQLException{
        cleanOldDataSources();
    }
    
    
    
    /**
     * Clean (or close) previous Master and Slave DataSources
     * @throws SQLException
     */
    private static void cleanOldDataSources() throws SQLException {
        // Clean Master 
        if(masterDataSource != null){
            ((AbstractDataSource) masterDataSource).close();
        }
        
        // Clean Slaves
        if(slaveDataSources != null){
            for(DataSource slaveDataSource : slaveDataSources){
                ((AbstractDataSource) slaveDataSource).close();
            }
        }
    }
    
    
    
    /**
     * Initialize new Master and Slave DataSources 
     * 
     * @param clusterIpAddresses
     * @throws MasterSQLNodeNotFoundException
     */
    private static void initializeNewDataSources(final SqlNodeInformation[] sqlNodeInformations) throws MasterSQLNodeNotFoundException {
        slaveDataSources = new ArrayList<DataSource>();
        for(SqlNodeInformation sqlNodeInformation : sqlNodeInformations){
            try {
                AbstractDataSource ds = new DBCPDataSource(
                    sqlNodeInformation.nodeIp,
                    sqlNodeInformation.nodePort,
                    sqlNodeInformation.numberOfConnectionPerDataSource,
                    sqlNodeInformation.driverName
                );
                
                if(ds.isSlave()){
                    slaveDataSources.add(ds);
                } else {
                    masterDataSource = ds;
                }
            } catch (SQLException e) {
                LOG.error("An error happened when executing AbstractDataSource.isSlave() "
                        + "in DataSourceManager.initializeNewDataSources()", e);
            }            
        } // for end
        
        // If master node does not exist
        if(masterDataSource == null){
            throw new MasterSQLNodeNotFoundException();
        }
        
        // If there's no slave DataSource, slaveDataSources will contain 1 element : The masterDataSource
        if(slaveDataSources.size() == 0){
            slaveDataSources.add(masterDataSource);
        }
        
        // Initialize Slave DataSource indexes
        currentSlaveIndex = -1; 
        maxSlaveIndex = slaveDataSources.size() - 1;  
    }
    
    
    
    /**
     * A connection to master node
     * 
     * @return a connection to master
     * @throws SQLException
     */
    public static Connection getConnectionToMaster() throws SQLException {
        return masterDataSource.getConnection();
    }
    
    
    
    /** 
     * A connection to slave node
     * Return a Round Robin connection to a slave node 
     * The returned connection will be closed when Statement/PreparedStatement will be closed
     * This method is ThreadSafe
     * 
     * @return a connection to slave
     * @throws SQLException
     */
    public static Connection getConnectionToSlave() throws SQLException {
        slaveLock.lock();
        Connection slaveConnection = null; // local variable      
        try { 
            // Begin round robin counter
            if(currentSlaveIndex == maxSlaveIndex){
                currentSlaveIndex = -1;
            }
            currentSlaveIndex++;
            // End round robin counter
            
            slaveConnection = slaveDataSources.get(currentSlaveIndex).getConnection();
        } finally {
            slaveLock.unlock();
        }
        
        return slaveConnection;
    }
    
    
    public static void initClientMulticastCheckAliveIfItsNull() throws SQLException {
        if(cmca == null){
            try {
                cmca = new ClientMulticastCheckAlive();
            } catch (IOException e) {
                LOG.error("An error happened during ClientMulticastCheckAlive instanciation", e);
                throw new SQLException(e);                   
            }
        }
    }
    
    
    
    /**
     * Get all informations about current SQLCluster connected nodes
     * 
     * @return
     */
    public static SqlNodeInformation[] getSqlNodeInformations(){
        return sqlNodeInformations;
    }
    
    
    /* *** DEPRECATED METHODS *** */   
    
    /**
     * This method obtains cluster nodes in "ipv4:port" format in pw_sql_cluster_nodes (jvm argument), 
     * analyze it and return an String array
     * 
     * @return
     * @throws IllegalArgumentException if the following format is not respected : 
     * "255.255.255.255:99999" or "255.255.255.255:99999|255.255.255.255:99999|255.255.255.255:99999"
     */
    /*
    @Deprecated
    public static String[] getClusterNodes() throws IllegalArgumentException {
        // Format must be : "255.255.255.255:33333" or "255.255.255.255:33333|255.255.255.255:33333|255.255.255.255:33333"
        String regexIps = "(([0-9]{1,3}\\.){3}[0-9]{1,3}:[0-9]{1,5})|((([0-9]{1,3}\\.){3}[0-9]{1,3}:[0-9]{1,5}\\|){1,}([0-9]{1,3}\\.){3}[0-9]{1,3}:[0-9]{1,5})";
        
        if(!clusterNodes.matches(regexIps)){
            throw new IllegalArgumentException("Wrong pw_sql_cluster_nodes value");
        }
        
        return clusterNodes.split("\\|");
    }
    */
}

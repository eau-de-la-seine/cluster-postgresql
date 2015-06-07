package fr.ekinci.clusterpostgresql;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import javax.sql.DataSource;


/**
 * A DataSource class for our partners
 * 
 * Advancement : 100%
 * Tests : Need database connection, complete version of tests in another project : mock-postgresql-rendez_vous
 * 
 * @author Gokan EKINCI
 */
public class SQLCluster implements DataSource {
    private final static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(SQLCluster.class);
    private volatile static boolean errorHappenedInStaticBlock = false;
    
    
    /**
    * This "static" block will be called the first time (and only once) when an attribute of this class is required
    */
    static {
        try {
            DataSourceManager.runInitializeDataSources();
        } catch (SQLException e) {
            errorHappenedInStaticBlock = true;
            LOG.fatal("An error happened during the first DataSource "
                    + "initialization in the static block of SQLCluster", e);
        }
    }
       
    
    public SQLCluster() throws SQLException {
        if(errorHappenedInStaticBlock){
            errorHappenedInStaticBlock = false; // Re-initialize for not showing more than 1 time
            throw new SQLException("An error happened during the first DataSource "
                    + "initialization in the static block of SQLCluster");
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return new ConnectionImpl(DataSourceManager.getConnectionToMaster());
    }
    
    @Override
    public Connection getConnection(String arg0, String arg1)
            throws SQLException {
        return getConnection();
    }
    
    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null; 
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter arg0) throws SQLException {
        
    }

    @Override
    public void setLoginTimeout(int arg0) throws SQLException {
        
    }

    @Override
    public boolean isWrapperFor(Class<?> arg0) throws SQLException {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> arg0) throws SQLException {
        return null;
    }

}

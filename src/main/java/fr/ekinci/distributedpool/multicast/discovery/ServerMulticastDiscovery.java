package fr.ekinci.distributedpool.multicast.discovery;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import fr.ekinci.distributedpool.multicast.MachineCategory;
import fr.ekinci.abstractdatasource.AbstractDataSource;
import fr.ekinci.multicastwrapper.ActionMessageListener;
import fr.ekinci.multicastwrapper.MulticastActionMessage;
import static fr.ekinci.distributedpool.multicast.MachineCategory.*;
import static fr.ekinci.distributedpool.multicast.MachineType.*;


/**
 * This class is used in node-database
 * Advancement : 100%
 * 
 * @author Gokan EKINCI
 */
public class ServerMulticastDiscovery extends AbstractMulticastDiscovery {
    private final ServerMulticastDiscoveryResponse mdsr;
    
    // SQL
    private final AbstractDataSource ds;
    
    public ServerMulticastDiscovery(AbstractDataSource ds)
        throws IOException, SocketException, UnknownHostException {        
        super(NODE_INPAAS_DATABASE.toString());        
        this.ds = ds;
        
        // Construct the response message for the multicast client
        mdsr = new ServerMulticastDiscoveryResponse(
            System.getProperty("pw_sql_corba_servicename"), 
            ipV4AddressOfThisMachine, 
            Integer.parseInt(System.getProperty("pw_sql_corba_port"))
        );
        
        addMessageListener(new MulticastDiscoveryServerActionMessageListener());
    }    
    
    
    public class MulticastDiscoveryServerActionMessageListener implements ActionMessageListener {
        private final Logger LOG = Logger.getLogger(MulticastDiscoveryServerActionMessageListener.class);
        
        @Override
        public void onMessage(MulticastActionMessage message) {                    
            
            MachineCategory currentMachineCategory = SLAVE; // Is slave by default
            try {
                currentMachineCategory = (ds.isSlave()) ? SLAVE : MASTER;
            } catch (SQLException e) {
                LOG.error("An erro happened during execution of AbstractDataSource.isSlave() "
                        + "in MulticastDiscoveryServerActionMessageListener.onMessage()", e);
            }
            
            // Respond only if current machine :
            // - is Master 
            // - and sender is node_inpaas_sqlclusterclient
            if( 
                currentMachineCategory == MASTER
                && message.getMachineType().equals(NODE_INPAAS_SQLCLUSTERCLIENT.toString())
            ){
                try {
                    MulticastActionMessage mam = new MulticastActionMessage();
                    mam.setJsonValue(gson.toJson(mdsr));
                    sendMessage(mam);
                } catch (IOException e) {
                    LOG.error("An error happened during sendMessage() in "
                            + "MulticastDiscoveryServerActionMessageListener.onMessage()", e);
                }
            } // End if
        } // End onMessage      
    } // End MulticastDiscoveryServerActionMessageListener
}

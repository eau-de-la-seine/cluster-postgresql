package fr.ekinci.distributedpool.multicast.checkalive;

import static fr.ekinci.distributedpool.multicast.MachineCategory.*;
import static fr.ekinci.distributedpool.multicast.MachineType.*;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import fr.ekinci.distributedpool.multicast.MachineCategory;
import fr.ekinci.abstractdatasource.AbstractDataSource;
import fr.ekinci.multicastwrapper.ActionMessageListener;
import fr.ekinci.multicastwrapper.MulticastActionMessage;


/**
 * Used for receiving a message when a node-database master send a
 * message in order to know if this node-client is alive 
 * 
 * Note: If current node-database is slave, it send a message like MulticastCheckAliveClient does
 * 
 * Advancement : 100%
 * @author Gokan EKINCI
 */
public class ServerMulticastCheckAlive extends AbstractMulticastCheckAlive {
    private final Set<AliveMachine> setOfAliveMachines = 
        Collections.synchronizedSet(new HashSet<AliveMachine>());
    
    // SQL
    private final AbstractDataSource ds;

    private final static int WAIT_ALIVE_MACHINE_TIMEOUT = 2_000;
    
    public ServerMulticastCheckAlive(AbstractDataSource ds)
            throws IOException, SocketException, UnknownHostException {
        super(NODE_INPAAS_DATABASE.toString());
        this.ds = ds;
        addMessageListener(new MulticastCheckAliveServerActionMessageListener());
    }
    
    
    /**
     * Make setOfAliveMachines empty before 
     */
    public void resetSetOfAliveMachines(){
        setOfAliveMachines.clear();
    }
    
    
    /**
     * get list of alive machine
     */
    public Set<AliveMachine> getSetOfAliveMachine(){
        return setOfAliveMachines;  
    }
    
    
    /**
     * Return a list of current alive machines
     * This method is executed in a node-database with MASTER category
     * 
     * WARNING : this method must be called One time per Corba call
     * @return
     * @throws IOException
     */
    public Set<AliveMachine> currentAliveMachines() throws IOException{
        resetSetOfAliveMachines();
        MulticastActionMessage mam = new MulticastActionMessage();
        mam.setMachineCategory(MachineCategory.MASTER.toString());
        sendMessage(mam, WAIT_ALIVE_MACHINE_TIMEOUT);
        
        // Add current machine (because multicast does not answer to itself
        setOfAliveMachines.add(new AliveMachine(
            this.getIpV4AddressOfThisMachine(), 
            NODE_INPAAS_DATABASE.toString(),
            null // No login for database
        ));
        
        return getSetOfAliveMachine();
    }
    
    
    /**
     * This implementation is used in node-database
     * If node-database is master  : fill is with alive machines ip
     * If node-database is slave   : send a message a message in the channel for node-database master
     * 
     * @author Gokan EKINCI
     */
    public class MulticastCheckAliveServerActionMessageListener implements ActionMessageListener {
        private final Logger LOG = Logger.getLogger(MulticastCheckAliveServerActionMessageListener.class);

        @Override
        public void onMessage(MulticastActionMessage message) {
            MachineCategory currentMachineCategory = SLAVE; // Is slave by default
            try {
                currentMachineCategory = (ds.isSlave()) ? SLAVE : MASTER;
            } catch (SQLException e) {
                LOG.error("An erro happened during execution of AbstractDataSource.isSlave() "
                        + "in MulticastCheckAliveServerActionMessageListener.onMessage()", e);
            }
            
            // If current machine is master or slave, fill setOfAliveMachines attribute
            setOfAliveMachines.add(new AliveMachine(
                message.getMachineIp(), 
                message.getMachineType(),
                gson.fromJson(message.getJsonValue(), String.class) // User login if it's SQLCLUSTERCLIENT
            ));

            // If current node-database is slave sendMessage and sender is master 
            // Slave node-database is client of the node-database master
            if(
                currentMachineCategory == SLAVE 
                && message.getMachineCategory() != null
                && message.getMachineCategory().equals(MachineCategory.MASTER.toString())
            ){
                try {
                    sendMessage(new MulticastActionMessage());
                } catch (IOException e) {
                    LOG.error("An error happened during sendMessage() in "
                            + "MulticastCheckAliveServerActionMessageListener.onMessage()", e);
                }
            }
            
        } // End onMessage      
    } // End MulticastCheckAliveServerActionMessageListener
}

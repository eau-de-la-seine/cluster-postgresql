package fr.ekinci.distributedpool.multicast.checkalive;

import static fr.ekinci.distributedpool.multicast.MachineType.*;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.apache.log4j.Logger;
import fr.ekinci.multicastwrapper.ActionMessageListener;
import fr.ekinci.multicastwrapper.MulticastActionMessage;


/**
 * Used for sending a message when a node-database master send a
 * message in order to know if this node-client is alive
 * 
 * @author Gokan EKINCI
 */
public class ClientMulticastCheckAlive extends AbstractMulticastCheckAlive {
    private final static Logger LOG = Logger.getLogger(ClientMulticastCheckAlive.class);
    
    public ClientMulticastCheckAlive()
            throws IOException, SocketException, UnknownHostException {
        super(NODE_INPAAS_SQLCLUSTERCLIENT.toString());
        addMessageListener(new MulticastCheckAliveClientActionMessageListener());
    }
    
    /**
     * Check if sender is node_inpaas_database and send a message
     * Only a node_inpaas_database (node-database) with master type send in this channel
     * We don't accept messages from a node_inpaas_sqlclusterclient
     * 
     * @author Gokan EKINCI
     */
    public class MulticastCheckAliveClientActionMessageListener implements ActionMessageListener {
        private final Logger LOG = Logger.getLogger(MulticastCheckAliveClientActionMessageListener.class);

        @Override
        public void onMessage(MulticastActionMessage message) {

            if(message.getMachineType().equals(NODE_INPAAS_DATABASE.toString())){
                try {
                    MulticastActionMessage mam = new MulticastActionMessage();
                    mam.setJsonValue(gson.toJson(System.getProperty("pw_sql_username")));
                    sendMessage(mam);
                } catch (IOException e) {
                    LOG.error("An error happened during sendMessage() in "
                            + "MulticastCheckAliveClientActionMessageListener.onMessage()", e);
                }
            }
        }      
    }

}

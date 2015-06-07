package fr.ekinci.distributedpool.multicast.discovery;

import static fr.ekinci.distributedpool.multicast.MachineType.*;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import fr.ekinci.multicastwrapper.ActionMessageListener;
import fr.ekinci.multicastwrapper.MulticastActionMessage;

/**
 * This class is waiting for a MulticastDiscoveryServerResponse
 * which contains Corba service informations
 * 
 * This class is used in node-client
 * 
 * @author Gokan EKINCI
 */
public class ClientMulticastDiscovery extends AbstractMulticastDiscovery {
    private volatile ServerMulticastDiscoveryResponse mdsr;
    
    public ClientMulticastDiscovery()
            throws IOException, SocketException, UnknownHostException {
        super(NODE_INPAAS_SQLCLUSTERCLIENT.toString());
        addMessageListener(new MulticastDiscoveryClientActionMessageListener());
    }
        
    public class MulticastDiscoveryClientActionMessageListener implements ActionMessageListener {
        @Override
        public void onMessage(MulticastActionMessage message) {
            // Check if sender is node_inpaas_database
            // Only a node_inpaas_database (node-database) with master type send in this channel)
            // We don't accept messages from a node_inpaas_sqlclusterclient
            if(message.getMachineType().equals(NODE_INPAAS_DATABASE.toString())){
                mdsr = gson.fromJson(message.getJsonValue(), ServerMulticastDiscoveryResponse.class);
            }
        }      
    }
    
    public ServerMulticastDiscoveryResponse getMulticastDiscoveryServerResponse(){
        return mdsr;
    }
}

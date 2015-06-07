package fr.ekinci.distributedpool.multicast.checkalive;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import fr.ekinci.multicastwrapper.MulticastBase;

/**
 * For checking if client is alive or not
 * If client is dead, it will be removed from SQL License Registry
 * 
 * @author Gokan EKINCI
 */
public abstract class AbstractMulticastCheckAlive extends MulticastBase {
    public final static String CURRENT_MACHINE_NETWORK_INTERFACE_NAME = "eth0";
    public final static String MULTICAST_VIRTUAL_GROUP_IPV4_ADDRESS = "224.224.224.224";
    public final static int MULTICAST_VIRTUAL_GROUP_PORT = 3002;
    
    public AbstractMulticastCheckAlive(String currentMachineType)
        throws IOException, SocketException, UnknownHostException {
        super(CURRENT_MACHINE_NETWORK_INTERFACE_NAME, 
                MULTICAST_VIRTUAL_GROUP_IPV4_ADDRESS,
                MULTICAST_VIRTUAL_GROUP_PORT, 
                currentMachineType);
    }
}

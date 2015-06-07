package fr.ekinci.distributedpool.multicast.replication;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.apache.log4j.Logger;
import fr.ekinci.abstractdatasource.AbstractDataSource;
import fr.ekinci.distributedpool.license.ILicenseRegistry;
import fr.ekinci.multicastwrapper.ActionMessageListener;
import fr.ekinci.multicastwrapper.MulticastActionMessage;
import fr.ekinci.multicastwrapper.SlaveInvoker;
import static fr.ekinci.distributedpool.multicast.MachineType.*;


/**
 * Replicate licence Registry in current context
 * 
 * Advancement : 100%
 * @author Gokan EKINCI
 */
public class ServerMulticastReplication extends AbstractMulticastReplication {
    private final AbstractDataSource ds;
    private ILicenseRegistry licenseRegistry;
    
    public ServerMulticastReplication(AbstractDataSource ds)
            throws IOException, SocketException, UnknownHostException {
        super(NODE_INPAAS_DATABASE.toString());
        this.ds = ds;
        addMessageListener(new MulticastReplicationServerActionMessageListener());
    }
    
    public void setLicenseRegistry(ILicenseRegistry licenseRegistry){
        this.licenseRegistry = licenseRegistry;
    }
    
    
    /**
     * If a master send a replication message, slave will receive it
     * Only a master send for slave in this multicast channel
     * 
     * @author Gokan EKINCI
     */
    public class MulticastReplicationServerActionMessageListener implements ActionMessageListener {
        private final Logger LOG = Logger.getLogger(MulticastReplicationServerActionMessageListener.class);
        
        @Override
        public void onMessage(MulticastActionMessage message) {                    
            try {
                SlaveInvoker.executeReceivedReplicationMessage(licenseRegistry, message);
            } catch (ClassNotFoundException | NoSuchMethodException
                    | SecurityException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException
                    | IOException e) {
                e.printStackTrace();
                LOG.error("An error happened during sendMessage() in "
                        + "MulticastReplicationServerActionMessageListener.onMessage()", e);    
            }
        } // End onMessage      
    } // End MulticastReplicationServerActionMessageListener

}

package fr.ekinci.distributedpool.license;

import java.io.Serializable;
import java.util.List;

/**
 * TODO
 * @author Christian DAGO
 */
public class ConnectionIp implements Serializable {

    private String clientNodeIp;
    private List<String> databaseNodeIp;
    
    public ConnectionIp(String clientNodeIp, List<String> databaseNodeIp){
        this.clientNodeIp = clientNodeIp;
        this.databaseNodeIp = databaseNodeIp;
    }
    
    public ConnectionIp(){
        
    }

    @Override
    public String toString() {
        return "ConnectionIp [clientNodeIp=" + clientNodeIp
                + ", databaseNodeIp=" + databaseNodeIp + "]";
    }

    public String getClientNodeIp() {
        return clientNodeIp;
    }

    public void setClientNodeIp(String clientNodeIp) {
        this.clientNodeIp = clientNodeIp;
    }

    public List<String> getDatabaseNodeIp() {
        return databaseNodeIp;
    }

    public void setDatabaseNodeIp(List<String> databaseNodeIp) {
        this.databaseNodeIp = databaseNodeIp;
    }
    
    
}

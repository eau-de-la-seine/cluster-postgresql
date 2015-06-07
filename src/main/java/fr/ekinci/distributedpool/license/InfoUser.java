package fr.ekinci.distributedpool.license;

import java.io.Serializable;
import java.util.List;

/**
 * TODO
 * @author Christian DAGO
 */
public class InfoUser implements Serializable {

    private int connectionAttributed;
    private int lastConnectionPerDatasource;
    private List<ConnectionIp> listConnectionIp;
    
    public InfoUser(List<ConnectionIp> listConnectionIp){
        this.setConnectionAttributed(0);
        this.setLastConnectionPerDatasource(0);
        this.setListConnectionIp(listConnectionIp);
    }

    public int getConnectionAttributed() {
        return connectionAttributed;
    }

    public void setConnectionAttributed(int connectionAttributed) {
        this.connectionAttributed = connectionAttributed;
    }

    public int getLastConnectionPerDatasource() {
        return lastConnectionPerDatasource;
    }

    public void setLastConnectionPerDatasource(int lastConnectionPerDatasource) {
        this.lastConnectionPerDatasource = lastConnectionPerDatasource;
    }

    public List<ConnectionIp> getListConnectionIp() {
        return listConnectionIp;
    }

    public void setListConnectionIp(List<ConnectionIp> listConnectionIp) {
        this.listConnectionIp = listConnectionIp;
    }

    @Override
    public String toString() {
        return "InfoUser [connectionAttributed=" + connectionAttributed
                + ", lastConnectionPerDatasource="
                + lastConnectionPerDatasource + ", listConnectionIp="
                + listConnectionIp + "]";
    }
    
    
}

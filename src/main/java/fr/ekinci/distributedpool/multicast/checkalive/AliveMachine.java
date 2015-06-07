package fr.ekinci.distributedpool.multicast.checkalive;

/**
 * Contains alive machines caracteristics
 * 
 * @author Gokan EKINCI
 */
public class AliveMachine {
    private String aliveMachineIpV4;
    private String aliveMachineType;
    private String aliveClientUserName; // Only useful if it's SQLCLUSTERCLIENT type
    
    public AliveMachine(
        String aliveMachineIpV4, 
        String aliveMachineType,
        String aliveClientUserName
    ){
        this.aliveMachineIpV4 = aliveMachineIpV4;
        this.aliveMachineType = aliveMachineType;
        this.aliveClientUserName = aliveClientUserName;
    }

    @Override
    public String toString() {
        return "AliveMachine [aliveMachineIpV4=" + aliveMachineIpV4
                + ", aliveMachineType=" + aliveMachineType
                + ", aliveClientUserName=" + aliveClientUserName + "]";
    }

    public String getAliveMachineIpV4() {
        return aliveMachineIpV4;
    }

    public String getAliveMachineType() {
        return aliveMachineType;
    }

    public String getAliveClientUserName() {
        return aliveClientUserName;
    }

    public void setAliveMachineIpV4(String aliveMachineIpV4) {
        this.aliveMachineIpV4 = aliveMachineIpV4;
    }

    public void setAliveMachineType(String aliveMachineType) {
        this.aliveMachineType = aliveMachineType;
    }

    public void setAliveClientUserName(String aliveClientUserName) {
        this.aliveClientUserName = aliveClientUserName;
    }    
}

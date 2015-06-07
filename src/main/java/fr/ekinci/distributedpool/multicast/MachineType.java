package fr.ekinci.distributedpool.multicast;


/**
 * Machine Type
 * 
 * @author Gokan EKINCI
 */
public enum MachineType {
    NODE_INPAAS_DATABASE("node_inpaas_database"),
    NODE_INPAAS_SQLCLUSTERCLIENT("node_inpaas_sqlclusterclient");
    
    private final String machineType;
    
    private MachineType(String machineType){
        this.machineType = machineType;
    }
    
    @Override
    public String toString(){
        return machineType;
    }
}

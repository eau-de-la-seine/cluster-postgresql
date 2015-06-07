package fr.ekinci.distributedpool.multicast;

/**
 * If node is Master or Slave
 * 
 * @author Gokan EKINCI
 */
public enum MachineCategory {
    MASTER("master"), SLAVE("slave");
    
    private final String machineCategory;
    
    private MachineCategory(String machineCategory){
        this.machineCategory = machineCategory;
    }
    
    @Override
    public String toString(){
        return machineCategory;
    }

}

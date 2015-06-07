package fr.ekinci.distributedpool.license;

import java.util.Map;


/**
 * License Registry Interface
 * 
 * @author Gokan EKINCI
 */
public interface ILicenseRegistry {
    
    /**
     * For replication and tests, we can get and set userMap;
     */
    Map<String, InfoUser> getUserMap();
}

package fr.ekinci.clusterpostgresql;

import java.sql.SQLException;

/**
 * 
 * @author Gokan EKINCI
 */
/* package visibility */ class LicenseCheckingInternalErrorException extends SQLException {
    public LicenseCheckingInternalErrorException(Throwable t){
        super(t);
    }
}

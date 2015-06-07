package fr.ekinci.clusterpostgresql;

import java.sql.SQLException;

/**
 * Exception which is launched when maximum number of connections in SQL cluster is reached
 * Advancement : 100%
 * 
 * @author Gokan EKINCI
 */
/* package visibility */ class MaximumNumberOfConnectionsInSQLClusterIsReachedException extends SQLException {
    public MaximumNumberOfConnectionsInSQLClusterIsReachedException(){
        super("The maximum number of Connections in the SQLCluster is Reached, "
                + "unfortunately you're not authorized to instanciate new Connections");
    }
}

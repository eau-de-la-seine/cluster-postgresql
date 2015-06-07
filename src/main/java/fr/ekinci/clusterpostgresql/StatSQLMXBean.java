package fr.ekinci.clusterpostgresql;

/**
 * JMX Interface for StatSQL
 * 
 * @author Gokan EKINCI
 */
public interface StatSQLMXBean {
	int getCounterInsertQuery();
	int getCounterUpdateQuery();
    int getCounterSelectQuery();
    int getCounterDeleteQuery();
    void incrementCounterInsertQuery();
    void incrementCounterUpdateQuery();
    void incrementCounterSelectQuery();
    void incrementCounterDeleteQuery();
    void decrementCounterInsertQuery();
    void decrementCounterUpdateQuery();
    void decrementCounterSelectQuery();
    void decrementCounterDeleteQuery();
}

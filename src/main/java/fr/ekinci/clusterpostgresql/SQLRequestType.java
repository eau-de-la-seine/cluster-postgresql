package fr.ekinci.clusterpostgresql;


/**
 * A class which manage READ/WRITE and SELECT/INSERT/UPDATE/DELETE SQL requests
 * 
 * Advancement : 100%
 * Tests : 100%
 * 
 * @author Gokan EKINCI
 */
/* package visibility */ enum SQLRequestType {
    READ, WRITE;
    
    private static StatSQL statSQL = new StatSQL();
    
    public static SQLRequestType getSQLRequestType(String sql){
        String dmlName = getSQLRequestDMLName(sql);
        
        switch(dmlName) {
            case "SELECT" : return READ;
            case "INSERT" : return WRITE;
            case "UPDATE" : return WRITE;
            case "DELETE" : return WRITE;
            default : return null;
        }
    }
    
    
    /**
     * Get DML name : SELECT | INSERT | UPDATE | DELETE
     * @param sql
     * @return the first 6 characters of SQL (DML) request
     */
    public static String getSQLRequestDMLName(String sql){
        return sql.trim().substring(0, 6).toUpperCase();
    }
    
    
    /**
     * Increment JMX SQL request counter for statistics
     * @param dmlName must be one of those DML : SELECT | INSERT | UPDATE | DELETE
     */
    public static void increment(String dmlName){
        switch(dmlName){
            case "SELECT" : 
                statSQL.incrementCounterSelectQuery(); 
                break;
            case "INSERT" : 
                statSQL.incrementCounterInsertQuery(); 
                break;
            case "UPDATE" : 
                statSQL.incrementCounterUpdateQuery(); 
                break;
            case "DELETE" : 
                statSQL.incrementCounterDeleteQuery(); 
                break;
        }
    }
    
    
    /**
     * Decrement JMX SQL request counter for statistics
     * @param dmlName must be one of those DML : SELECT | INSERT | UPDATE | DELETE
     */    
    public static void decrement(String dmlName){
        switch(dmlName){
            case "SELECT" : 
                statSQL.decrementCounterSelectQuery(); 
                break;
            case "INSERT" : 
                statSQL.decrementCounterInsertQuery(); 
                break;
            case "UPDATE" : 
                statSQL.decrementCounterUpdateQuery(); 
                break;
            case "DELETE" : 
                statSQL.decrementCounterDeleteQuery(); 
                break;
        }
    }
}

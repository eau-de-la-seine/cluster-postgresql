package fr.ekinci.clusterpostgresql;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * JMX Implementation for StatSQLMXBean
 * @author Gokan EKINCI
 */
class StatSQL implements StatSQLMXBean{

	private static AtomicInteger counterInsertQuery = new AtomicInteger(0);
    private static AtomicInteger counterSelectQuery = new AtomicInteger(0);
    private static AtomicInteger counterDeleteQuery = new AtomicInteger(0);
    private static AtomicInteger counterUpdateQuery = new AtomicInteger(0);
    
	@Override
	public int getCounterInsertQuery() {
		return counterInsertQuery.get();
	}
	@Override
	public int getCounterUpdateQuery() {
		return counterUpdateQuery.get();
	}
	@Override
	public int getCounterSelectQuery() {
		return counterSelectQuery.get();
	}
	@Override
	public int getCounterDeleteQuery() {
		return counterDeleteQuery.get();
	}
	@Override
	public void incrementCounterInsertQuery() {
		counterInsertQuery.incrementAndGet();
	}
	@Override
	public void incrementCounterUpdateQuery() {
		counterUpdateQuery.incrementAndGet();
	}
	@Override
	public void incrementCounterSelectQuery() {
		counterSelectQuery.incrementAndGet();
	}
	@Override
	public void incrementCounterDeleteQuery() {
		counterDeleteQuery.incrementAndGet();
	}
	@Override
	public void decrementCounterInsertQuery() {
		counterInsertQuery.decrementAndGet();
	}
	@Override
	public void decrementCounterUpdateQuery() {
		counterUpdateQuery.decrementAndGet();
	}
	@Override
	public void decrementCounterSelectQuery() {
		counterSelectQuery.decrementAndGet();
	}
	@Override
	public void decrementCounterDeleteQuery() {
		counterDeleteQuery.decrementAndGet();
	}
}

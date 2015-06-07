package fr.ekinci.clusterpostgresql;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import org.apache.log4j.Logger;

/**
 * A wrapper class for PreparedStatement
 * It's including JMX from pw-jmx-remote
 * 
 * Advancement : 100%
 * Tests : Need database connection, complete version of tests in another project : mock-postgresql-rendez_vous
 *
 * @author Gokan EKINCI
 */
/* package visibility */ class PreparedStatementImpl extends StatementImpl implements PreparedStatement {
    private final static Logger LOG = Logger.getLogger(PreparedStatementImpl.class);

    private final PreparedStatement originPreparedStatement;
	private final Connection connection;
	private final SQLRequestType type;
	private final boolean hasToCloseOriginConnection;
	private final String dmlName; // for pw-jmx-remote

	/**
	 * 
	 * @param pstmt - origin preparedStatement object
	 * @param connection - Could be a connection to master (if WRITE type request) or connection to slave (if READ type request)
	 * @param type - origin type of the preparedStatement object
	 * @param dmlName - origin dml name of the preparedStatement object
	 * @param originMasterConnection - useful for execute(String SQL, ...) implemented in StatementImpl
	 * @throws SQLException 
	 */
	public PreparedStatementImpl(
			PreparedStatement pstmt, 
			Connection connection, 
			SQLRequestType type, 
			String dmlName,
			Connection originMasterConnection 
			) throws SQLException{
		super(
		    originMasterConnection.createStatement(
		        pstmt.getResultSetType(), 
		        pstmt.getResultSetConcurrency(), 
		        pstmt.getResultSetHoldability()
		    )
	     );
		this.originPreparedStatement = pstmt;
		this.connection = connection;
		this.type = type;
		// if it's READ type, connection will be closed when PreparedStatement.close() is executed
		this.hasToCloseOriginConnection = (type == SQLRequestType.READ);
		this.dmlName = dmlName;
	}

	@Override
	public boolean execute() throws SQLException {        
		SQLRequestType.increment(dmlName);
		try {
			return originPreparedStatement.execute();
		} finally {
			SQLRequestType.decrement(dmlName);
		}
	}      

	@Override
	public ResultSet executeQuery() throws SQLException {
	    ResultSet resultSet = null;
	    if(type == SQLRequestType.READ){    
	        SQLRequestType.increment("SELECT");
	        // Its close method will decrement SELECT counter
	        resultSet = new ResultSetImpl(
	            originPreparedStatement.executeQuery()
	        );
	    } else{
	        LOG.error("Bad request, use a SELECT request in executeQuery()");
	        resultSet = originPreparedStatement.executeQuery(); // Will throw an exception because it's not SELECT
	    }
	    
		return resultSet;
	}

	@Override
	public int executeUpdate() throws SQLException {        
		SQLRequestType.increment(dmlName);
		try {
			return originPreparedStatement.executeUpdate();
		} finally {
			SQLRequestType.decrement(dmlName);
		}
	}

	/**
	 * VERY IMPORTANT
	 * if the connection is a slave type, it has to be closed when PreparedStatement.close() is called
	 */
	@Override
	public void close() throws SQLException {
	    try {
	        super.close(); // Call StatementImpl.close();
	    } finally {
	        try {
	            originPreparedStatement.close();
	        } finally {
	            if(hasToCloseOriginConnection){
	                connection.close();
	            }
	        }
	    }
	}

	@Override
	public void closeOnCompletion() throws SQLException {
		originPreparedStatement.closeOnCompletion();
	}



	/* DO NOT TOUCH METHODS BELOW */

	@Override
	public void clearWarnings() throws SQLException {
		originPreparedStatement.clearWarnings();
	}

	@Override
	public int getFetchDirection() throws SQLException {
		return originPreparedStatement.getFetchDirection();
	}

	@Override
	public int getFetchSize() throws SQLException {
		return originPreparedStatement.getFetchSize();
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		return originPreparedStatement.getGeneratedKeys();
	}

	@Override
	public int getMaxFieldSize() throws SQLException {
		return originPreparedStatement.getMaxFieldSize();
	}

	@Override
	public int getMaxRows() throws SQLException {
		return originPreparedStatement.getMaxRows();
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		return originPreparedStatement.getMoreResults();
	}

	@Override
	public boolean getMoreResults(int current) throws SQLException {
		return originPreparedStatement.getMoreResults(current);
	}

	@Override
	public int getQueryTimeout() throws SQLException {
		return originPreparedStatement.getQueryTimeout();
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		return originPreparedStatement.getResultSet();
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {
		return originPreparedStatement.getResultSetConcurrency();
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		return originPreparedStatement.getResultSetHoldability();
	}

	@Override
	public int getResultSetType() throws SQLException {
		return originPreparedStatement.getResultSetType();
	}

	@Override
	public int getUpdateCount() throws SQLException {
		return originPreparedStatement.getUpdateCount();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		return originPreparedStatement.getWarnings();
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		return originPreparedStatement.isCloseOnCompletion();
	}

	@Override
	public boolean isClosed() throws SQLException {
		return originPreparedStatement.isClosed();
	}

	@Override
	public boolean isPoolable() throws SQLException {
		return originPreparedStatement.isPoolable();
	}

	@Override
	public void setCursorName(String name) throws SQLException {
		originPreparedStatement.setCursorName(name);
	}

	@Override
	public void setEscapeProcessing(boolean enable) throws SQLException {
		originPreparedStatement.setEscapeProcessing(enable);
	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {
		originPreparedStatement.setFetchDirection(direction);
	}

	@Override
	public void setFetchSize(int rows) throws SQLException {
		originPreparedStatement.setFetchSize(rows);
	}

	@Override
	public void setMaxFieldSize(int max) throws SQLException {
		originPreparedStatement.setMaxFieldSize(max);
	}

	@Override
	public void setMaxRows(int max) throws SQLException {
		originPreparedStatement.setMaxRows(max);
	}

	@Override
	public void setPoolable(boolean poolable) throws SQLException {
		originPreparedStatement.setPoolable(poolable);
	}

	@Override
	public void setQueryTimeout(int seconds) throws SQLException {
		originPreparedStatement.setQueryTimeout(seconds);
	}

	@Override
	public void addBatch() throws SQLException {
		originPreparedStatement.addBatch();
	}

	@Override
	public void clearParameters() throws SQLException {
		originPreparedStatement.clearParameters();
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		return originPreparedStatement.getMetaData();
	}

	@Override
	public ParameterMetaData getParameterMetaData() throws SQLException {
		return originPreparedStatement.getParameterMetaData();
	}

	@Override
	public void setArray(int parameterIndex, Array x) throws SQLException {
		originPreparedStatement.setArray(parameterIndex, x);
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
		originPreparedStatement.setAsciiStream(parameterIndex, x);
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x, int length)
			throws SQLException {
		originPreparedStatement.setAsciiStream(parameterIndex, x, length);
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x, long length)
			throws SQLException {
		originPreparedStatement.setAsciiStream(parameterIndex, x, length);
	}

	@Override
	public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
		originPreparedStatement.setBigDecimal(parameterIndex, x);
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
		originPreparedStatement.setBinaryStream(parameterIndex, x);
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x, int length)
			throws SQLException {
		originPreparedStatement.setBinaryStream(parameterIndex, x, length);
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x, long length)
			throws SQLException {
		originPreparedStatement.setBinaryStream(parameterIndex, x, length);
	}

	@Override
	public void setBlob(int parameterIndex, Blob x) throws SQLException {
		originPreparedStatement.setBlob(parameterIndex, x);
	}

	@Override
	public void setBlob(int parameterIndex, InputStream x) throws SQLException {
		originPreparedStatement.setBlob(parameterIndex, x);
	}

	@Override
	public void setBlob(int parameterIndex, InputStream inputStream, long length)
			throws SQLException {
		originPreparedStatement.setBlob(parameterIndex, inputStream, length);
	}

	@Override
	public void setBoolean(int parameterIndex, boolean x) throws SQLException {
		originPreparedStatement.setBoolean(parameterIndex, x);
	}

	@Override
	public void setByte(int parameterIndex, byte x) throws SQLException {
		originPreparedStatement.setByte(parameterIndex, x);
	}

	@Override
	public void setBytes(int parameterIndex, byte[] x) throws SQLException {
		originPreparedStatement.setBytes(parameterIndex, x);
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
		originPreparedStatement.setCharacterStream(parameterIndex, reader);
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader, int length)
			throws SQLException {
		originPreparedStatement.setCharacterStream(parameterIndex, reader, length);
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader, long length)
			throws SQLException {
		originPreparedStatement.setCharacterStream(parameterIndex, reader, length);
	}

	@Override
	public void setClob(int parameterIndex, Clob x) throws SQLException {
		originPreparedStatement.setClob(parameterIndex, x);
	}

	@Override
	public void setClob(int parameterIndex, Reader reader) throws SQLException {
		originPreparedStatement.setClob(parameterIndex, reader);
	}

	@Override
	public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
		originPreparedStatement.setClob(parameterIndex, reader, length);
	}

	@Override
	public void setDate(int parameterIndex, Date x) throws SQLException {
		originPreparedStatement.setDate(parameterIndex, x);
	}

	@Override
	public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
		originPreparedStatement.setDate(parameterIndex, x, cal);
	}

	@Override
	public void setDouble(int parameterIndex, double x) throws SQLException {
		originPreparedStatement.setDouble(parameterIndex, x);
	}

	@Override
	public void setFloat(int parameterIndex, float x) throws SQLException {
		originPreparedStatement.setFloat(parameterIndex, x);
	}

	@Override
	public void setInt(int parameterIndex, int x) throws SQLException {
		originPreparedStatement.setInt(parameterIndex, x);
	}

	@Override
	public void setLong(int parameterIndex, long x) throws SQLException {
		originPreparedStatement.setLong(parameterIndex, x);
	}

	@Override
	public void setNCharacterStream(int parameterIndex, Reader reader) throws SQLException {
		originPreparedStatement.setNCharacterStream(parameterIndex, reader);
	}

	@Override
	public void setNCharacterStream(int parameterIndex, Reader reader, long length)
			throws SQLException {
		originPreparedStatement.setNCharacterStream(parameterIndex, reader, length);
	}

	@Override
	public void setNClob(int parameterIndex, NClob value) throws SQLException {
		originPreparedStatement.setNClob(parameterIndex, value);
	}

	@Override
	public void setNClob(int parameterIndex, Reader reader) throws SQLException {
		originPreparedStatement.setNClob(parameterIndex, reader);
	}

	@Override
	public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
		originPreparedStatement.setNClob(parameterIndex, reader, length);
	}

	@Override
	public void setNString(int parameterIndex, String value) throws SQLException {
		originPreparedStatement.setNString(parameterIndex, value);
	}

	@Override
	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		originPreparedStatement.setNull(parameterIndex, sqlType);
	}

	@Override
	public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
		originPreparedStatement.setNull(parameterIndex, sqlType, typeName);
	}

	@Override
	public void setObject(int parameterIndex, Object x) throws SQLException {
		originPreparedStatement.setObject(parameterIndex, x);
	}

	@Override
	public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
		originPreparedStatement.setObject(parameterIndex, x, targetSqlType);
	}

	@Override
	public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength)
			throws SQLException {
		originPreparedStatement.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
	}

	@Override
	public void setRef(int parameterIndex, Ref x) throws SQLException {
		originPreparedStatement.setRef(parameterIndex, x);
	}

	@Override
	public void setRowId(int parameterIndex, RowId x) throws SQLException {
		originPreparedStatement.setRowId(parameterIndex, x);
	}

	@Override
	public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
		originPreparedStatement.setSQLXML(parameterIndex, xmlObject);
	}

	@Override
	public void setShort(int parameterIndex, short x) throws SQLException {
		originPreparedStatement.setShort(parameterIndex, x);
	}

	@Override
	public void setString(int parameterIndex, String x) throws SQLException {
		originPreparedStatement.setString(parameterIndex, x);
	}

	@Override
	public void setTime(int parameterIndex, Time x) throws SQLException {
		originPreparedStatement.setTime(parameterIndex, x);
	}

	@Override
	public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
		originPreparedStatement.setTime(parameterIndex, x, cal);
	}

	@Override
	public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
		originPreparedStatement.setTimestamp(parameterIndex, x);
	}

	@Override
	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal)
			throws SQLException {
		originPreparedStatement.setTimestamp(parameterIndex, x, cal);
	}

	@Override
	public void setURL(int parameterIndex, URL x) throws SQLException {
		originPreparedStatement.setURL(parameterIndex, x);
	}

	@Override
	public void setUnicodeStream(int parameterIndex, InputStream x, int length)
			throws SQLException {
		originPreparedStatement.setUnicodeStream(parameterIndex, x, length);
	}




	/* Redefined Statement methods */

	@Override
	public void addBatch(String sql) throws SQLException {
		originPreparedStatement.addBatch(sql);        
	}

	@Override
	public int[] executeBatch() throws SQLException {
		return originPreparedStatement.executeBatch();
	}

	@Override
	public void clearBatch() throws SQLException {
		originPreparedStatement.clearBatch();
	}

	@Override
	public void cancel() throws SQLException {
		originPreparedStatement.cancel();
	}

	@Override
	public Connection getConnection() throws SQLException {
		return connection;
	}



	/*
    Statement interface methods implemented in StatementImpl ! :

    @Override
    public boolean execute(String sql) throws SQLException {
        return false;       
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        return false;
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        return false;
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        return false;
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        return null;
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        return 0;
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return 0;
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        return 0;
    }

    @Override
    public int executeUpdate(String sql, String[] columnIndexes) throws SQLException {
        return 0;
    }


    Wrapper interface methods implemented in StatementImpl ! :

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return originPreparedStatement.isWrapperFor(iface);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return originPreparedStatement.unwrap(iface);
    }
	 */

}

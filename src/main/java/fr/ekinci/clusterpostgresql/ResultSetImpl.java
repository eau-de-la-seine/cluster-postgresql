package fr.ekinci.clusterpostgresql;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;


/**
 * Only close() is used for decrementing JMX SELECT counter
 * @see close() method !
 * 
 * Advancement : 100%
 * Tests : Need database connection, complete version of tests in another project : mock-postgresql-rendez_vous
 * 
 * @author Gokan EKINCI
 */
/* package visibility */ class ResultSetImpl implements ResultSet {
    private ResultSet originResultSet;
    
    public ResultSetImpl(ResultSet resultSet){
        this.originResultSet = resultSet;
    }
    
    /**
     * This method is redefined for decrementing SELECT counter
     */
    @Override
    public void close() throws SQLException {
        SQLRequestType.decrement("SELECT");
        originResultSet.close();
    }
    
    @Override
    public Statement getStatement() throws SQLException {
        return originResultSet.getStatement();
    }
    
    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return originResultSet.isWrapperFor(iface);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return originResultSet.unwrap(iface);
    }

    @Override
    public boolean absolute(int row) throws SQLException {
        return originResultSet.absolute(row);
    }

    @Override
    public void afterLast() throws SQLException {
        originResultSet.afterLast();
    }

    @Override
    public void beforeFirst() throws SQLException {
        originResultSet.beforeFirst();
    }

    @Override
    public void cancelRowUpdates() throws SQLException {
        originResultSet.cancelRowUpdates();
    }

    @Override
    public void clearWarnings() throws SQLException {
        originResultSet.clearWarnings();        
    }

    @Override
    public void deleteRow() throws SQLException {
        originResultSet.deleteRow();
    }

    @Override
    public int findColumn(String columnLabel) throws SQLException {
        return originResultSet.findColumn(columnLabel);
    }

    @Override
    public boolean first() throws SQLException {
        return originResultSet.first();
    }

    @Override
    public Array getArray(int columnIndex) throws SQLException {
        return originResultSet.getArray(columnIndex);
    }

    @Override
    public Array getArray(String columnLabel) throws SQLException {
        return originResultSet.getArray(columnLabel);
    }

    @Override
    public InputStream getAsciiStream(int columnIndex) throws SQLException {
        return originResultSet.getAsciiStream(columnIndex);
    }

    @Override
    public InputStream getAsciiStream(String columnLabel) throws SQLException {
        return originResultSet.getAsciiStream(columnLabel);
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        return originResultSet.getBigDecimal(columnIndex);
    }

    @Override
    public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
        return originResultSet.getBigDecimal(columnLabel);
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
        return originResultSet.getBigDecimal(columnIndex, scale);
    }

    @Override
    public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
        return originResultSet.getBigDecimal(columnLabel, scale);
    }

    @Override
    public InputStream getBinaryStream(int columnIndex) throws SQLException {
        return originResultSet.getBinaryStream(columnIndex);
    }

    @Override
    public InputStream getBinaryStream(String columnLabel) throws SQLException {
        return originResultSet.getBinaryStream(columnLabel);
    }

    @Override
    public Blob getBlob(int columnIndex) throws SQLException {
        return originResultSet.getBlob(columnIndex);
    }

    @Override
    public Blob getBlob(String columnLabel) throws SQLException {
        return originResultSet.getBlob(columnLabel);
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        return originResultSet.getBoolean(columnIndex);
    }

    @Override
    public boolean getBoolean(String columnLabel) throws SQLException {
        return originResultSet.getBoolean(columnLabel);
    }

    @Override
    public byte getByte(int columnIndex) throws SQLException {
        return originResultSet.getByte(columnIndex);
    }

    @Override
    public byte getByte(String columnLabel) throws SQLException {
        return originResultSet.getByte(columnLabel);
    }

    @Override
    public byte[] getBytes(int columnIndex) throws SQLException {
        return originResultSet.getBytes(columnIndex);
    }

    @Override
    public byte[] getBytes(String columnLabel) throws SQLException {
        return originResultSet.getBytes(columnLabel);
    }

    @Override
    public Reader getCharacterStream(int columnIndex) throws SQLException {
        return originResultSet.getCharacterStream(columnIndex);
    }

    @Override
    public Reader getCharacterStream(String columnLabel) throws SQLException {
        return originResultSet.getCharacterStream(columnLabel);
    }

    @Override
    public Clob getClob(int columnIndex) throws SQLException {
        return originResultSet.getClob(columnIndex);
    }

    @Override
    public Clob getClob(String columnLabel) throws SQLException {
        return originResultSet.getClob(columnLabel);
    }

    @Override
    public int getConcurrency() throws SQLException {
        return originResultSet.getConcurrency();
    }

    @Override
    public String getCursorName() throws SQLException {
        return originResultSet.getCursorName();
    }

    @Override
    public Date getDate(int columnIndex) throws SQLException {
        return originResultSet.getDate(columnIndex);
    }

    @Override
    public Date getDate(String columnLabel) throws SQLException {
        return originResultSet.getDate(columnLabel);
    }

    @Override
    public Date getDate(int columnIndex, Calendar cal) throws SQLException {
        return originResultSet.getDate(columnIndex, cal);
    }

    @Override
    public Date getDate(String columnLabel, Calendar cal) throws SQLException {
        return originResultSet.getDate(columnLabel, cal);
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException {
        return originResultSet.getDouble(columnIndex);
    }

    @Override
    public double getDouble(String columnLabel) throws SQLException {
        return originResultSet.getDouble(columnLabel);
    }

    @Override
    public int getFetchDirection() throws SQLException {
        return originResultSet.getFetchDirection();
    }

    @Override
    public int getFetchSize() throws SQLException {
        return originResultSet.getFetchSize();
    }

    @Override
    public float getFloat(int columnIndex) throws SQLException {
        return originResultSet.getFloat(columnIndex);
    }

    @Override
    public float getFloat(String columnLabel) throws SQLException {
        return originResultSet.getFloat(columnLabel);
    }

    @Override
    public int getHoldability() throws SQLException {
        return originResultSet.getHoldability();
    }

    @Override
    public int getInt(int columnIndex) throws SQLException {
        return originResultSet.getInt(columnIndex);
    }

    @Override
    public int getInt(String columnLabel) throws SQLException {
        return originResultSet.getInt(columnLabel);
    }

    @Override
    public long getLong(int columnIndex) throws SQLException {
        return originResultSet.getLong(columnIndex);
    }

    @Override
    public long getLong(String columnLabel) throws SQLException {
        return originResultSet.getLong(columnLabel);
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return originResultSet.getMetaData();
    }

    @Override
    public Reader getNCharacterStream(int columnIndex) throws SQLException {
        return originResultSet.getNCharacterStream(columnIndex);
    }

    @Override
    public Reader getNCharacterStream(String columnLabel) throws SQLException {
        return originResultSet.getNCharacterStream(columnLabel);
    }

    @Override
    public NClob getNClob(int columnIndex) throws SQLException {
        return originResultSet.getNClob(columnIndex);
    }

    @Override
    public NClob getNClob(String columnLabel) throws SQLException {
        return originResultSet.getNClob(columnLabel);
    }

    @Override
    public String getNString(int columnIndex) throws SQLException {
        return originResultSet.getNString(columnIndex);
    }

    @Override
    public String getNString(String columnLabel) throws SQLException {
        return originResultSet.getNString(columnLabel);
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        return originResultSet.getObject(columnIndex);
    }

    @Override
    public Object getObject(String columnLabel) throws SQLException {
        return originResultSet.getObject(columnLabel);
    }

    @Override
    public Object getObject(int columnIndex, Map<String, Class<?>> map)
            throws SQLException {
        return originResultSet.getObject(columnIndex, map);
    }

    @Override
    public Object getObject(String columnLabel, Map<String, Class<?>> map)
            throws SQLException {
        return originResultSet.getObject(columnLabel, map);
    }

    @Override
    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        return originResultSet.getObject(columnIndex, type);
    }

    @Override
    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        return originResultSet.getObject(columnLabel, type);
    }

    @Override
    public Ref getRef(int columnIndex) throws SQLException {
        return originResultSet.getRef(columnIndex);
    }

    @Override
    public Ref getRef(String columnLabel) throws SQLException {
        return originResultSet.getRef(columnLabel);
    }

    @Override
    public int getRow() throws SQLException {
        return originResultSet.getRow();
    }

    @Override
    public RowId getRowId(int columnIndex) throws SQLException {
        return originResultSet.getRowId(columnIndex);
    }

    @Override
    public RowId getRowId(String columnLabel) throws SQLException {
        return originResultSet.getRowId(columnLabel);
    }

    @Override
    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        return originResultSet.getSQLXML(columnIndex);
    }

    @Override
    public SQLXML getSQLXML(String columnLabel) throws SQLException {
        return originResultSet.getSQLXML(columnLabel);
    }

    @Override
    public short getShort(int columnIndex) throws SQLException {
        return originResultSet.getShort(columnIndex);
    }

    @Override
    public short getShort(String columnLabel) throws SQLException {
        return originResultSet.getShort(columnLabel);
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        return originResultSet.getString(columnIndex);
    }

    @Override
    public String getString(String columnLabel) throws SQLException {
        return originResultSet.getString(columnLabel);
    }

    @Override
    public Time getTime(int columnIndex) throws SQLException {
        return originResultSet.getTime(columnIndex);
    }

    @Override
    public Time getTime(String columnLabel) throws SQLException {
        return originResultSet.getTime(columnLabel);
    }

    @Override
    public Time getTime(int columnIndex, Calendar cal) throws SQLException {
        return originResultSet.getTime(columnIndex, cal);
    }

    @Override
    public Time getTime(String columnLabel, Calendar cal) throws SQLException {
        return originResultSet.getTime(columnLabel, cal);
    }

    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        return originResultSet.getTimestamp(columnIndex);
    }

    @Override
    public Timestamp getTimestamp(String columnLabel) throws SQLException {
        return originResultSet.getTimestamp(columnLabel);
    }

    @Override
    public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
        return originResultSet.getTimestamp(columnIndex, cal);
    }

    @Override
    public Timestamp getTimestamp(String columnLabel, Calendar cal)
            throws SQLException {
        return originResultSet.getTimestamp(columnLabel, cal);
    }

    @Override
    public int getType() throws SQLException {
        return originResultSet.getType();
    }

    @Override
    public URL getURL(int columnIndex) throws SQLException {
        return originResultSet.getURL(columnIndex);
    }

    @Override
    public URL getURL(String columnLabel) throws SQLException {
        return originResultSet.getURL(columnLabel);
    }

    @Override
    public InputStream getUnicodeStream(int columnIndex) throws SQLException {
        return originResultSet.getUnicodeStream(columnIndex);
    }

    @Override
    public InputStream getUnicodeStream(String columnLabel) throws SQLException {
        return originResultSet.getUnicodeStream(columnLabel);
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return originResultSet.getWarnings();
    }

    @Override
    public void insertRow() throws SQLException {
        originResultSet.insertRow();
    }

    @Override
    public boolean isAfterLast() throws SQLException {
        return originResultSet.isAfterLast();
    }

    @Override
    public boolean isBeforeFirst() throws SQLException {
        return originResultSet.isBeforeFirst();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return originResultSet.isClosed();
    }

    @Override
    public boolean isFirst() throws SQLException {
        return originResultSet.isFirst();
    }

    @Override
    public boolean isLast() throws SQLException {
        return originResultSet.isLast();
    }

    @Override
    public boolean last() throws SQLException {
        return originResultSet.last();
    }

    @Override
    public void moveToCurrentRow() throws SQLException {
        originResultSet.moveToCurrentRow();
    }

    @Override
    public void moveToInsertRow() throws SQLException {
        originResultSet.moveToInsertRow();
    }

    @Override
    public boolean next() throws SQLException {
        return originResultSet.next();
    }

    @Override
    public boolean previous() throws SQLException {
        return originResultSet.previous();
    }

    @Override
    public void refreshRow() throws SQLException {
        originResultSet.refreshRow();
    }

    @Override
    public boolean relative(int rows) throws SQLException {
        return originResultSet.relative(rows);
    }

    @Override
    public boolean rowDeleted() throws SQLException {
        return originResultSet.rowDeleted();
    }

    @Override
    public boolean rowInserted() throws SQLException {
        return originResultSet.rowInserted();
    }

    @Override
    public boolean rowUpdated() throws SQLException {
        return originResultSet.rowUpdated();
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        originResultSet.setFetchDirection(direction);
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        originResultSet.setFetchSize(rows);
    }

    @Override
    public void updateArray(int columnIndex, Array x) throws SQLException {
        originResultSet.updateArray(columnIndex, x);
    }

    @Override
    public void updateArray(String columnLabel, Array x) throws SQLException {
        originResultSet.updateArray(columnLabel, x);
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x)
            throws SQLException {
        originResultSet.updateAsciiStream(columnIndex, x);
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x)
            throws SQLException {
        originResultSet.updateAsciiStream(columnLabel, x);
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, int length)
            throws SQLException {
        originResultSet.updateAsciiStream(columnIndex, x, length);
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, int length)
            throws SQLException {
        originResultSet.updateAsciiStream(columnLabel, x, length);
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, long length)
            throws SQLException {
        originResultSet.updateAsciiStream(columnIndex, x, length);
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, long length)
            throws SQLException {
        originResultSet.updateAsciiStream(columnLabel, x, length);
    }

    @Override
    public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
        originResultSet.updateBigDecimal(columnIndex, x);
    }

    @Override
    public void updateBigDecimal(String columnLabel, BigDecimal x)
            throws SQLException {
        originResultSet.updateBigDecimal(columnLabel, x);
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x)
            throws SQLException {
        originResultSet.updateBinaryStream(columnIndex, x);
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x)
            throws SQLException {
        originResultSet.updateBinaryStream(columnLabel, x);
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, int length)
            throws SQLException {
        originResultSet.updateBinaryStream(columnIndex, x, length);
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, int length)
            throws SQLException {
        originResultSet.updateBinaryStream(columnLabel, x, length);
    }

    @Override
    public void updateBinaryStream(int columnLabel, InputStream x, long length)
            throws SQLException {
        originResultSet.updateBinaryStream(columnLabel, x, length);
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, long length)
            throws SQLException {
        originResultSet.updateBinaryStream(columnLabel, x, length);
    }

    @Override
    public void updateBlob(int columnIndex, Blob x) throws SQLException {
        originResultSet.updateBlob(columnIndex, x);
    }

    @Override
    public void updateBlob(String columnLabel, Blob x) throws SQLException {
        originResultSet.updateBlob(columnLabel, x);
    }

    @Override
    public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
        originResultSet.updateBlob(columnIndex, inputStream);
    }

    @Override
    public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
        originResultSet.updateBlob(columnLabel, inputStream);
    }

    @Override
    public void updateBlob(int columnIndex, InputStream inputStream, long length)
            throws SQLException {
        originResultSet.updateBlob(columnIndex, inputStream, length);
    }

    @Override
    public void updateBlob(String columnLabel, InputStream inputStream, long length)
            throws SQLException {
        originResultSet.updateBlob(columnLabel, inputStream, length);
    }

    @Override
    public void updateBoolean(int columnIndex, boolean x) throws SQLException {
        originResultSet.updateBoolean(columnIndex, x);
    }

    @Override
    public void updateBoolean(String columnLabel, boolean x) throws SQLException {
        originResultSet.updateBoolean(columnLabel, x);
    }

    @Override
    public void updateByte(int columnIndex, byte x) throws SQLException {
        originResultSet.updateByte(columnIndex, x);
    }

    @Override
    public void updateByte(String columnLabel, byte x) throws SQLException {
        originResultSet.updateByte(columnLabel, x);
    }

    @Override
    public void updateBytes(int columnIndex, byte[] x) throws SQLException {
        originResultSet.updateBytes(columnIndex, x);
    }

    @Override
    public void updateBytes(String columnLabel, byte[] x) throws SQLException {
        originResultSet.updateBytes(columnLabel, x);
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x)
            throws SQLException {
        originResultSet.updateCharacterStream(columnIndex, x);
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader)
            throws SQLException {
        originResultSet.updateCharacterStream(columnLabel, reader);
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x, int length)
            throws SQLException {
        originResultSet.updateCharacterStream(columnIndex, x, length);
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, int length)
            throws SQLException {
        originResultSet.updateCharacterStream(columnLabel, reader, length);
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x, long length)
            throws SQLException {
        originResultSet.updateCharacterStream(columnIndex, x, length);
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, long length)
            throws SQLException {
        originResultSet.updateCharacterStream(columnLabel, reader, length);
    }

    @Override
    public void updateClob(int columnIndex, Clob x) throws SQLException {
        originResultSet.updateClob(columnIndex, x);
    }

    @Override
    public void updateClob(String columnLabel, Clob x) throws SQLException {
        originResultSet.updateClob(columnLabel, x);
    }

    @Override
    public void updateClob(int columnIndex, Reader reader) throws SQLException {
        originResultSet.updateClob(columnIndex, reader);
    }

    @Override
    public void updateClob(String columnLabel, Reader reader) throws SQLException {
        originResultSet.updateClob(columnLabel, reader);
    }

    @Override
    public void updateClob(int columnIndex, Reader reader, long length)
            throws SQLException {
        originResultSet.updateClob(columnIndex, reader, length);
    }

    @Override
    public void updateClob(String columnLabel, Reader reader, long length)
            throws SQLException {
        originResultSet.updateClob(columnLabel, reader, length);
    }

    @Override
    public void updateDate(int columnIndex, Date x) throws SQLException {
        originResultSet.updateDate(columnIndex, x);
    }

    @Override
    public void updateDate(String columnLabel, Date x) throws SQLException {
        originResultSet.updateDate(columnLabel, x);
    }

    @Override
    public void updateDouble(int columnIndex, double x) throws SQLException {
        originResultSet.updateDouble(columnIndex, x);
    }

    @Override
    public void updateDouble(String columnLabel, double x) throws SQLException {
        originResultSet.updateDouble(columnLabel, x);
    }

    @Override
    public void updateFloat(int columnIndex, float x) throws SQLException {
        originResultSet.updateFloat(columnIndex, x);
    }

    @Override
    public void updateFloat(String columnLabel, float x) throws SQLException {
        originResultSet.updateFloat(columnLabel, x);
    }

    @Override
    public void updateInt(int columnIndex, int x) throws SQLException {
        originResultSet.updateInt(columnIndex, x);
    }

    @Override
    public void updateInt(String columnLabel, int x) throws SQLException {
        originResultSet.updateInt(columnLabel, x);
    }

    @Override
    public void updateLong(int columnIndex, long x) throws SQLException {
        originResultSet.updateLong(columnIndex, x);
    }

    @Override
    public void updateLong(String columnLabel, long x) throws SQLException {
        originResultSet.updateLong(columnLabel, x);
    }

    @Override
    public void updateNCharacterStream(int columnIndex, Reader x)
            throws SQLException {
        originResultSet.updateNCharacterStream(columnIndex, x);
    }

    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader)
            throws SQLException {
        originResultSet.updateNCharacterStream(columnLabel, reader);
    }

    @Override
    public void updateNCharacterStream(int columnIndex, Reader x, long length)
            throws SQLException {
        originResultSet.updateNCharacterStream(columnIndex, x, length);
    }

    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader, long length)
            throws SQLException {
        originResultSet.updateNCharacterStream(columnLabel, reader, length);
    }

    @Override
    public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
        originResultSet.updateNClob(columnIndex, nClob);
    }

    @Override
    public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
        originResultSet.updateNClob(columnLabel, nClob);
    }

    @Override
    public void updateNClob(int columnIndex, Reader reader) throws SQLException {
        originResultSet.updateNClob(columnIndex, reader);
    }

    @Override
    public void updateNClob(String columnLabel, Reader reader) throws SQLException {
        originResultSet.updateNClob(columnLabel, reader);
    }

    @Override
    public void updateNClob(int columnIndex, Reader reader, long length)
            throws SQLException {
        originResultSet.updateNClob(columnIndex, reader, length);
    }

    @Override
    public void updateNClob(String columnIndex, Reader reader, long length)
            throws SQLException {
        originResultSet.updateNClob(columnIndex, reader, length);
    }

    @Override
    public void updateNString(int columnIndex, String nString) throws SQLException {
        originResultSet.updateNString(columnIndex, nString);
    }

    @Override
    public void updateNString(String columnLabel, String nString) throws SQLException {
        originResultSet.updateNString(columnLabel, nString);
    }

    @Override
    public void updateNull(int columnIndex) throws SQLException {
        originResultSet.updateNull(columnIndex);
    }

    @Override
    public void updateNull(String columnLabel) throws SQLException {
        originResultSet.updateNull(columnLabel);
    }

    @Override
    public void updateObject(int columnIndex, Object x) throws SQLException {
        originResultSet.updateObject(columnIndex, x);
    }

    @Override
    public void updateObject(String columnLabel, Object x) throws SQLException {
        originResultSet.updateObject(columnLabel, x);
    }

    @Override
    public void updateObject(int columnIndex, Object x, int scaleOrLength)
            throws SQLException {
        originResultSet.updateObject(columnIndex, x, scaleOrLength);
    }

    @Override
    public void updateObject(String columnLabel, Object x, int scaleOrLength)
            throws SQLException {
        originResultSet.updateObject(columnLabel, x, scaleOrLength);
    }

    @Override
    public void updateRef(int columnIndex, Ref x) throws SQLException {
        originResultSet.updateRef(columnIndex, x);
    }

    @Override
    public void updateRef(String columnIndex, Ref x) throws SQLException {
        originResultSet.updateRef(columnIndex, x);
    }

    @Override
    public void updateRow() throws SQLException {
        originResultSet.updateRow();
    }

    @Override
    public void updateRowId(int columnIndex, RowId x) throws SQLException {
        originResultSet.updateRowId(columnIndex, x);
    }

    @Override
    public void updateRowId(String columnLabel, RowId x) throws SQLException {
        originResultSet.updateRowId(columnLabel, x);
    }

    @Override
    public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
        originResultSet.updateSQLXML(columnIndex, xmlObject);
    }

    @Override
    public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
        originResultSet.updateSQLXML(columnLabel, xmlObject);
    }

    @Override
    public void updateShort(int columnIndex, short x) throws SQLException {
        originResultSet.updateShort(columnIndex, x);
    }

    @Override
    public void updateShort(String columnIndex, short x) throws SQLException {
        originResultSet.updateShort(columnIndex, x);        
    }

    @Override
    public void updateString(int columnIndex, String x) throws SQLException {
        originResultSet.updateString(columnIndex, x);
    }

    @Override
    public void updateString(String columnLabel, String x) throws SQLException {
        originResultSet.updateString(columnLabel, x);
    }

    @Override
    public void updateTime(int columnIndex, Time x) throws SQLException {
        originResultSet.updateTime(columnIndex, x);
    }

    @Override
    public void updateTime(String columnLabel, Time x) throws SQLException {
        originResultSet.updateTime(columnLabel, x);
    }

    @Override
    public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
        originResultSet.updateTimestamp(columnIndex, x);
    }

    @Override
    public void updateTimestamp(String columnLabel, Timestamp x)
            throws SQLException {
        originResultSet.updateTimestamp(columnLabel, x);
    }

    @Override
    public boolean wasNull() throws SQLException {
        return originResultSet.wasNull();
    }

}

package EasySQL.object;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.*;
import java.text.MessageFormat;
import java.util.Map;

public class EasyTable {

    private final String tableName;
    private Connection connection;

    public EasyTable(Connection connection, String tableName) {
        this.tableName = tableName;
        this.connection = connection;
    }
    public EasyTable(MysqlDataSource dataSource, String tableName) {
        this.tableName = tableName;
        try { this.connection = dataSource.getConnection(); }
        catch (SQLException e) { e.printStackTrace(); }
    }
    public EasyTable(String databaseName, String databaseUser, String databasePassword, String tableName) {
        this.tableName = tableName;
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, databaseUser, databasePassword);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public String getTableName() {
        return tableName;
    }
    public Connection getConnection() {
        return connection;
    }

    /**
     * @param rowName the row the data should be selected from
     * @param whereRow used in the sql WHERE statement
     * @param whereValue used in the sql WHERE statement
     * @return null, if an error occurred or no data was found
     */
    public String getString(String rowName, String whereRow, Object whereValue) {
        try (PreparedStatement statement = connection.prepareStatement(MessageFormat.format("SELECT * FROM {0} WHERE {1}={2};", tableName, whereRow, whereValue))) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return resultSet.getString(rowName);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    /**
     * @param rowName the row the data should be selected from
     * @param whereRow used in the sql WHERE statement
     * @param whereValue used in the sql WHERE statement
     * @return Integer.MAX_VALUE, if an error occurred or no data was found
     */
    public int getInt(String rowName, String whereRow, Object whereValue) {
        try (PreparedStatement statement = connection.prepareStatement(MessageFormat.format("SELECT * FROM {0} WHERE {1}={2};", tableName, whereRow, whereValue))) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt(rowName);
        } catch (SQLException e) { e.printStackTrace(); }
        return Integer.MAX_VALUE;
    }

    /**
     * Inserts data into the table
     * @param values has to contain your key row
     * @return whether the operation was successful
     */
    public boolean insert(Map<String, Object> values) {
        try {

            StringBuilder stringBuilder = new StringBuilder("INSERT INTO " + tableName + "(");
            for (String s : values.keySet()) {
                stringBuilder.append(s);
                stringBuilder.append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
            stringBuilder.append(") VALUES(");
            for (Object s : values.values()) {
                if (s instanceof String) stringBuilder.append('\'');
                stringBuilder.append(s);
                if (s instanceof String) stringBuilder.append('\'');
                stringBuilder.append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
            stringBuilder.append(")");

            PreparedStatement statement = connection.prepareStatement(stringBuilder.toString());
            statement.execute();
            statement.close();

            return true;

        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    /**
     * Inserts data into the table
     * @param keyRow the name of your key row
     * @return whether the operation was successful
     */
    public boolean insertAutoIncrement(String keyRow, Map<String, Object> values) {
        try {

            StringBuilder stringBuilder = new StringBuilder("INSERT INTO " + tableName + "(" + keyRow + ",");
            for (String s : values.keySet()) {
                stringBuilder.append(s);
                stringBuilder.append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
            stringBuilder.append(") VALUES(" + "NULL,");
            for (Object s : values.values()) {
                if (s instanceof String) stringBuilder.append('\'');
                stringBuilder.append(s);
                if (s instanceof String) stringBuilder.append('\'');
                stringBuilder.append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
            stringBuilder.append(")");

            System.out.println(stringBuilder.toString());

            PreparedStatement statement = connection.prepareStatement(stringBuilder.toString());
            statement.execute();
            statement.close();

            return true;

        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    /**
     * Inserts data into the table
     * Uses EasyTable#insertAutoIncrement() with "ID" as the key row
     * @return whether the operation was successful
     */
    public boolean insertAutoIncrement(Map<String, Object> values) {
        return insertAutoIncrement("ID", values);
    }

    /**
     * Updates data in the table
     * @param rowName The row to be updated
     * @param value The new value
     * @param whereRow used in the sql WHERE statement
     * @param whereValue used in the sql WHERE statement
     */
    public void update(String rowName, String value, String whereRow, String whereValue) {
        try (PreparedStatement statement = connection.prepareStatement(String.format("UPDATE '{0}' SET '{1}'='{2}' WHERE '{3}'='{4}'", tableName, rowName, value, whereRow, whereValue))) {
            statement.executeUpdate();
        } catch (SQLException e) {e.printStackTrace(); }
    }

}

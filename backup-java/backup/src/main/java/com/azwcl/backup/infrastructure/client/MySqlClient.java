package com.azwcl.backup.infrastructure.client;

import com.azwcl.backup.infrastructure.model.MySqlConnectionInfo;

import java.sql.*;

/**
 * MySQL client
 *
 * @author azwcl
 * @date 2023/06/18
 * @since v0.0.1
 */
public class MySqlClient {

    private static final String connectionUrl = "jdbc:mysql://%s:%d/%s?nullCatalogMeansCurrent=true";
    private Connection connection = null;
    private final MySqlConnectionInfo connectionInfo;

    /**
     * MySQL 客户端初始化
     *
     * @param connectionInfo 连接信息
     * @throws SQLException SQLException
     */
    public MySqlClient(MySqlConnectionInfo connectionInfo) throws SQLException {
        this.connectionInfo = connectionInfo;
        this.connection();
    }

    /**
     * 数据库连接
     *
     * @throws SQLException SQL Exception
     */
    private void connection() throws SQLException {
        String presentConnectionUrl = String.format(connectionUrl, connectionInfo.getHost(), connectionInfo.getPort(), connectionInfo.getDbName());
        this.connection = DriverManager.getConnection(presentConnectionUrl, connectionInfo.getUsername(), connectionInfo.getPassword());
    }

    /**
     * 关闭数据库连接
     *
     * @throws SQLException 数据库异常
     */
    public void close() throws SQLException {
        this.connection.close();
    }

    /**
     * 备份所有
     *
     * @return 返回所有的 SQL
     * @throws SQLException 数据库异常
     */

    public String backUpAll() throws SQLException {
        DatabaseMetaData metaData = this.connection.getMetaData();
        ResultSet tables = metaData.getTables(null, null, null, new String[]{"TABLE"});
        StringBuilder sql = new StringBuilder();
        while (tables.next()) {
            String tableName = tables.getString("TABLE_NAME");

            // table structure backup
            String createTableSql = generateCreateTableStatement(tableName);
            sql.append(createTableSql);

            // table records backup
            String insertDataSql = generateInsertDataStatements(connection, tableName);
            sql.append(insertDataSql);
        }
        return sql.toString();
    }

    /**
     * 生成创表 sql
     *
     * @param tableName 数据库表
     * @return 返回创表语句
     * @throws SQLException SQL 异常
     */
    private String generateCreateTableStatement(String tableName) throws SQLException {
        StringBuilder createTableSql = new StringBuilder();
        createTableSql.append("-- -----------------------------\n");
        createTableSql.append(String.format("-- table structure for %s\n", tableName));
        createTableSql.append("-- -----------------------------\n");

        Statement statement = this.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(String.format("SHOW CREATE TABLE %s", tableName));
        while (resultSet.next()) {
            return createTableSql.append(String.format("%s\n", resultSet.getString("Create Table"))).toString();
        }

        return null;
    }

    /**
     * 生成插入语句
     *
     * @param connection 连接信息
     * @param tableName  数据库表
     * @return 返回插入语句
     * @throws SQLException SQL 异常
     */
    private String generateInsertDataStatements(Connection connection, String tableName) throws SQLException {
        StringBuilder insertDataSql = new StringBuilder();
        insertDataSql.append("-- -----------------------------\n");
        insertDataSql.append("-- table records of ").append(tableName).append("\n");
        insertDataSql.append("-- -----------------------------\n");


        Statement statement = connection.createStatement();
        ResultSet data;
        try {
            data = statement.executeQuery("SELECT * FROM " + tableName);
        } catch (Exception e) {
            return "";
        }
        while (data.next()) {
            insertDataSql.append("INSERT INTO ").append(tableName).append(" VALUES (");
            for (int i = 1; i <= data.getMetaData().getColumnCount(); i++) {
                Object value = data.getObject(i);
                if (value != null) {
                    insertDataSql.append("'").append(value).append("',");
                } else {
                    insertDataSql.append("NULL,");
                }
            }
            insertDataSql.deleteCharAt(insertDataSql.lastIndexOf(","));
            insertDataSql.append(");\n");
        }
        insertDataSql.append("\n");

        statement.close();

        return insertDataSql.toString();
    }
}

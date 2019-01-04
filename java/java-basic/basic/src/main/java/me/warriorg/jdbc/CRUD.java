package me.warriorg.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.util.Properties;
import java.util.UUID;

public class CRUD {
    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final String dbURL = "jdbc:h2:~/test";
    private final String dbDriver = "org.h2.Driver";
    private final String dbUsername = "sa";
    private final String dbPassword = "";

    private final String tableSql = "CREATE TABLE IF NOT EXISTS Users\n" +
            "(\n" +
            "    id varchar(40),\n" +
            "    account varchar(20),\n" +
            "    password varchar(40),\n" +
            "    name varchar(20),\n" +
            "    create_date timestamp,\n" +
            "    disable boolean\n" +
            ");";

    public CRUD() {
        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException e) {
            logger.error("初始化驱动: " + e.getMessage(), e);
        }
    }

    private Connection getConnection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", dbUsername);
        props.setProperty("password", dbPassword);
        return DriverManager.getConnection(dbURL, props);
    }

    private void createTable() {
        try (Connection conn = getConnection()) {
            logger.debug("数据库连接状态： {}", conn);
            try (Statement statement = conn.createStatement()) {
                statement.execute(tableSql);
                logger.debug("数据库创建表成功");
            }
        } catch (SQLException ex) {
            logger.error("数据库连接错误: " + ex.getMessage(), ex);
        }
    }

    private void select() {
        String sql = "SELECT * FROM Users";

        try (Connection conn = getConnection()) {
            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet result = statement.executeQuery(sql);

            int rowcount = 0;
            if (result.last()) {
                rowcount = result.getRow();
                result.beforeFirst();
            }
            logger.debug("获取{}数据总数：{}", result.getMetaData().getTableName(1), rowcount);

            while (result.next()) {
                String id = result.getString("id");
                String account = result.getString(2);
                String password = result.getString(3);
                String name = result.getString("name");
                Object createDate = result.getObject(5);
                Object disable = result.getObject(6);

                logger.debug("获取{}数据 row:{} id:{} account:{} password:{} name:{} createDate:{} disable:{}",
                        result.getMetaData().getTableName(1), result.getRow(), id, account, password, name, createDate, disable);
            }
            result.close();
            statement.close();

        } catch (SQLException ex) {
            logger.error("数据库连接错误: " + ex.getMessage(), ex);
        }


    }

    private void insert() {
        String sql = "INSERT INTO Users (id, account, password, name, create_date, disable) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection()) {
            logger.debug("数据库连接状态： {}", conn);
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setObject(1, UUID.randomUUID().toString());
                statement.setObject(2, "admin");
                statement.setObject(3, "123456");
                statement.setObject(4, "admin");
                statement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                statement.setBoolean(6, false);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    logger.debug("插入数据成功");
                }
            }
        } catch (SQLException ex) {
            logger.error("数据库连接错误: " + ex.getMessage(), ex);
        }
    }

    private void update() {
        String sql = "UPDATE Users SET name = ? WHERE account = ?";
        try (Connection conn = getConnection()) {
            logger.debug("数据库连接状态： {}", conn);
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setObject(1, "不想当管理");
                statement.setObject(2, "admin");
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    logger.debug("更新数据成功");
                }
            }
        } catch (SQLException ex) {
            logger.error("数据库连接错误: " + ex.getMessage(), ex);
        }
    }

    private void delete() {
        String sql = "DELETE FROM Users WHERE account=?";

        try (Connection conn = getConnection()) {
            logger.debug("数据库连接状态： {}", conn);
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setObject(1, "admin");
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    logger.debug("删除数据成功");
                }
            }
        } catch (SQLException ex) {
            logger.error("数据库连接错误: " + ex.getMessage(), ex);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        CRUD crud = new CRUD();
        crud.createTable();
        crud.insert();
        crud.select();
        crud.update();
        crud.select();
        crud.delete();
        crud.select();
    }

}

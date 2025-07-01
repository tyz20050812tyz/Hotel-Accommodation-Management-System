package com.hotel.dao.util;

import java.sql.*;

/**
 * 数据库连接工具类
 * 编写者：开发小组
 * 完成时间：2024年7月
 * 功能：管理数据库连接的获取与资源释放
 */
public class JdbcUtil {
    // PostgreSQL连接信息（示例值，实际需根据环境修改）
    private static final String URL = "jdbc:postgresql://localhost:5432/hotel_management";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    /**
     * 获取数据库连接
     * @return 数据库连接对象
     * @throws SQLException 连接异常
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * 关闭数据库资源
     * @param conn 连接对象
     * @param stmt 语句对象
     * @param rs 结果集对象
     */
    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 静态代码块注册驱动
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
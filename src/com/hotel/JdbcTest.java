package com.hotel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcTest {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/hotel_management";
        String username = "root";// 数据库用户名
        String password = "123456";// 数据库密码

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            if (conn != null && !conn.isClosed()) {
                System.out.println("成功连接到PostgreSQL数据库");
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("无法连接到数据库");
            e.printStackTrace();
        }
    }
}
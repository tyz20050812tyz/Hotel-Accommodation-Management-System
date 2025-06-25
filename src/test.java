import java.sql.Connection;import java.sql.DriverManager;import java.sql.ResultSet;import java.sql.Statement;

public class test {
    public static void main(String[] args) {
        try {
           Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/hotel_management", "root", "123456");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM rooms");
            while (rs.next()) {
                // 处理结果
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
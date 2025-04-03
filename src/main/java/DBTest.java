import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;


public class DBTest {
    public static void main(String[] args) {
        Connection conn = null;

        try {
            Properties info = new Properties();

            String username = "root";
            String pass = "bywpassword";
            String schema = "coursescheduler";

            info.put("user", username);
            info.put("password", pass);

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + schema, info);

            System.out.println("Connection successful!");

            conn.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

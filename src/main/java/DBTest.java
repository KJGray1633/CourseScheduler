import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.sql.Statement;


public class DBTest {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Properties info = new Properties();

            String username = "root";
            String pass = "bywpassword";
            String schema = "coursescheduler";

            info.put("user", username);
            info.put("password", pass);

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + schema, info);

            System.out.println("Connection successful!");

            stmt  = conn.createStatement();
            String creatUsersTable = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "uid VARCHAR(10) NOT NULL, "
                    + "cid VARCHAR(20), ";
            stmt.execute(creatUsersTable);
            System.out.println("'users' table created successfully!");
            conn.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }




    }
}

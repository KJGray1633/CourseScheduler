import java.sql.*;
import java.util.Properties;

public class DBTest {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Properties info = new Properties();

            String username = "root";
            String pass = "bywpassword";
            String schema = "coursescheduler";

            info.put("user", username);
            info.put("password", pass);

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + schema, info);

            System.out.println("Connection successful!");

            // Drop the table if it exists
            String dropTableSQL = "DROP TABLE IF EXISTS users";
            pstmt = conn.prepareStatement(dropTableSQL);
            pstmt.executeUpdate();
            System.out.println("Table 'users' dropped successfully!");

            // Create the new table
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users ("
                    + "uid VARCHAR(10) NOT NULL, "
                    + "cid VARCHAR(20), "
                    + "PRIMARY KEY (uid, cid))";
            pstmt = conn.prepareStatement(createUsersTable);
            pstmt.executeUpdate();
            System.out.println("'users' table created successfully!");

            conn.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
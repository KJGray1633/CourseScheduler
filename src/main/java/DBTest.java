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
                    + "uid INT NOT NULL, "
                    + "username VARCHAR(50), "
                    + "password VARCHAR(50), "
                    + "major VARCHAR(50), "
                    + "year VARCHAR(50), "
                    + "PRIMARY KEY (uid))";
            pstmt = conn.prepareStatement(createUsersTable);
            pstmt.executeUpdate();
            System.out.println("'users' table created successfully!");

            // Drop the courses table if it exists
            String dropCourseTable = "DROP TABLE IF EXISTS courses";
            pstmt = conn.prepareStatement(dropCourseTable);
            pstmt.executeUpdate();
            System.out.println("Table 'courses' dropped successfully!");

            // Create the courses table
            String createCoursesTable = "CREATE TABLE IF NOT EXISTS courses ("
                    + "cid INT NOT NULL, "
                    + "name VARCHAR(50), "
                    + "courseCode VARCHAR(10), "
                    + "times VARCHAR(50), "
                    + "referenceNum INT, "
                    + "PRIMARY KEY (cid))";

            pstmt = conn.prepareStatement(createCoursesTable);
            pstmt.executeUpdate();
            System.out.println("'courses' table created successfully!");

            // Drop the schedule table if it exists
            String dropScheduleTable = "DROP TABLE IF EXISTS schedule";
            pstmt = conn.prepareStatement(dropScheduleTable);
            pstmt.executeUpdate();
            System.out.println("Table 'schedule' dropped successfully!");

            // Create the schedule table
            String createScheduleTable = "CREATE TABLE IF NOT EXISTS schedule ("
                    + "cid INT NOT NULL, "
                    + "uid INT NOT NULL, "
                    + "PRIMARY KEY (cid, uid))";

            pstmt = conn.prepareStatement(createScheduleTable);
            pstmt.executeUpdate();
            System.out.println("'schedule' table created successfully!");

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
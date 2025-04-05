import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DBTest {
    public static void main(String[] args) {
        Connection conn = null;
        ArrayList<Course> courses = Search.parseJSON();
        try {
            Properties info = new Properties();

            String username = "root";
            String pass = "bywpassword";
            String schema = "coursescheduler";

            info.put("user", username);
            info.put("password", pass);

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + schema, info);

            System.out.println("Connection successful!");

            createTables(conn);
            if (courses == null) {
                System.out.println("No courses found.");
                return;
            }
            insertCoursesIntoDatabase(conn, courses);
            exampleUser(conn);

            conn.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void exampleUser(Connection conn) throws SQLException {
        String exampleInsertUser = "INSERT INTO users (uid, username, password, major, year) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(exampleInsertUser);
        pstmt.setInt(1, 1);
        pstmt.setString(2, "Sarah");
        pstmt.setString(3, "password");
        pstmt.setString(4, "Computer Science");
        pstmt.setString(5, "2026");
        pstmt.executeUpdate();
        System.out.println("Example user inserted successfully!");

    }


    public static void createTables(Connection conn) throws SQLException {
        PreparedStatement pstmt;
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
                + "times VARCHAR(200), "
                + "referenceNum INT, "
                + "location VARCHAR(100), "
                + "openSeats INT, "
                + "section VARCHAR(10), "
                + "semester VARCHAR(20), "
                + "subject VARCHAR(50), "
                + "totalSeats INT, "
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

    }

    public static void insertCoursesIntoDatabase(Connection conn, ArrayList<Course> courses) {
        try {

            String insertSQL = "INSERT INTO courses (cid, name, courseCode, times, referenceNum, location, openSeats, section, semester, subject, totalSeats) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);

            for (Course course : courses) {
                pstmt.setInt(1, course.getCid());
                pstmt.setString(2, course.getName());
                pstmt.setString(3, String.valueOf(course.getCourseCode()));
                pstmt.setString(4, course.getTimes().toString());
                pstmt.setInt(5, course.getReferenceNum());
                pstmt.setString(6, course.getLocation());
                pstmt.setInt(7, course.getOpenSeats());
                pstmt.setString(8, course.getSection());
                pstmt.setString(9, course.getSemester());
                pstmt.setString(10, course.getSubject());
                pstmt.setInt(11, course.getTotalSeats());
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            System.out.println("Courses inserted successfully!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
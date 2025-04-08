import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DBCreate {
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

            dropAllTables(conn);
            createAllTables(conn);
            if (courses == null) {
                System.out.println("No courses found.");
                return;
            }
            insertCoursesIntoDatabase(conn, courses);

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


    public static void createAllTables(Connection conn) throws SQLException {
        PreparedStatement pstmt;

        // Create the users table
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users ("
                + "uid INT NOT NULL AUTO_INCREMENT, "
                + "username VARCHAR(50), "
                + "password VARCHAR(50), "
                + "major VARCHAR(50), "
                + "year VARCHAR(50), "
                + "PRIMARY KEY (uid))";
        pstmt = conn.prepareStatement(createUsersTable);
        pstmt.executeUpdate();
        System.out.println("'users' table created successfully!");

        // Create the courses table
        String createCoursesTable = "CREATE TABLE IF NOT EXISTS courses ("
                + "cid INT NOT NULL, "
                + "name VARCHAR(50), "
                + "credits INT, "
                + "courseCode VARCHAR(10), "
                + "times VARCHAR(200), "
                + "referenceNum INT, "
                + "location VARCHAR(100), "
                + "openSeats INT, "
                + "professor VARCHAR(200), "
                + "section VARCHAR(10), "
                + "semester VARCHAR(20), "
                + "subject VARCHAR(50), "
                + "totalSeats INT, "
                + "PRIMARY KEY (cid))";

        pstmt = conn.prepareStatement(createCoursesTable);
        pstmt.executeUpdate();
        System.out.println("'courses' table created successfully!");

        // Create the schedule table
        String createScheduleTable = "CREATE TABLE IF NOT EXISTS schedule ("
                + "cid INT NOT NULL, "
                + "uid INT NOT NULL, "
                + "PRIMARY KEY (cid, uid))";

        pstmt = conn.prepareStatement(createScheduleTable);
        pstmt.executeUpdate();
        System.out.println("'schedule' table created successfully!");

    }

    public static void dropAllTables(Connection conn) throws SQLException {
        String dropScheduleTable = "DROP TABLE IF EXISTS schedule";
        String dropCourseTable = "DROP TABLE IF EXISTS courses";
        String dropUserTable = "DROP TABLE IF EXISTS users";

        Statement stmt = conn.createStatement();
        stmt.executeUpdate(dropScheduleTable);
        stmt.executeUpdate(dropCourseTable);
        stmt.executeUpdate(dropUserTable);
    }

    public static void insertCoursesIntoDatabase(Connection conn, ArrayList<Course> courses) {
        try {

            String insertSQL = "INSERT INTO courses (cid, name, credits, courseCode, times, referenceNum, location, openSeats, professor, section, semester, subject, totalSeats) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);

            for (Course course : courses) {
                pstmt.setInt(1, course.getCid());
                pstmt.setString(2, course.getName());
                pstmt.setInt(3, course.getCredits());
                pstmt.setString(4, String.valueOf(course.getCourseCode()));
                pstmt.setString(5, course.getTimes().toString());
                pstmt.setInt(6, course.getReferenceNum());
                pstmt.setString(7, course.getLocation());
                pstmt.setInt(8, course.getOpenSeats());
                pstmt.setString(9, course.getProfessor().toString());
                pstmt.setString(10, course.getSection());
                pstmt.setString(11, course.getSemester());
                pstmt.setString(12, course.getSubject());
                pstmt.setInt(13, course.getTotalSeats());
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            System.out.println("Courses inserted successfully!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
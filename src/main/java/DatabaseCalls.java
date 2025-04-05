import java.util.ArrayList;
import java.sql.*;
import java.util.Properties;

public class DatabaseCalls {

    private static Connection conn = null;

    public static boolean connectToDB() {
        if (conn != null) {
            return true; // Already connected
        }
        try {
            Properties info = new Properties();
            String username = "root";
            String pass = "bywpassword";
            String schema = "coursescheduler";
            info.put("user", username);
            info.put("password", pass);
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + schema, info);
//            System.out.println("Connection successful!");
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
//                System.out.println("Connection closed.");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean addCourse(int uid, int cid) {
        if (!connectToDB()) {
            return false; // Failed to connect to DB
        }

        // check if user exists in the database
        String checkUserSQL = "SELECT * FROM users WHERE uid = ?";
        try (Connection conn = this.conn;
             PreparedStatement pstmt = conn.prepareStatement(checkUserSQL)) {
            pstmt.setInt(1, uid);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
//                System.out.println("User exists in the database.");

                // check if course exists in the database
                String checkCourseSQL = "SELECT * FROM courses WHERE cid = ?";
                try (PreparedStatement pstmt2 = conn.prepareStatement(checkCourseSQL)) {
                    pstmt2.setInt(1, cid);
                    rs = pstmt2.executeQuery();
                    if (rs.next()) {
//                        System.out.println("Course exists in the database.");

                        // check if course is already in the schedule
                        String checkScheduleSQL = "SELECT * FROM schedule WHERE uid = ? AND cid = ?";
                        try (PreparedStatement pstmt3 = conn.prepareStatement(checkScheduleSQL)) {
                            pstmt3.setInt(1, uid);
                            pstmt3.setInt(2, cid);
                            rs = pstmt3.executeQuery();
                            if (rs.next()) {
//                                System.out.println("Course already in the schedule.");
                                return false;
                            }
                        }

                        // add course to the schedule
                        String addCourseSQL = "INSERT INTO schedule (cid, uid) VALUES (?, ?)";
                        try (PreparedStatement pstmt3 = conn.prepareStatement(addCourseSQL)) {
                            pstmt3.setInt(1, cid);
                            pstmt3.setInt(2, uid);
                            pstmt3.executeUpdate();
//                            System.out.println("Course added to the schedule.");
                            return true;
                        }
                    } else {
//                        System.out.println("Course does not exist in the database.");
                        return false;
                    }
                }
            } else {
//                System.out.println("User does not exist in the database.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } finally {
            closeConnection();
        }
    }

    public boolean dropCourse(int uid, int cid) {
        if (!connectToDB()) {
            return false; // Failed to connect to DB
        }

        // check if user exists in the database
        String checkUserSQL = "SELECT * FROM users WHERE uid = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(checkUserSQL)) {
            pstmt.setInt(1, uid);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
//                System.out.println("User exists in the database.");

                // check if course exists in the database
                String checkCourseSQL = "SELECT * FROM courses WHERE cid = ?";
                try (PreparedStatement pstmt2 = conn.prepareStatement(checkCourseSQL)) {
                    pstmt2.setInt(1, cid);
                    rs = pstmt2.executeQuery();
                    if (rs.next()) {
//                        System.out.println("Course exists in the database.");

                        // check if course is in the schedule
                        String checkScheduleSQL = "SELECT * FROM schedule WHERE uid = ? AND cid = ?";
                        try (PreparedStatement pstmt3 = conn.prepareStatement(checkScheduleSQL)) {
                            pstmt3.setInt(1, uid);
                            pstmt3.setInt(2, cid);
                            rs = pstmt3.executeQuery();
                            if (rs.next()) {
//                                System.out.println("Course is in the schedule.");

                                // remove course from the schedule
                                String removeCourseSQL = "DELETE FROM schedule WHERE cid = ? AND uid = ?";
                                try (PreparedStatement pstmt4 = conn.prepareStatement(removeCourseSQL)) {
                                    pstmt4.setInt(1, cid);
                                    pstmt4.setInt(2, uid);
                                    pstmt4.executeUpdate();
//                                    System.out.println("Course removed from the schedule.");
                                    return true;
                                }
                            } else {
//                                System.out.println("Course is not in the schedule.");
                                return false;
                            }
                        }
                    } else {
//                        System.out.println("Course does not exist in the database.");
                        return false;
                    }
                }
            } else {
//                System.out.println("User does not exist in the database.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } finally {
            closeConnection();
        }
    }


    public ArrayList<Course> searchCourses(String query) {
        return null;
    }

    public User validateUser(String username, String password) {
        return null;
    }

    public Course getCourse(int cid) {
        return null;
    }

    public Schedule getSchedule(int uid) {
        return null;
    }
}
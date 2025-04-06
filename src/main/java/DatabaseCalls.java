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

    public boolean validateUser(String username, String password) {
        if (!connectToDB()) {
            return false; // Failed to connect to DB
        }

        // check to see if username/password are in the database
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean addUser(int uid, String username, String password, String major, String year) {
        if (!connectToDB()) {
            return false; // Failed to connect to DB
        }

        String addUser = "INSERT INTO users (uid, username, password, major, year) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(addUser)) {
            pstmt.setInt(1, uid);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, major);
            pstmt.setString(5, year);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } finally {
            closeConnection();
        }
    }

    public boolean removeUser(int uid) {
        if (!connectToDB()) {
            return false; // Failed to connect to DB
        }

        String removeUser = "DELETE FROM users WHERE uid = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(removeUser)) {
            pstmt.setInt(1, uid);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } finally {
            closeConnection();
        }
    }



    public Course getCourse(int cid) {
        if (!connectToDB()) {
            return null; // Failed to connect to DB
        }
        // check if course exists in the database
        String checkCourseSQL = "SELECT * FROM courses WHERE cid = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(checkCourseSQL)) {
            pstmt.setInt(1, cid);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                int courseCode = rs.getInt("courseCode");
                return new Course(cid, name, courseCode);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeConnection();
        }
        return null; // Course not found
    }

    public Schedule getSchedule(int uid) {
        return null;
    }
}
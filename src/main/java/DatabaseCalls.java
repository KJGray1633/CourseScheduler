import java.util.ArrayList;
import java.sql.*;
import java.util.Properties;

public class DatabaseCalls {

    private static Connection conn = null;

    public static boolean connectToDB() {
        if (conn != null) {
            return true;
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

    private static boolean userExists(int uid) {
        String checkUserSQL = "SELECT * FROM users WHERE uid = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(checkUserSQL)) {
            pstmt.setInt(1, uid);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    private static boolean courseExists(int cid) {
        String checkCourseSQL = "SELECT * FROM courses WHERE cid = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(checkCourseSQL)) {
            pstmt.setInt(1, cid);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    private static boolean inSchedule(int uid, int cid) {
        String checkScheduleSQL = "SELECT * FROM schedule WHERE uid = ? AND cid = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(checkScheduleSQL)) {
            pstmt.setInt(1, uid);
            pstmt.setInt(2, cid);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public boolean addCourse(int uid, int cid) {
        if (!connectToDB()) {
            return false;
        }
        if (userExists(uid) && courseExists(cid) && !inSchedule(uid, cid)) {
            String addCourseSQL = "INSERT INTO schedule (cid, uid) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(addCourseSQL)) {
                pstmt.setInt(1, cid);
                pstmt.setInt(2, uid);
                pstmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                closeConnection();
            }
        }
        return false;
    }

    public boolean dropCourse(int uid, int cid) {
        if (!connectToDB()) {
            return false;
        }

        if (userExists(uid) && courseExists(cid) && inSchedule(uid, cid)) {
            String removeCourseSQL = "DELETE FROM schedule WHERE cid = ? AND uid = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(removeCourseSQL)) {
                pstmt.setInt(1, cid);
                pstmt.setInt(2, uid);
                pstmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                closeConnection();
            }
        }
        return false;
    }


    public boolean validateUser(String username, String password) {
        if (!connectToDB()) {
            return false;
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

    public int addUser(String username, String password, String major, String year) {
        int uid = -1;
        if (!connectToDB()) {
            return -1;
        }
        String addUser = "INSERT INTO users (username, password, major, year) VALUES ( ?, ?, ?, ?)";
        String getUid = "SELECT uid FROM users WHERE username = ? AND password = ? AND major = ? AND year = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(addUser)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, major);
            pstmt.setString(4, year);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            closeConnection();
            return -1;
        } try (PreparedStatement pstmt = conn.prepareStatement(getUid)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, major);
            pstmt.setString(4, year);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                uid = rs.getInt("uid");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            closeConnection();
            return -1;
        } finally {
            closeConnection();
        }
        return uid;
    }

    public boolean removeUser(int uid) {
        if (!connectToDB()) {
            return false;
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

    public User getUser() {
        if (!connectToDB()) {
            return null;
        }


    }


    public Course getCourse(int cid) {
        if (!connectToDB()) {
            return null;
        }

        if (courseExists(cid)) {
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
        }
        return null;
    }

    public Schedule getSchedule(int uid) {
        if (!connectToDB()) {
            return null;
        }
        if (userExists(uid)) {
            String getScheduleSQL = "SELECT * FROM schedule WHERE uid = ?";
            try (PreparedStatement pstmt2 = conn.prepareStatement(getScheduleSQL)) {
                pstmt2.setInt(1, uid);
                ResultSet rs2 = pstmt2.executeQuery();
                ArrayList<Integer> cids = new ArrayList<>();
                while (rs2.next()) {
                    cids.add(rs2.getInt("cid"));
                }
                return new Schedule(cids);
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            } finally {
                closeConnection();
            }
        }
        return null;
    }
}
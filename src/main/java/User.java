import java.util.ArrayList;

public class User {
    private int uid;
    private String username;
    private String password;
    private String courseHistory;
    private String major;
    private String year;
    private DatabaseCalls dbc = new DatabaseCalls();

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String username, String password, String major, String year) {
        this.username = username;
        this.password = password;
        this.major = major;
        this.year = year;
        this.uid = dbc.addUser(this.username, this.password, this.major, this.year);
    }

    public User(int uid) {
        this.uid = uid;
        this.username = dbc.getUser(uid).getUsername();
        this.major = dbc.getUser(uid).getMajor();
        this.year = dbc.getUser(uid).getYear();
        this.password = dbc.getUser(uid).getPassword();
    }

    public int getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getCourseHistory() {
        var c = dbc.getUserCourseHistory(uid);
        return c != null ? c : "";
    }

    public void setCourseHistory(String courseHistory) {
        this.courseHistory = courseHistory;
        dbc.saveUserCourseHistory(this.uid, this.courseHistory);
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
        dbc.saveUserMajor(this.uid, this.major);
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
        dbc.saveUserYear(this.uid, this.year);
    }
}

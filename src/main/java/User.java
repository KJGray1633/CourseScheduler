import java.util.ArrayList;

public class User {
    private int uid;
    private String username;
    private String password;
    private ArrayList<Course> courseHistory;
    private String major;
    private String year;

    public User(int uid, String username, String password, String major, String year) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.major = major;
        this.year = year;
    }

    public User(int uid) {

    }

    public int getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Course> getCourseHistory() {
        return courseHistory;
    }

    public String getMajor() {
        return major;
    }

    public String getYear() {
        return year;
    }
}

import java.util.ArrayList;
//Made changes
public class User {
    private int uid;
    private String username;
    private String password;
    private ArrayList<Course> courseHistory;
    private String major;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String year;
    private DatabaseCalls dbc = new DatabaseCalls();

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

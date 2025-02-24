import java.util.ArrayList;

public class DatabaseConnection {

    private ArrayList<Integer> uCourses = new ArrayList<Integer>();

    public boolean addCourse(int uid, int cid) {

        if (getCourse(cid) == null) {
            return false;
        }
        else {
            //database call: UPDATE user SET cid = cid WHERE uid = uid

            // without db call
            uCourses.add(cid);
        }
        return false;
    }

    public boolean dropCourse(int uid, int cid) {
        return false;
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

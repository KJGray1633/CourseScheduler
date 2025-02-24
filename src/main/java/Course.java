import java.util.ArrayList;

public class Course {
    private int cid;
    private String name;
    private String courseCode;
    private String description;
    private ArrayList<MeetingTime> times;
    private int referenceNum;

    public Course(int cid) {

    }

    public int getCid() {
        return cid;
    }

    public String getName() {
        return name;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<MeetingTime> getTimes() {
        return times;
    }

    public int getReferenceNum() {
        return referenceNum;
    }

    public boolean isOverlap(Course course) {
        return false;
    }
}

import java.util.ArrayList;

public class Course {
    private int cid;
    private String name;
    private String courseCode;
    private String description;
    private String professor;
    private ArrayList<MeetingTime> times;
    private int referenceNum;

    public Course(int cid) {

    }

    public Course(int cid, String name, String courseCode, String professor) {
        this.cid = cid;
        this.name = name;
        this.courseCode = courseCode;
        this.professor = professor;
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

    public String getProfessor() {return professor;}

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

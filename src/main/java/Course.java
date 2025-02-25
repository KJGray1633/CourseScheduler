import java.util.ArrayList;

public class Course {
    private int cid;
    private String name;
    private int courseCode;
    private String description;
    private ArrayList<MeetingTime> times;
    private int referenceNum;

    public Course(int cid) {
        this.cid = cid;
        times = new ArrayList<>();

    }

    public int getCid() {
        return cid;
    }

    public String getName() {
        return name;
    }

    public int getCourseCode() {
        return courseCode;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<MeetingTime> getTimes() {
        return times;
    }

    public void setCourseCode(int courseCode) {
        this.courseCode = courseCode;
    }

    public int getReferenceNum() {
        return referenceNum;
    }

    public boolean isOverlap(Course course) {
        return false;
    }

    public void setTimes(ArrayList<MeetingTime> times) {
        this.times = times;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Course{" +
                "cid=" + cid +
                ", name='" + name + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", description='" + description + '\'' +
                ", times=" + times +
                ", referenceNum=" + referenceNum +
                '}';
    }
}

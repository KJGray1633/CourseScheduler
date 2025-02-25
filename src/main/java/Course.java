import java.util.ArrayList;

public class Course {
    private int cid;
    private String name;
    private int courseCode;
    private String description;
    private ArrayList<String> professor;
    protected ArrayList<MeetingTime> times;
    private int referenceNum;

    public Course(int cid) {
        this.cid = cid;
        times = new ArrayList<>();

    }

    public Course(int cid, String name, int courseCode) {
        this.cid = cid;
        this.name = name;
        this.courseCode = courseCode;
        this.professor = new ArrayList<>();
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

    public ArrayList<String> getProfessor() {
        return professor;
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
        // get the list of times of the other course
        ArrayList<MeetingTime> otherTimes = course.getTimes();
        // compare each time
        for (MeetingTime otherTime : otherTimes) {
            for (MeetingTime time : times) {
                // if both times are on the same day, compare times
                if (time.getDay().equals(otherTime.getDay())) {
                    // if this time completely overlaps with the other, return true
                    if (time.getStartTime().getTime() <= otherTime.getStartTime().getTime()
                            && time.getEndTime().getTime() >= otherTime.getEndTime().getTime()) {
                        return true;
                    }
                    // if this time finishes during the other, return true
                    if (time.getEndTime().getTime() > otherTime.getStartTime().getTime()
                            && time.getEndTime().getTime() < otherTime.getEndTime().getTime()) {
                        return true;
                    }
                    // if this time starts during the other, return true
                    if (time.getStartTime().getTime() > otherTime.getStartTime().getTime()
                            && time.getStartTime().getTime() < otherTime.getEndTime().getTime()) {
                        return true;
                    }
                }
            }
        }
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

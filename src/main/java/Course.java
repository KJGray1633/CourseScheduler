import java.sql.Time;
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
}

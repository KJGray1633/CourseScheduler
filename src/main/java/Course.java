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

    // testing isOverlap
    public static void main(String[] args) {
        Course c1 = new Course(0);
        c1.times = new ArrayList<>();
        c1.times.add(new MeetingTime(new Time(1), new Time(10), "Monday"));
        Course c2 = new Course(1);
        c2.times = new ArrayList<>();
        c2.times.add(new MeetingTime(new Time(1), new Time(10), "Monday"));
        System.out.println(c1.isOverlap(c2));
        Course c3 = new Course(2);
        c3.times = new ArrayList<>();
        c3.times.add(new MeetingTime(new Time(5), new Time(10), "Monday"));
        System.out.println(c1.isOverlap(c3));
        Course c4 = new Course(3);
        c4.times = new ArrayList<>();
        c4.times.add(new MeetingTime(new Time(10), new Time(20), "Monday"));
        System.out.println(c1.isOverlap(c4));
        Course c5 = new Course(4);
        c5.times = new ArrayList<>();
        c5.times.add(new MeetingTime(new Time(1), new Time(10), "Tuesday"));
        System.out.println(c1.isOverlap(c5));
    }
}

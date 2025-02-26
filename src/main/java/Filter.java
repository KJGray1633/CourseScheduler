import java.sql.Time;
import java.util.ArrayList;

public class Filter {
    // completed Days enum
    public enum Days {
        MWF,
        TR
    }

    private Time startTime;
    private Time endTime;
    private ArrayList<String> prof;
    private Days days;
    private String department;
    private int courseCode;

    public int getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(int courseCode) {
        this.courseCode = courseCode;
    }

    //Hello
    public Filter() {

    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public ArrayList<String> getProf() {
        return prof;
    }

//    public void setProf(String prof) {
//        this.prof = prof;
//    }

    public Days getDays() {
        return days;
    }

    public void setDays(Days days) {
        this.days = days;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public ArrayList<Course> filterSearch(ArrayList<Course> searchResults) {
        return null;
    }
}

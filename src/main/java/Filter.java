import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Filter {
    private List<Day> days = new ArrayList<>();
    private String department;
    private int courseCode;
    private String name;
    private List<String> prof = new ArrayList<>();
    private Time startTime;
    private Time endTime;

    // Getters and setters for the fields
    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(int courseCode) {
        this.courseCode = courseCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getProf() {
        return prof;
    }

    public void setProf(List<String> prof) {
        this.prof = prof;
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
}

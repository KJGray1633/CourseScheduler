import java.util.ArrayList;

public class Schedule {
    private ArrayList<Course> courses;

    public Schedule(int uid) {
        courses = new ArrayList<>();
        courses.add(new Course(1,"Programming 1", "COMP141","Programming Prof 1"));
        courses.add(new Course(2,"Foundations of Academic Discourse", "WRIT101","English Prof 1"));
        courses.add(new Course(3,"Principles of Accounting","ACCT201","Accounting Prof 1"));
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public boolean addCourse(Course course) {
        return false;
    }

    public boolean dropCourse(Course course) {
        courses.remove(course);
        return true;
    }
}

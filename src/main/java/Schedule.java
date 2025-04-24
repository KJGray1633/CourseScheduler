import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Schedule {
    public ArrayList<Course> courses;
    DatabaseCalls dbc = new DatabaseCalls();
    int uid;

    public Schedule(int uid) {
//        courses.add(new Course(1,"Programming 1", 141));
//        courses.add(new Course(2,"Foundations of Academic Discourse", 101));
//        courses.add(new Course(3,"Principles of Accounting",201));
        courses = new ArrayList<>();
        this.uid = uid;
        courses = dbc.getSchedule(uid).getCourses();
    }

    public Schedule(ArrayList<Integer> cids) {
        courses = new ArrayList<>();
        ArrayList<Course> allCourses = Search.parseJSON();
        for (Course c : allCourses) {
            if (cids.contains(c.getCid())) {
                courses.add(c);
            }
        }
    }

    public Schedule() {
        courses = new ArrayList<>();
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get("schedule.json")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        Search search = new Search();
        JSONObject json = new JSONObject(content);
        JSONArray jsonCourses = json.getJSONArray("courses");
        for (int i = 0; i < jsonCourses.length(); i++) {
            JSONObject jsonCourse = jsonCourses.getJSONObject(i);
            int cid = jsonCourse.getInt("cid");
            Course course = search.createCourseFromCid(cid);
            courses.add(course);
        }
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public boolean addCourse(Course course) {
        for (Course c : courses) {
            if (c.isOverlap(course)) {
                return false;
            }
        }
        courses.add(course);
        dbc.addCourse(uid, course.getCid());
        return true;
    }

    public boolean dropCourse(Course course) {
        for (Course c : courses) {
            if (c.getCid() == course.getCid()) {
                courses.remove(c);
                break;
            }
        }
        dbc.dropCourse(uid, course.getCid());
        return true;
    }

    public boolean containsCourseId(int cid) {
        for (Course course : courses) {
            if (course.getCid() == cid) {
                return true;
            }
        }
        return false;
    }
}

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
        this.uid = uid;
        dbc.getSchedule(uid);
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
        courses.add(course);
        saveSchedule();
        return true;
    }

    public boolean dropCourse(Course course) {
        courses.remove(course);
        saveSchedule();
        return true;
    }

    public boolean saveSchedule() {
        for(int i = 0; i < courses.size(); i++){
            dbc.addCourse(uid, courses.get(i).getCid());
        }
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

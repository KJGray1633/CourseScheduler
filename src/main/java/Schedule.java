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

    public Schedule(int uid) {
//        courses.add(new Course(1,"Programming 1", 141));
//        courses.add(new Course(2,"Foundations of Academic Discourse", 101));
//        courses.add(new Course(3,"Principles of Accounting",201));
        this();
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
        saveSchedule();
        return true;
    }

    public boolean dropCourse(Course course) {
        for (Course c : courses) {
            if (c.getCid() == course.getCid()) {
                courses.remove(c);
                break;
            }
        }
        saveSchedule();
        return true;
    }

    public boolean saveSchedule() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("courses", courses);

        try (FileWriter file = new FileWriter("schedule.json")) {
            file.write(jsonObject.toString(2)); // Use toString(2) for pretty printing
            file.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
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

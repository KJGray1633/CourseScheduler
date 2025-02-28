import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

public class Schedule {
    protected ArrayList<Course> courses;

    public Schedule(int uid) {
//        courses = new ArrayList<>();
//        courses.add(new Course(1,"Programming 1", 0,"Programming Prof 1"));
//        courses.add(new Course(2,"Foundations of Academic Discourse", 2,"English Prof 1"));
//        courses.add(new Course(3,"Principles of Accounting",2,"Accounting Prof 1"));
        courses = loadSchedule();
    }

    public ArrayList<Course> loadSchedule() {
        ArrayList<Course> tempCourses = new ArrayList<>();
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get("schedule.json")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        JSONObject json = new JSONObject(content);
        JSONArray jsonCourses = json.getJSONArray("courses");
        for (int i = 0; i < jsonCourses.length(); i++) {
            JSONObject jsonCourse = jsonCourses.getJSONObject(i);
            int referenceNum = jsonCourse.getInt("referenceNum");
            String professor = jsonCourse.getString("professor");
            int courseCode = jsonCourse.getInt("courseCode");
            String name = jsonCourse.getString("name");
            int cid = jsonCourse.getInt("cid");
            Course course = new Course(cid, name, courseCode, professor);
            tempCourses.add(course);
        }

        return tempCourses;
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

    public boolean saveSchedule() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("courses", courses);

        try (FileWriter file = new FileWriter("schedule.json")) {
            file.write(jsonObject.toString(2)); // Use toString(2) for pretty printing
            file.flush();
            System.out.print("JSON data written to schedule.json");
            return true;
        } catch (IOException e) {
            System.err.print("Failed to write JSON data to schedule.json");
            return false;
        }
    }
}

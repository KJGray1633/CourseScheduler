import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

public class Schedule {
    private static final Logger logger = Logger.getLogger(Schedule.class.getName());
    public ArrayList<Course> courses;

    public Schedule(int uid) {
        courses = new ArrayList<>();
        courses.add(new Course(1, "Programming 1", 141));
        courses.add(new Course(2, "Foundations of Academic Discourse", 101));
        courses.add(new Course(3, "Principles of Accounting", 201));
        logger.log(Level.INFO, "Schedule created with default courses for user id: {0}", uid);
    }

    public Schedule(String fileName) {
        courses = new ArrayList<>();
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(fileName)));
            logger.log(Level.INFO, "Read schedule from file: {0}", fileName);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to read schedule from file: {0}", fileName);
            System.out.println(e.getMessage());
            return;
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
            //Course course = new Course(cid, name, courseCode, professor);
            //courses.add(course);
        }
        logger.log(Level.INFO, "Schedule loaded from file: {0}", fileName);
    }

    public ArrayList<Course> getCourses() {
        logger.log(Level.INFO, "Getting list of courses");
        return courses;
    }

    public boolean addCourse(Course course) {
        logger.log(Level.INFO, "Adding course: {0}", course);
        return false;
    }

    public boolean dropCourse(Course course) {
        logger.log(Level.INFO, "Dropping course: {0}", course);
        courses.remove(course);
        return true;
    }

    public boolean saveSchedule() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("courses", courses);

        try (FileWriter file = new FileWriter("schedule.json")) {
            file.write(jsonObject.toString(2)); // Use toString(2) for pretty printing
            file.flush();
            logger.log(Level.INFO, "Schedule saved to schedule.json");
            System.out.print("JSON data written to schedule.json");
            return true;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to write JSON data to schedule.json", e);
            System.err.print("Failed to write JSON data to schedule.json");
            return false;
        }
    }
}
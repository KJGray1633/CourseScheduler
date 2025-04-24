import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class RecommendedSchedule {

    private ArrayList<Course> courses;

    public RecommendedSchedule(String major, int desiredCredits) {
        courses = new ArrayList<>();
        String result;
        try {
            String userDirectory = System.getProperty("user.dir");
            //filePath + "/../../../scripts/recommended_courses.py "
            String path = userDirectory + "/scripts/recommended_courses.py";
            String commandString = "python " + userDirectory + "/scripts/recommended_courses.py " +  major + " " + desiredCredits;
            ProcessBuilder pb = new ProcessBuilder("python3",path,major,desiredCredits+"");
            Process process = pb.start();//Runtime.getRuntime().exec(commandString);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            result = output.toString();
            //System.out.println(result);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        result = "[\n" +
                "    94,\n" +
                "    127,\n" +
                "    748,\n" +
                "    133,\n" +
                "    330\n" +
                "]";

        String[] courseIDs = result.replaceAll("[\\[\\] ]", "").split(",");
        Search search = new Search();
        for (String courseID : courseIDs) {
            int id = Integer.parseInt(courseID.strip());
            Course course = search.createCourseFromCid(id);
            courses.add(course);
        }
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public static void main(String[] args) {
        RecommendedSchedule schedule = new RecommendedSchedule("computerScience", 15);
        for (Course course : schedule.courses) {
            System.out.println(course.getName() + " - " + course.getCid());
        }
    }
}

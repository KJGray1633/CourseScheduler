import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;


public class RecommendedSchedule {

    private ArrayList<Course> courses;
    private Search search;
    private DatabaseCalls dbc;

    public RecommendedSchedule(String major, int desiredCredits) {
        search = new Search();
        courses = new ArrayList<>();
        dbc = new DatabaseCalls();
        String result;
//        try {
//            String userDirectory = System.getProperty("user.dir");
//            //filePath + "/../../../scripts/recommended_courses.py "
//            String path = userDirectory + "/scripts/recommended_courses.py";
//            String commandString = "python " + userDirectory + "/scripts/recommended_courses.py " +  major + " " + desiredCredits;
//            ProcessBuilder pb = new ProcessBuilder("python3",path,major,desiredCredits+"");
//            Process process = pb.start();//Runtime.getRuntime().exec(commandString);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            StringBuilder output = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                output.append(line).append("\n");
//            }
//            result = output.toString();
//            //System.out.println(result);
//        }
//        catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
        // Get course history
        String courseHistory = dbc.getUserCourseHistory(1);
        // Convert course history into an array list of required course info
        ArrayList<RequiredCourseInfo> takeCourseInfo = new ArrayList<>();
        if (courseHistory != null && !courseHistory.isEmpty()) {
            String[] courses = courseHistory.split(",");
            for (String course : courses) {
                String[] parts = course.trim().split(" ");
                if (parts.length == 2) {
                    String department = parts[0].toUpperCase();
                    int courseCode = Integer.parseInt(parts[1]);
                    takeCourseInfo.add(new RequiredCourseInfo(department, courseCode, 0)); // Assuming semesterNumber is 0
                }
            }
        }
        // Make List called requiredCourses that is all items in getRequiredCourses(major) if they are not in takeCourseInfo
        ArrayList<RequiredCourseInfo> tmp = getRequiredCourses(major);
        ArrayList<RequiredCourseInfo> requiredCourses = new ArrayList<>();
        for (RequiredCourseInfo requiredCourse : tmp) {
            boolean found = false;
            for (RequiredCourseInfo takenCourse : takeCourseInfo) {
                if (requiredCourse.equals(takenCourse)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                requiredCourses.add(requiredCourse);
            }
        }

        courses = recommendCourses(requiredCourses, desiredCredits);

        //System.out.println("Here");


//        result = "[\n" +
//                "    94,\n" +
//                "    127,\n" +
//                "    748,\n" +
//                "    133,\n" +
//                "    330\n" +
//                "]";
//
//        String[] courseIDs = result.replaceAll("[\\[\\] ]", "").split(",");
//        Search search = new Search();
//        for (String courseID : courseIDs) {
//            int id = Integer.parseInt(courseID.strip());
//            Course course = search.createCourseFromCid(id);
//            courses.add(course);
//        }
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public ArrayList<Course> recommendCourses(ArrayList<RequiredCourseInfo> requiredCourses, int desiredCredits) {
        // Sort courses by semester_number (ascending)
        requiredCourses.sort(Comparator.comparingInt(RequiredCourseInfo::getSemesterNumber));

        // Initialize the result list
        ArrayList<Course> selectedCourses = new ArrayList<>();

        // Call the recursive helper method
        return recommendCoursesRecursive(requiredCourses, selectedCourses, desiredCredits);
    }

    private ArrayList<Course> recommendCoursesRecursive(ArrayList<RequiredCourseInfo> remainingCourses,
                                                   ArrayList<Course> selectedCourses,
                                                   int creditsRemaining) {
        // Base case: If no credits are remaining, return the selected courses
        if (creditsRemaining <= 0) {
            return selectedCourses;
        }

        // Loop through all remaining required courses
        for (RequiredCourseInfo requiredInfo : remainingCourses) {
            // Get all courses for the current required course info
            List<Course> coursesForRequirement = coursesFromRequiredCourseInfo(requiredInfo, search.getSearchResults());

            for (Course course : coursesForRequirement) {
                // Check for conflicts (e.g., duplicate courses)
                if (!selectedCourses.contains(course) && !course.isOverlap(selectedCourses)) {
                    // Add course to selected courses and recurse
                    ArrayList<Course> newSelectedCourses = new ArrayList<>(selectedCourses);
                    newSelectedCourses.add(course);

                    ArrayList<RequiredCourseInfo> newRemainingCourses = new ArrayList<>(remainingCourses);
                    newRemainingCourses.remove(requiredInfo);

                    ArrayList<Course> result = recommendCoursesRecursive(newRemainingCourses, newSelectedCourses, creditsRemaining - course.getCredits());

                    // If a valid result is found, return it
                    if (result != null) {
                        return result;
                    }
                }
            }
        }

        // If no valid schedule is found, return null
        return null;
    }

    public static ArrayList<Integer> getTakenCids() {
        // Read from taken_cids.txt, where each line is an int cid and return that array list
        ArrayList<Integer> cids = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("taken_cids.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                cids.add(Integer.parseInt(line.trim()));
            }
        } catch (IOException e) {
            System.err.println("Error reading taken_cids file: " + e.getMessage());
        }
        return cids;
    }

    public static List<Course> coursesFromRequiredCourseInfo(RequiredCourseInfo requiredCourseInfo, Collection<Course> allCourses) {
        // Loop through all courses and return a List of all courses from allCourses that have the same course code and department
        List<Course> courses = new ArrayList<>();
        for (Course c : allCourses) {
            if (c.getCourseCode() == requiredCourseInfo.getCourseCode() && c.getSubject().equalsIgnoreCase(requiredCourseInfo.getDepartment())) {
                courses.add(c);
            }
        }
        return courses;
    }

    public static ArrayList<RequiredCourseInfo> getRequiredCourses(String major) {
        ArrayList<RequiredCourseInfo> requiredCourses = new ArrayList<>();
        String filePath = "required_courses.txt"; // Path to the file

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split(" ");
                if (parts.length < 3) continue;

                String department = parts[0].toLowerCase();
                int courseCode = Integer.parseInt(parts[1]);
                int semesterNumber = Integer.parseInt(parts[2]);

                RequiredCourseInfo courseInfo = new RequiredCourseInfo(department, courseCode, semesterNumber);
                requiredCourses.add(courseInfo);
            }
        } catch (IOException e) {
            System.err.println("Error reading required courses file: " + e.getMessage());
        }



        return requiredCourses;
    }

    public static void main(String[] args) {
        RecommendedSchedule schedule = new RecommendedSchedule("computerScience", 15);
        for (Course course : schedule.courses) {
            System.out.println(course.getName() + " - " + course.getCid());
        }
    }
}

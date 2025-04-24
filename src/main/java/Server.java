import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.plugin.bundled.CorsPluginConfig;

import java.util.Map;

public class Server {
    private final Schedule schedule;
    private Search search;
    private Filter filter;
    private User user;

    public Server() {
        this.schedule = new Schedule();
        this.search = new Search();
        this.filter = new Filter();
        this.user = new User(1);
    }

    public void registerRoutes(Javalin app) {
        app.get("/schedule", this::getSchedule);
        app.post("/schedule", this::addCourse);
        app.delete("/schedule", this::dropCourse);
        app.get("/search", this::getResults);
        app.post("/search", this::searchCourses);
        app.get("/filter", this::getFilteredResults);
        app.post("/filter", this::filterCourses);
        app.post("/year", this::saveYear);
        app.get("/year", this::getYear);
        app.post("/major", this::saveMajor);
        app.get("/major", this::getMajor);
        app.post("/courseHistory", this::saveCourseHistory);
        app.get("/courseHistory", this::getCourseHistory);
    }

    private void getSchedule(Context ctx) {
        ctx.json(schedule.getCourses());
    }

    private void addCourse(Context ctx) {
        Course course = ctx.bodyAsClass(Course.class);
        System.out.println("Adding course: " + course);
        boolean added = schedule.addCourse(course); // Check if the course was successfully added

        if (added) {
            ctx.status(200).json(Map.of("message", "Course added successfully", "course", course));
        } else {
            ctx.status(400).json(Map.of("error", "Course could not be added due to a conflict or overlap"));
        }
    }

    private void dropCourse(Context ctx) {
        Course course = ctx.bodyAsClass(Course.class);
        boolean dropped = schedule.dropCourse(course);
        if (dropped) {
            ctx.json(Map.of("message", "Course removed", "course", course));
        } else {
            ctx.status(404).result("Course not found: " + course);
        }
    }

    private void getResults(Context ctx) {
        ctx.json(search.getSearchResults());
    }

    private void searchCourses(Context ctx) {
        String query = ctx.body();
        System.out.println("Search query: " + query);
        search = new Search(query);
        ctx.json(Map.of("message", "Search completed for: " + query));
    }

    private void getFilteredResults(Context ctx) {
        ctx.json(search.filter(filter));
    }

    private void filterCourses(Context ctx) {
        try {
            Filter filter = ctx.bodyAsClass(Filter.class);
            System.out.println("Filter criteria: " + filter);
            System.out.println("Filter days: " + filter.getDays());
            System.out.println("Filter department: " + filter.getDepartment());
            System.out.println("Filter course code: " + filter.getCourseCode());
            System.out.println("Filter name: " + filter.getName());
            System.out.println("Filter professors: " + filter.getProf());
            System.out.println("Filter start time: " + filter.getStartTime());
            System.out.println("Filter end time: " + filter.getEndTime());
            this.filter = filter;
            ctx.json(Map.of("message", "Filter applied"));
        } catch (Exception e) {
            System.err.println("Error processing filter request: " + e.getMessage());
            ctx.status(400).json(Map.of("error", "Invalid filter criteria"));
        }
    }

    // Save year method
    private void saveYear(Context ctx) {
        String year = ctx.body();
        // Get the year attribute from year as a json
        year = year.substring(year.indexOf(":") + 1, year.length() - 1);
        // Remove the quotes
        year = year.replace("\"", "");
        user.setYear(year);

        // Send a JSON response back to the client
        ctx.json(Map.of("message", "Year saved successfully", "year", year));
    }

    // Get year method
    private void getYear(Context ctx) {
        ctx.json(Map.of("year", user.getYear()));
    }

    // Save major method
    private void saveMajor(Context ctx) {
        String major = ctx.body();
        // Get the major attribute from major as a json
        major = major.substring(major.indexOf(":") + 1, major.length() - 1);
        // Remove the quotes
        major = major.replace("\"", "");
        user.setMajor(major);

        // Send a JSON response back to the client
        ctx.json(Map.of("message", "Major saved successfully", "major", major));
    }

    // Get major method
    private void getMajor(Context ctx) {
        ctx.json(Map.of("major", user.getMajor()));
    }

    public static void main(String[] args) {
        Server server = new Server();

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(CorsPluginConfig.CorsRule::anyHost);
            });
        }).start(7000);

        server.registerRoutes(app);
    }

    // Save course history method
    private void saveCourseHistory(Context ctx) {
        String courseHistory = ctx.body();
        // Extract the courseHistory attribute from the JSON
        courseHistory = courseHistory.substring(courseHistory.indexOf(":") + 1, courseHistory.length() - 1);
        // Remove the quotes
        courseHistory = courseHistory.replace("\"", "");
        user.setCourseHistory(courseHistory);

        // Send a JSON response back to the client
        ctx.json(Map.of("message", "Course history saved successfully", "courseHistory", courseHistory));
    }

    // Get course history method
    private void getCourseHistory(Context ctx) {
        ctx.json(Map.of("courseHistory", user.getCourseHistory()));
    }
}

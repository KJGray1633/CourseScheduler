import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.plugin.bundled.CorsPluginConfig;

public class Server {
    private final Schedule schedule;
    private final Search search;

    public Server(Schedule schedule, Search search) {
        this.schedule = schedule;
        this.search = search;
    }

    public void registerRoutes(Javalin app) {
        app.get("/schedule", this::getSchedule);
        app.post("/schedule", this::addCourse);
        app.delete("/schedule", this::dropCourse);
        app.get("/search", this::searchCourses);
    }

    private void getSchedule(Context ctx) {
        ctx.json(schedule.getCourses());
    }

    private void addCourse(Context ctx) {
        Course course = ctx.bodyAsClass(Course.class);
        schedule.addCourse(course);
        ctx.result("Course added: " + course);
    }

    private void dropCourse(Context ctx) {
        Course course = ctx.bodyAsClass(Course.class);
        boolean dropped = schedule.dropCourse(course);
        if (dropped) {
            ctx.result("Course dropped: " + course);
        } else {
            ctx.status(404).result("Course not found: " + course);
        }
    }

    private void searchCourses(Context ctx) {
        ctx.json(search.getSearchResults());
    }

    public static void main(String[] args) {
        Schedule schedule = new Schedule();
        Search search = new Search();
        Server server = new Server(schedule, search);

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(CorsPluginConfig.CorsRule::anyHost);
            });
        }).start(7000);

        server.registerRoutes(app);
    }
}

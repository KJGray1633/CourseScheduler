import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.plugin.bundled.CorsPluginConfig;

public class Server {
    private final Schedule schedule;

    public Server(Schedule schedule) {
        this.schedule = schedule;
    }

    public void registerRoutes(Javalin app) {
        app.get("/schedule", this::getSchedule);
        app.post("/schedule", this::addCourse);
        app.delete("/schedule", this::dropCourse);
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

    public static void main(String[] args) {
        Schedule schedule = new Schedule();
        Server server = new Server(schedule);

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(CorsPluginConfig.CorsRule::anyHost);
            });
        }).start(7000);

        server.registerRoutes(app);
    }
}

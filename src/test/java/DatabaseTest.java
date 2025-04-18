import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


// run DBCreate.java before running this tester class
public class DatabaseTest {

    private DatabaseCalls db;

    @BeforeEach
    public void setUp() {
        db = new DatabaseCalls();
    }

    @Test
    public void testAddUser() {
        String username = "Sarah";
        String password = "password";
        String major = "Computer Science";
        String year = "2026";
        User myUser = new User(username, password, major, year);
        assertTrue(db.validateUser(username, password));
    }

    @Test
    public void testDropCourseNotAdded() {
        boolean dropCID = db.dropCourse(1, 1);

        assertFalse(dropCID);
    }

    @Test
    public void testAddCourse() {
        boolean addCID1 = db.addCourse(1, 1);
        boolean addCID2 = db.addCourse(1, 2);
        boolean addCID3 = db.addCourse(1, 3);
        boolean addCID4 = db.addCourse(1, 4);

        assertTrue(addCID1);
        assertTrue(addCID2);
        assertTrue(addCID3);
        assertTrue(addCID4);
    }

    @Test
    public void testGetSchedule() {
        db.addCourse(1, 1);
        db.addCourse(1, 2);
        db.addCourse(1, 3);
        db.addCourse(1, 4);

        Schedule schedule = db.getSchedule(1);

        assertNotNull(schedule);

        assertTrue(schedule.containsCourseId(1));
        assertTrue(schedule.containsCourseId(2));
        assertTrue(schedule.containsCourseId(3));
        assertTrue(schedule.containsCourseId(4));
    }

    @Test
    public void testDropAllCourses() {
        testAddUser();
        testAddCourse();

        boolean c1 = db.dropCourse(1, 1);
        assertTrue(c1);
        boolean c2 = db.dropCourse(1, 2);
        assertTrue(c2);
        boolean c3 = db.dropCourse(1, 3);
        assertTrue(c3);
        boolean c4 = db.dropCourse(1, 4);
        assertTrue(c4);

    }

    @Test
    public void testUserExists() {
        boolean userExists = db.validateUser("Sarah", "password");
        assertTrue(userExists);
    }

    @Test
    public void testDeleteUser() {
        boolean userDeleted = db.removeUser(1);
        assertTrue(userDeleted);
    }

    @Test
    public void testGetCourse() {
        Course course = new Course(1, "principles of accounting i", 201);

        Course retrievedCourse = db.getCourse(1);

        assertNotNull(retrievedCourse);
        assertEquals(course.getCid(), retrievedCourse.getCid());
        assertEquals(course.getName(), retrievedCourse.getName());
        assertEquals(course.getCourseCode(), retrievedCourse.getCourseCode());
    }

}
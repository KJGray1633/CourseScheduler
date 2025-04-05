import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TempDbTest {

    private DatabaseCalls db;

    @BeforeEach
    public void setUp() {
        db = new DatabaseCalls();
    }

    @Test
    public void testAddCourse() {
        boolean removeCID1 = db.dropCourse(1, 1);
        boolean removeCID2 = db.dropCourse(1, 2);
        boolean removeCID3 = db.dropCourse(1, 3);
        boolean removeCID4 = db.dropCourse(1, 4);

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
    public void testDropCourse() {
        boolean addCID1 = db.addCourse(1, 1);
        boolean addCID2 = db.addCourse(1, 2);
        boolean addCID3 = db.addCourse(1, 3);
        boolean addCID4 = db.addCourse(1, 4);

        boolean removeCID1 = db.dropCourse(1, 1);
        boolean removeCID2 = db.dropCourse(1, 2);
        boolean removeCID3 = db.dropCourse(1, 3);
        boolean removeCID4 = db.dropCourse(1, 4);

        assertTrue(removeCID1);
        assertTrue(removeCID2);
        assertTrue(removeCID3);
        assertTrue(removeCID4);
    }

    @Test
    public void testDropCourseNotAdded() {
        boolean dropCID1 = db.dropCourse(1, 1);

        boolean addCID2 = db.addCourse(1, 2);
        boolean addCID3 = db.addCourse(1, 3);

        boolean dropCID = db.dropCourse(1, 1);

        assertFalse(dropCID);
    }
}
import com.java.model.Course;
import com.java.model.MeetingTime;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CourseTest {
    @Test
    void testIsOverlap() {
        Course c1 = new Course(0);
        c1.times = new ArrayList<>();
        c1.times.add(new MeetingTime(new Time(1), new Time(10), "Monday"));
        Course c2 = new Course(1);
        c2.times = new ArrayList<>();
        c2.times.add(new MeetingTime(new Time(1), new Time(10), "Monday"));
        assertTrue(c1.isOverlap(c2));
        Course c3 = new Course(2);
        c3.times = new ArrayList<>();
        c3.times.add(new MeetingTime(new Time(5), new Time(10), "Monday"));
        assertTrue(c1.isOverlap(c3));
        Course c4 = new Course(3);
        c4.times = new ArrayList<>();
        c4.times.add(new MeetingTime(new Time(10), new Time(20), "Monday"));
        assertFalse(c1.isOverlap(c4));
        Course c5 = new Course(4);
        c5.times = new ArrayList<>();
        c5.times.add(new MeetingTime(new Time(1), new Time(10), "Tuesday"));
        assertFalse(c1.isOverlap(c5));
    }
}

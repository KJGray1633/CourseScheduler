import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CourseTest {

    @Test
    void testIsOverlapSameTimeSameDay() {
        Course c1 = new Course(0);
        c1.times = new ArrayList<>();
        c1.times.add(new MeetingTime(new Time(1), new Time(10), "m"));
        Course c2 = new Course(1);
        c2.times = new ArrayList<>();
        c2.times.add(new MeetingTime(new Time(1), new Time(10), "m"));
        assertTrue(c1.isOverlap(c2));
    }

    @Test
    void testIsOverlapDifferentStartTimeSameDay() {
        Course c1 = new Course(0);
        c1.times = new ArrayList<>();
        c1.times.add(new MeetingTime(new Time(1), new Time(10), "m"));
        Course c3 = new Course(2);
        c3.times = new ArrayList<>();
        c3.times.add(new MeetingTime(new Time(5), new Time(10), "m"));
        assertTrue(c1.isOverlap(c3));
    }

    @Test
    void testIsOverlapNonOverlappingTimesSameDay() {
        Course c1 = new Course(0);
        c1.times = new ArrayList<>();
        c1.times.add(new MeetingTime(new Time(1), new Time(10), "m"));
        Course c4 = new Course(3);
        c4.times = new ArrayList<>();
        c4.times.add(new MeetingTime(new Time(10), new Time(20), "m"));
        assertFalse(c1.isOverlap(c4));
    }

    @Test
    void testIsOverlapDifferentDays() {
        Course c1 = new Course(0);
        c1.times = new ArrayList<>();
        c1.times.add(new MeetingTime(new Time(1), new Time(10), "m"));
        Course c5 = new Course(4);
        c5.times = new ArrayList<>();
        c5.times.add(new MeetingTime(new Time(1), new Time(10), "t"));
        assertFalse(c1.isOverlap(c5));
    }
}
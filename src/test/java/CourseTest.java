import com.java.model.Course;
import com.java.model.MeetingTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CourseTest {
    @Test
    void testIsOverlap() {
        Course c1 = new Course(0);
        c1.setTimes(new ArrayList<>());
        c1.getTimes().add(new MeetingTime(new Time(1), new Time(10), "Monday"));
        Course c2 = new Course(1);
        c2.setTimes(new ArrayList<>());
        c2.getTimes().add(new MeetingTime(new Time(1), new Time(10), "Monday"));
        Assertions.assertTrue(c1.isOverlap(c2));
        Course c3 = new Course(2);
        c3.setTimes(new ArrayList<>());
        c3.getTimes().add(new MeetingTime(new Time(5), new Time(10), "Monday"));
        Assertions.assertTrue(c1.isOverlap(c3));
        Course c4 = new Course(3);
        c4.setTimes(new ArrayList<>());
        c4.getTimes().add(new MeetingTime(new Time(10), new Time(20), "Monday"));
        Assertions.assertFalse(c1.isOverlap(c4));
        Course c5 = new Course(4);
        c5.setTimes(new ArrayList<>());
        c5.getTimes().add(new MeetingTime(new Time(1), new Time(10), "Tuesday"));
        Assertions.assertFalse(c1.isOverlap(c5));
    }
}

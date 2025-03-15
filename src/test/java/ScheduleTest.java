

import com.java.model.Course;
import com.java.model.Schedule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScheduleTest {
    private Schedule schedule;
    @BeforeEach
    void setup() {
        schedule = new Schedule(0);
        schedule.courses = new ArrayList<>();
        schedule.courses.add(new Course(1));
        schedule.courses.add(new Course(2));
        schedule.courses.add(new Course(3));
    }
    @Test
    void testSaveSchedule() {
        Assertions.assertTrue(schedule.saveSchedule());
    }

    @Test
    void testScheduleFromJSON() {
        Schedule loadSchedule = new Schedule("schedule.json");
        for (int i = 0; i < loadSchedule.courses.size(); i++) {
            Assertions.assertEquals(loadSchedule.courses.get(i).getCid(), schedule.courses.get(i).getCid());
        }
    }
}

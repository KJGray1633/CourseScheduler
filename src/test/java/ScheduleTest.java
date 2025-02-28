import org.junit.jupiter.api.BeforeAll;
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
        schedule.courses.add(new Course(1,"Programming 1", 0,"Programming Prof 1"));
        schedule.courses.add(new Course(2,"Foundations of Academic Discourse", 1,"English Prof 1"));
        schedule.courses.add(new Course(3,"Principles of Accounting",2,"Accounting Prof 1"));
    }
    @Test
    void testSaveSchedule() {
        assertTrue(schedule.saveSchedule());
    }

    @Test
    void testScheduleFromJSON() {
        Schedule loadSchedule = new Schedule(0);
        for (int i = 0; i < loadSchedule.courses.size(); i++) {
            assertEquals(loadSchedule.courses.get(i).getCid(), schedule.courses.get(i).getCid());
        }
    }
}

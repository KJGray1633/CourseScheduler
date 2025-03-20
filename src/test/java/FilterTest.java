import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;

public class FilterTest {

    private Search search;

    @BeforeEach
    public void setUp() {
        search = new Search();
    }

    @Test
    public void testFilterComp141() {
        Filter filter = new Filter();
        filter.setDepartment("comp");
        filter.setCourseCode(141);
        filter.setDays(new ArrayList<>(Arrays.asList(Day.MONDAY, Day.WEDNESDAY, Day.FRIDAY)));
        filter.setStartTime(Time.valueOf("12:00:00"));
        filter.setEndTime(Time.valueOf("12:50:00"));

        ArrayList<Course> filteredResults = search.filter(filter);
        System.out.println(filteredResults);

        for (Course course : filteredResults) {
            assertEquals("comp", course.getSubject(), "Course subject should be 'comp'");
            assertEquals(141, course.getCourseCode(), "Course code should be 141");
            assertTrue(course.getTimes().stream().anyMatch(mt ->
                    mt.getDay().equals(Day.MONDAY) ||
                            mt.getDay().equals(Day.WEDNESDAY) ||
                            mt.getDay().equals(Day.FRIDAY)
            ), "Course should have a meeting time on MWF");
            assertTrue(course.getTimes().stream().allMatch(mt ->
                    !mt.getStartTime().before(filter.getStartTime()) &&
                            !mt.getEndTime().after(filter.getEndTime())
            ), "Course meeting times should be within the specified time range");
        }
    }
}
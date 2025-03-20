import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class SearchTest {

    private Search search;

    @BeforeEach
    public void setUp() {
        search = new Search();
    }

    @Test
    public void testSearchBiologyQuery() {
        search.setQuery("biology");
        ArrayList<Course> results = search.getSearchResults();
        assertNotNull(results, "Search results should not be null");

        int manualBiologyCount = getManualBiologyCount();
        assertEquals(manualBiologyCount, results.size(), "getSearchResults should match the JSON manual count.");
    }

    private int getManualBiologyCount() {
        int biologyCount = 0;
        ArrayList<Course> courses = Search.parseJSON();
        if (courses == null) {
            fail("Failed to parse JSON file");
        }

        for (Course course : courses) {
            String name = course.getName().toLowerCase();
            if (name.contains("biology")) {
                biologyCount++;
            }
        }

        return biologyCount;
    }
}
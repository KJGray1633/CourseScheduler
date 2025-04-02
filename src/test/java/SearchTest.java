import com.java.model.Course;
import com.java.model.Search;
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
    public void testSearchEmptyQuery() {
        search.setQuery("");
        ArrayList<Course> results = search.getSearchResults();
        assertNotNull(results, "com.java.model.Search results should not be null");

        ArrayList<Course> allCourses = Search.parseJSON();
        assertEquals(allCourses.size(), results.size(), "com.java.model.Search results should match all courses for an empty query.");
    }

    @Test
    public void testSetGetQuery() {
        search.setQuery("abababa");
        String theQuery = search.getQuery();
        assertEquals(("abababa"), theQuery, "Query should be 'abababa'");
    }

}
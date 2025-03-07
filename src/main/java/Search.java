import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Scanner;

public class Search {
    private String query;
    private ArrayList<Course> searchResults;

    public static List<Course> parseJSON() {
        int id = 0;
        List<Course> courses = new ArrayList<>();
        String content = "";

        try {
            content = new String(Files.readAllBytes(Paths.get("data_wolfe.json")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        JSONObject json = new JSONObject(content);
        JSONArray classes = json.getJSONArray("classes");

        for (int i = 0; i < classes.length(); i++) {
            JSONObject c = classes.getJSONObject(i);
            Course currCourse = new Course(id);
            int credits = c.getInt("credits");
            currCourse.setCredits(credits);

            JSONArray fac = c.getJSONArray("faculty");
            for (int j = 0; j < fac.length(); j++) {
                String faculty = fac.getString(j);
                currCourse.getProfessor().add(faculty);
            }
            boolean isLab = c.getBoolean("is_lab");
            boolean isOpen = c.getBoolean("is_open");
            currCourse.setLab(isLab);
            currCourse.setOpen(isOpen);

            String location = c.getString("location");
            String name = c.getString("name");
            currCourse.setLocation(location);
            currCourse.setName(name);

            int number = c.getInt("number");
            int open_seats = c.getInt("open_seats");
            currCourse.setCourseCode(number);
            currCourse.setOpenSeats(open_seats);

            String section = c.getString("section");
            String semester = c.getString("semester");
            String subject = c.getString("subject");
            currCourse.setSection(section);
            currCourse.setSemester(semester);
            currCourse.setSubject(subject);
            id++;

            JSONArray times = c.getJSONArray("times");
            for (int j = 0; j < times.length(); j++) {
                JSONObject js = times.getJSONObject(j);
                String day = js.getString("day");

                String end_time = js.getString("end_time");
                Time et = scanTime(end_time);

                String start_time = js.getString("start_time");
                Time st = scanTime(start_time);
                MeetingTime mt = new MeetingTime(st, et, day);
                currCourse.getTimes().add(mt);
                System.out.println(currCourse);
            }

            int total_seats = c.getInt("total_seats");
            currCourse.setTotalSeats(total_seats);
        }
        return courses;
    }

    public static Time scanTime(String t) {
        Scanner scan = new Scanner(t);
        scan.useDelimiter(":");
        int hours = scan.nextInt();
        int mins = scan.nextInt();
        int secs = scan.nextInt();
        long ms = 0;
        ms += (3600000 * hours);
        ms += (60000 * mins);
        ms += (1000 * secs);
        Time time = new Time(ms);
        return time;
    }

    public static void main(String[] args) {
        parseJSON();
    }

    public Search(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public ArrayList<Course> getSearchResults() {
        return searchResults;
    }

    public void filter(Filter filter) {
        // Account for situations where filter fields are null
        for (Course c : searchResults) {
            for (String prof : c.getProfessor()) {
                if (filter.getProf() != null && !filter.getProf().contains(prof)) {
                    searchResults.remove(c);
                }
            }

            if (filter.getDepartment() != null && !filter.getDepartment().equals(c.getName())) {
                searchResults.remove(c);
            }

            // Check day, end and start times
            if (filter.getCourseCode() != 0 && filter.getCourseCode() != c.getCourseCode()) {
                searchResults.remove(c);
            }
            for (MeetingTime t : c.getTimes()) {
                boolean isDay = filter.getDays().equals(Filter.Days.valueOf(t.getDay()));
                if (filter.getDays() != null && !isDay) {
                    c.getTimes().remove(t);
                    searchResults.remove(c);
                }
            }
            if (filter.getName() != null && !c.getName().equals(filter.getName())) {
                searchResults.remove(c);
            }

            if (filter.getReferenceCode() != 0 && c.getReferenceNum() != filter.getReferenceCode()) {
                searchResults.remove(c);
            }
        }
    }

    public String spellCheck(String s) {
        /**
         * Needs to be tested
        * */
//        String dif;
//        for(Course c : searchResults){
//            dif = StringUtils.difference(c.getName(), s);
//            if(dif.length() < 4){
//                return c.getName();
//            }
//
//        }
//        return "Could not find match for " + s;
        return "";
    }
}

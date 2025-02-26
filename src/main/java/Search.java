import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.ArrayList;

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
            JSONArray fac = c.getJSONArray("faculty");
            for (int j = 0; j < fac.length(); j++) {
                String faculty = fac.getString(j);
                currCourse.getProfessor().add(faculty);
            }
            boolean isLab = c.getBoolean("is_lab");
            boolean isOpen = c.getBoolean("is_open");

            String location = c.getString("location");
            String name = c.getString("name");

            int number = c.getInt("number");
            int open_seats = c.getInt("open_seats");

            String section = c.getString("section");
            String semester = c.getString("semester");
            String subject = c.getString("subject");

            id++;
            currCourse.setName(name);
            currCourse.setCourseCode(number);

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
        }
        return courses;
        }

    public static Time scanTime(String t){
        Scanner scan = new Scanner(t);
        scan.useDelimiter(":");
        int hours = scan.nextInt();
        int mins = scan.nextInt();
        int secs = scan.nextInt();
        long ms = 0;
        ms += (3600000*hours);
        ms += (60000*mins);
        ms += (1000*secs);
        Time time = new Time(ms);
        return time;
    }

        public static void main (String[]args){
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

    public ArrayList<Course> filter (Filter filter){
        for(Course c : searchResults){
            for(String prof: c.getProfessor()){
                if(!filter.getProf().contains(prof)){
                    searchResults.remove(c);
                }
            }

            if(!filter.getDepartment().equals(c.getName())){
                searchResults.remove(c);
            }

            // Check day, end and start times
            if(filter.getCourseCode() != c.getCourseCode()){
                searchResults.remove(c);
            }
            for(MeetingTime t: c.getTimes()){
                boolean isDay = filter.getDays().equals(Filter.Days.valueOf(t.getDay()));
                if(!isDay){
                    c.getTimes().remove(t);
                    searchResults.remove(c);
                }
            }


        }
            return null;
        }

        public String spellCheck (String s){
            return null;
        }
    }

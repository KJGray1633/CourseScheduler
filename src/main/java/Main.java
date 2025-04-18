import java.sql.Time;
import java.time.LocalTime;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.ParseException;
//Made changes
public class Main {
    private static DatabaseCalls dbc = new DatabaseCalls();
    public enum Page {
        HOME,
        SEARCH,
        CALENDAR,
        EXIT,
        INVALID,
        SAME
    }

    private static Search search;
    private static User user;
    private static Schedule schedule;

    private static Page currPage;

    private static String[] menu = {"Home","Search Courses","Calendar"};

    private static String getMenuString() {
        // Create a line string to be the top and bottom of command app
        String lineString = "----------------------------------------------------------------------------------------------------";
        StringBuilder sb = new StringBuilder();
        // Add the line string
        sb.append(lineString);
        sb.append("\n");
        // Add all the menu items
        for (int i = 0; i < menu.length; i++) {
            sb.append("\t");
            sb.append(menu[i]);
        }
        sb.append("\n");
        // Add the line string again
        sb.append(lineString);

        return sb.toString();
    }

    private static String getHomeString() {
        // Add the current courses to the schedule
        StringBuilder sb = new StringBuilder();
        sb.append("Current Courses:\n");
        ArrayList<Course> courses = schedule.getCourses();
        // Add all the courses to the string builder
        for (Course course : courses) {
            sb.append(course.toString());
            sb.append("\n\tRemove by typing 'RM ");
            sb.append(course.getCid());
            sb.append("'");
            sb.append("\n");
        }

        return sb.toString();
    }

    private static String getSearchCoursesString() {
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(System.in);
        sb.append("--Search--\n");
        sb.append("If you would like to search for courses please type 'search'\n\n");

        return sb.toString();

    }

    /**
     * Converts a string in the format H:MM or HH:MM to a java.sql.Time object.
     * Returns null if the string is not in the correct format or if an error occurs.
     *
     * @param timeString the time string in the format H:MM or HH:MM
     * @return the corresponding java.sql.Time object or null if the format is incorrect or an error occurs
     */
    public static Time convertStringToTime(String timeString) {
        String timePattern = "^([0-9]|[01]\\d|2[0-3]):[0-5]\\d$";
        Pattern pattern = Pattern.compile(timePattern);
        Matcher matcher = pattern.matcher(timeString);

        if (!matcher.matches()) {
            return null;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("H:mm");
            long ms = dateFormat.parse(timeString).getTime();
            return new Time(ms);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Validates if the given string matches the format XXXX 000.
     * Returns the string if it matches the format, otherwise returns null.
     *
     * @param input the string to be validated
     * @return the input string if it matches the format, null otherwise
     */
    public static boolean validateCourseCode(String input) {
        if (input == null || input.isEmpty()) {
            return true;
        }
        String patternString = "^[A-Za-z]{4} \\d{3}$";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(input);

        return matcher.matches();
    }

    private static void sortClassDay(ArrayList<Map.Entry<MeetingTime, Course>> meetingList) {
        Collections.sort(meetingList, new Comparator<Map.Entry<MeetingTime, Course>>() {
            @Override
            public int compare(Map.Entry<MeetingTime, Course> entry1, Map.Entry<MeetingTime, Course> entry2) {
                return entry1.getKey().getStartTime().compareTo(entry2.getKey().getStartTime());
            }
        });
    }

    private static String getCalendarString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--Calendar--\n\n");

        // Create a map to store the schedule for each day
        Map<Day, List<String>> dailySchedule = new HashMap<>();
        for (Day day : Day.values()) {
            dailySchedule.put(day, new ArrayList<>(Collections.nCopies(24, "")));
        }

        // Populate the schedule map with course information
        for (Course course : schedule.getCourses()) {
            for (MeetingTime meetingTime : course.getTimes()) {
                int startHour = meetingTime.getStartTime().toLocalTime().getHour();
                int endHour = meetingTime.getEndTime().toLocalTime().getHour();
                if (meetingTime.getEndTime().toLocalTime().getMinute() >= 30) {
                    endHour++;
                }
                String courseInfo = course.getSubject() + " " + course.getCourseCode();
                for (int hour = startHour; hour < endHour; hour++) {
                    dailySchedule.get(meetingTime.getDay()).set(hour, courseInfo);
                }
            }
        }

        // Create the header row
        sb.append(String.format("%-10s", ""));
        for (Day day : Day.values()) {
            sb.append(String.format("%-15s", day));
        }
        sb.append("\n");

        // Create the schedule rows
        for (int hour = 8; hour <= 21; hour++) {
            sb.append(String.format("%-10s", String.format("%02d:00 %s", hour % 12 == 0 ? 12 : hour % 12, hour < 12 ? "AM" : "PM")));
            for (Day day : Day.values()) {
                sb.append(String.format("%-15s", dailySchedule.get(day).get(hour)));
            }
            sb.append("\n");
        }

//        // Dictionary with class times for every day
//        Dictionary<Day, ArrayList<Map.Entry<MeetingTime, Course>>> classTimes = new Hashtable<>();
//
//        // Loop through all the different classes and their meeting times
//        for (Course course : schedule.getCourses()) {
//            for (MeetingTime meetingTime : course.getTimes()) {
//                // Add the meeting time to the correct dictionary entry
//                switch (meetingTime.getDay()) {
//                    case MONDAY:
//                        classTimes.get(Day.MONDAY).add(Map.entry(meetingTime,course));
//                        break;
//                    case TUESDAY:
//                        classTimes.get(Day.TUESDAY).add(Map.entry(meetingTime,course));
//                        break;
//                    case WEDNESDAY:
//                        classTimes.get(Day.WEDNESDAY).add(Map.entry(meetingTime,course));
//                        break;
//                    case THURSDAY:
//                        classTimes.get(Day.THURSDAY).add(Map.entry(meetingTime,course));
//                        break;
//                    case FRIDAY:
//                        classTimes.get(Day.FRIDAY).add(Map.entry(meetingTime,course));
//                        break;
//                }
//            }
//        }
//
//        Enumeration<Day> classKeys = classTimes.keys();
//
//        // Sort all the day array lists by when they start
//        while (classKeys.hasMoreElements()) {
//            Day key = classKeys.nextElement();
//            sortClassDay(classTimes.get(key));
//        }

        return sb.toString();
    }

    /**
     * Get a string to be printed as output for a specific page
     * Get a string to be printed as output for a specific page
     * @param currPage the page that was selected
     * @return a string containing the desired view for the selected page
     */
    private static String getPageString(Page currPage) {
        // Home
        if (currPage.equals(Page.HOME))
            return getHomeString();
        // Search Courses
        if (currPage.equals(Page.SEARCH))
            return getSearchCoursesString();
        // Calendar
        if (currPage.equals(Page.CALENDAR))
            return getCalendarString();
        // Throw exception if it was invalid
        throw new IllegalArgumentException("currPage must be one of Page.HOME, Page.SEARCH, or Page.CALENDAR to be accepted by getPageString function.");
    }

    /**
     * Gets the current page from the Page enum based off an input string that represents a page
     * @param input a string that contains the correct characters for a specific page
     * @return a Page enum value for the specified page
     */
    private static Page getNextPage(String input) {
        String cleanedInput = input.toLowerCase().strip();
        // Home
        if (cleanedInput.equals(menu[0].toLowerCase())) return Page.HOME;
        // Search Courses
        if (cleanedInput.equals(menu[1].toLowerCase())) return Page.SEARCH;
        // Calendar
        if (cleanedInput.equals(menu[2].toLowerCase())) return Page.CALENDAR;
        // Exit
        if (cleanedInput.equals("exit")) return Page.EXIT;
        // Else invalid
        return Page.INVALID;
    }

    private static boolean parseHomeInput(String input) {
        String[] inputArgs = input.toLowerCase().strip().split(" ");
        // Only action on the home page is remove: 'rm'
        if (!inputArgs[0].equals("rm"))
            return false;
        int removeCid;
        try {
            removeCid = Integer.parseInt(inputArgs[1]);
        } catch (NumberFormatException e) {
            // Second part of input was not an integer
            return false;
        }
        for (Course c : schedule.getCourses()) {
            if (c.getCid() == removeCid) {
                schedule.dropCourse(c);
                dbc.dropCourse(1, c.getCid());
                return true;
            }
        }

        return false;
    }

    private static boolean parseSearchInput(String input) {
        // Clean input
        String[] inputArgs = input.toLowerCase().strip().split(" ");
        // Make sure it's valid
        if (!inputArgs[0].equals("search") && !inputArgs[0].equals("add")) {
            return false;
        }
        if (inputArgs[0].equals("add")) {
            // Find the matching course from search results
            Course course = null;
            for (Course c : search.getSearchResults()) {
                if (c.getCid() == Integer.parseInt(inputArgs[1])) {
                    course = c;
                    break;
                }
            }
            if (course == null) {
                System.out.println("Course not found in search results.");
                return false;
            }
            // Check if the course is already in the schedule or overlaps
            for (Course c : schedule.getCourses()) {
                if (c.getCourseCode() == course.getCourseCode() && c.getSubject().equals(course.getSubject())) {
                    System.out.println("Course already in schedule.");
                    return false;
                }
                else if (c.isOverlap(course)) {
                    System.out.println("Course overlaps with another course in schedule.");
                    return false;
                }
            }
            // Add the course to the schedule
            schedule.addCourse(course);
            dbc.addCourse(1, course.getCid());
            System.out.println("Course successfully added to schedule!");
            return true;
        }
        Scanner scanner = new Scanner(System.in);
        // Only action on the home page is search: 'search'
        // Get keyword search
        System.out.print("Please enter your search keyword: ");
        String keyword = scanner.nextLine().strip().toLowerCase();
        // Create new search object if keywords are new
        if (search == null || !keyword.equals(search.getQuery())) {
            search = new Search(keyword);
        }

        // Create filter object
        Filter filter = new Filter();

        // Get day range
        String day = null;
        do {
            System.out.println(((day == null) ? "" : "Invalid entry. ") + "Please enter your day range (either 'MWF' or 'TR' or 'both' for both): ");
            day = scanner.nextLine().strip().toLowerCase();
        } while (!day.equals("mwf") && !day.equals("tr") && !day.equals("both"));
        // Assign the correct day to the filter
        switch (day) {
            case "mwf": // Just add MWF
                filter.setDays(new ArrayList<>(Arrays.asList(Day.MONDAY, Day.WEDNESDAY, Day.FRIDAY)));
                break;
            case "tr": // Just add TR
                filter.setDays(new ArrayList<>(Arrays.asList(Day.TUESDAY, Day.THURSDAY)));
                break;
            case "both": // Add all days
                filter.setDays(new ArrayList<>(Arrays.asList(Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY)));
                break;
        }

        // Get time ranges
        Time start = null;
        boolean first = true;
        String response;
        LocalTime et = LocalTime.of(8, 0);
        do {
            System.out.println((first ? "" : "Invalid entry. ") + "Please enter your earliest time in the form HH:MM (i.e. 14:30) or empty for all times: ");
            // convertStringToTime returns null if invalid input
            response = scanner.nextLine().strip().toLowerCase();
            start = convertStringToTime(response);
            if(start != null){
                LocalTime lt = LocalTime.parse(response);
                if(!et.isAfter(lt) || et.equals(lt)){
                    break;
                }
            }
            start = null;
            first = false;
            // Accept empty input
        } while (start == null && !response.isEmpty());
        Time end = null;
        // If response is empty (i.e no start time), then we do not need an end time
        if (!response.isEmpty()) {
            first = true;
            LocalTime latestTime = LocalTime.of(21, 0);
            do {
                System.out.println((first ? "" : "Invalid entry. ") + "Please enter your latest time in the form HH:MM (i.e. 14:30): ");
                // convertStringToTime returns null if invalid input
                end = convertStringToTime(scanner.nextLine().strip().toLowerCase());
                if(end != null){
                    LocalTime lt = LocalTime.parse(response);
                    if(!latestTime.isAfter(lt) || latestTime.equals(lt)){
                        break;
                    }
                }
                first = false;
            } while (end == null || end.before(start));
        }
        // Assign the time ranges to the filter
        filter.setStartTime(start);
        filter.setEndTime(end);

        // Get course codes
        first = true;
        String courseCode;
        do {
            System.out.println((first ? "" : "Invalid entry. ") + "Please enter your desired course code of the form XXXX 000 or empty for any (i.e. COMP 141): ");
            courseCode = scanner.nextLine().strip().toLowerCase();
            first = false;
        } while (!validateCourseCode(courseCode));
        // Assign the course code and department to the filter
        if (courseCode.isEmpty()) {
            filter.setDepartment(null);
            filter.setCourseCode(0);
        }
        else {
            String[] codeResults = courseCode.split(" ");
            filter.setDepartment(codeResults[0]);
            filter.setCourseCode(Integer.parseInt(codeResults[1]));
        }
        // Filter the results
        ArrayList<Course> currResults = search.filter(filter);

        // Print out the results
        if(currResults.isEmpty()){
            System.out.println("No results found");
        } else{
            System.out.println("Search results:");
            for (Course c : currResults) {
                System.out.println(c.toString() + "\n\tAdd by typing 'ADD " + c.getCid() + "'");
            }
        }
        return true;
    }

    private static boolean parseCalendarInput(String input) {
        // TODO: Implement this method to correctly respond to commands on the calendar page
        return false;
    }

    private static Page parsePageInput(String input) {
        // Check to see if the input command was for inside a page (opposed to being used to switch to another page)
        boolean insidePageInput = false;
        switch (currPage) {
            case HOME: {
                insidePageInput = parseHomeInput(input);
                break;
            }
            case SEARCH: {
                insidePageInput = parseSearchInput(input);
                break;
            }
            case CALENDAR: {
                insidePageInput = parseCalendarInput(input);
                break;
            }
        }
        if (insidePageInput)
            return Page.SAME;
        // If it was not used inside a page, it is probably for switching pages
        return getNextPage(input);
    }

    private static void run() {
        Scanner scan = new Scanner(System.in);
        currPage = Page.HOME;
        String input;
        // Loop every time the page is changed
        while (!currPage.equals(Page.EXIT)) {
            // Print out the menu
            System.out.println(getMenuString());

            // Print out the page view
            System.out.println(getPageString(currPage));

            Page pageStatus;
            do {
                // Get user's input and make it lower case and remove outer whitespace
                System.out.println("Please enter the name of the page you would like to navigate to or the command you would like to execute or 'exit' to quit: ");
                input = scan.nextLine().toLowerCase().strip();
                pageStatus = parsePageInput(input);

                if (pageStatus.equals(Page.INVALID)) {
                    System.out.println("'" + input + "' is an invalid input for the current page. Please try again.");
                }
                // Keep looping if invalid or still on the same page
            } while (pageStatus.equals(Page.INVALID) || pageStatus.equals(Page.SAME));

            // Change the current page to a new page
            currPage = pageStatus;

            // Make extra space for new page
            System.out.println("\n\n");
        }
        System.out.println("Terminating program. Thank you for scheduling courses with us!");
    }

    public static void main(String[] args) {
        user = new User("Bob", "123", "Art", "2025");
        schedule =  dbc.getSchedule(user.getUid());
        run();
//        Search s = new Search();


//        // Test for spellcheck
        //Search s = new Search("principles of accounting i");
//
//        Filter f = new Filter();
//        Day d1 = Day.MONDAY;
//        Day d2 = Day.WEDNESDAY;
//        Day d3 = Day.FRIDAY;
//
//        ArrayList<Day> d = new ArrayList<Day>();
//        f.setDays(d);
//        f.setCourseCode(201);
//        f.setDepartment("acct");
//        f.setName("principles of accounting i");
//        f.getProf().add("graybill, keith b.");
//
//
//        for(Course se: s.getSearchResults()){
//            System.out.println(se);
//        }
    }
    private static Time createTime(Calendar c){
        long timeInMillis = c.getTimeInMillis();
        Time t = new Time(timeInMillis);
        return t;
    }
}
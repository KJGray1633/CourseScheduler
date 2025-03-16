package com.java.model;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
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
            sb.append(course.getCourseCode());
            sb.append("\tProfessor: ");
            sb.append(course.getProfessor());
            sb.append("\tRemove by typing 'RM ");
            sb.append(course.getCid());
            sb.append("'");
            sb.append("\n");
        }

        return sb.toString();
    }

    private static String getSearchCoursesString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--Search--\n\n");
        return sb.toString();

    }

    private static String getCalendarString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--Calendar--\n\n");
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
        // com.java.model.Search Courses
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
        // com.java.model.Search Courses
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
                return true;
            }
        }

        return false;
    }

    private static boolean parseSearchInput(String input) {
        // TODO: Implement this method to correctly respond to commands on the search courses page
        return false;
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
                System.out.print("Please enter the name of the page you would like to navigate to or the command you would like to execute or 'exit' to quit: ");
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
        user = new User(1);
        schedule = new Schedule(user.getUid());
        run();
    }
}

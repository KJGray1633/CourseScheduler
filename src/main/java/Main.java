import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private Search search;
    private User user;
    private Schedule schedule;

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

    /**
     * Get a string to be printed as output for a specific page
     * Get a string to be printed as output for a specific page
     * @param menuItem the menu item that was selected
     * @return a string containing the desired view for the selected page
     */
    private static String getPageString(String menuItem) {
        //TODO: Implement this method for the different pages
        return "";
    }

    private static void run() {
        Scanner scan = new Scanner(System.in);
        String input = "home";
        while (true) {
            // Print out the menu
            System.out.println(getMenuString());

            // Print out the page view
            System.out.println(getPageString(input));

            // Get user's input and make it lower case and remove outer whitespace
            System.out.print("Please enter the name of the page you would like to navigate to or 'exit' to quit: ");
            input = scan.nextLine().toLowerCase().strip();

            // Make extra space
            System.out.println("\n\n");

            // Exit if the user inputted 'exit'
            if (input.equals("exit")) {
                System.out.println("Terminating program. Thank you for scheduling courses with us!");
                break;
            }
        }







    }

    public static void main(String[] args) {
        run();
    }
}

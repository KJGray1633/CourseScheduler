public class DbTest {
    public static void main(String[] args) {
        // Create an instance of DatabaseConnection (or your class containing the addCourse method)
        DatabaseConnection db = new DatabaseConnection();

        // Call addCourse method
        boolean result = db.addCourse(1, 1234);

        // Output the result of addCourse (true or false)
        if (result) {
            System.out.println("Course added successfully.");
        } else {
            System.out.println("Course could not be added.");
        }
    }
}

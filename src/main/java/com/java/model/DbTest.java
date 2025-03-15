package com.java.model;

public class DbTest {
    public static void main(String[] args) {
        // Create an instance of com.java.model.DatabaseConnection (or your class containing the addCourse method)
        DatabaseConnection db = new DatabaseConnection();

        // Call addCourse method
        boolean result = db.addCourse(1, 1234);

        // Output the result of addCourse (true or false)
        if (result) {
            System.out.println("com.java.model.Course added successfully.");
        } else {
            System.out.println("com.java.model.Course could not be added.");
        }


        // drop course test
        boolean result2 = db.dropCourse(1, 1234);

        if (result2) {
            System.out.println("com.java.model.Course dropped successfully.");
        } else {
            System.out.println("com.java.model.Course could not be dropped.");
        }
    }
}

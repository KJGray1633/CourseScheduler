import java.util.ArrayList;

public class RequiredCourseInfo {
    private int semesterNumber; // Represents the semester number
    private String department; // Represents the department of the course
    private int courseCode; // Represents the course code
    private ArrayList<Course> courses; // List of courses in this semester
    // Constructor
    public RequiredCourseInfo(String department, int courseCode, int semesterNumber) {
        this.department = department;
        this.courseCode = courseCode;
        this.semesterNumber = semesterNumber;
    }

    // Getter for semesterNumber
    public int getSemesterNumber() {
        return semesterNumber;
    }

    // Setter for semesterNumber
    public void setSemesterNumber(int semesterNumber) {
        this.semesterNumber = semesterNumber;
    }

    // Getter for course code
    public int getCourseCode() {
        return courseCode;
    }

    // Setter for course code
    public void setCourseCode(int courseCode) {
        this.courseCode = courseCode;
    }

    // Getter for department
    public String getDepartment() {
        return department;
    }

    // Setter for department
    public void setDepartment(String department) {
        this.department = department;
    }

    // equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RequiredCourseInfo that = (RequiredCourseInfo) obj;
        return courseCode == that.courseCode && department.equalsIgnoreCase(that.department);
    }
}
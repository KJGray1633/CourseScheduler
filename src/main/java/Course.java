import java.util.ArrayList;

public class Course {
    private int cid;
    private String name;
    private int courseCode;
    private String description;
    private ArrayList<String> professor;
    protected ArrayList<MeetingTime> times;
    private int referenceNum;


    private int credits;
    private boolean isLab;
    private boolean isOpen;
    private String location;
    private int openSeats;
    private String section;
    private String semester;
    private String subject;
    private int totalSeats;

    public int getCid() {
        return cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCourseCode() {
        return courseCode;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getProfessor() {
        return professor;
    }

    public ArrayList<MeetingTime> getTimes() {
        return times;
    }

    public void setTimes(ArrayList<MeetingTime> times) {
        this.times = times;
    }

    public void setCourseCode(int courseCode) {
        this.courseCode = courseCode;
    }

    public int getReferenceNum() {
        return referenceNum;
    }

    public int getCredits() {return credits;}

    public void setCredits(int credits) {this.credits = credits;}

    public boolean isLab() {return isLab;}

    public void setLab(boolean lab) {isLab = lab;}

    public boolean isOpen() {return isOpen;}

    public void setOpen(boolean open) {isOpen = open;}

    public String getLocation() {return location;}

    public void setLocation(String location) {this.location = location;}

    public int getOpenSeats() {return openSeats;}

    public void setOpenSeats(int openSeats) {this.openSeats = openSeats;}

    public String getSection() {return section;}

    public void setSection(String section) {this.section = section;}

    public String getSemester() {return semester;}

    public void setSemester(String semester) {this.semester = semester;}

    public String getSubject() {return subject;}

    public void setSubject(String subject) {this.subject = subject;}

    public int getTotalSeats() {return totalSeats;}

    public void setTotalSeats(int totalSeats) {this.totalSeats = totalSeats;}

    public Course(int cid) {
        this.cid = cid;
        times = new ArrayList<>();
        professor = new ArrayList<>();
    }

    public Course(int cid, String name, int courseCode) {
        this.cid = cid;
        this.name = name;
        this.courseCode = courseCode;
        this.professor = new ArrayList<>();
    }

    public boolean isOverlap(Course course) {
        ArrayList<MeetingTime> otherTimes = course.getTimes();
        if (otherTimes == null || times == null) return false;

        for (MeetingTime otherTime : otherTimes) {
            for (MeetingTime time : times) {
                if (time.getDay().equals(otherTime.getDay())) {
                    long start1 = time.getStartTime().getTime();
                    long end1 = time.getEndTime().getTime();
                    long start2 = otherTime.getStartTime().getTime();
                    long end2 = otherTime.getEndTime().getTime();

                    if ((start1 <= start2 && end1 >= end2) ||
                        (end1 > start2 && end1 < end2) ||
                        (start1 > start2 && start1 < end2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String professors = "";
        for(String p: professor){
            professors += p + ", ";
        }
        return name +
                " - " + professors + " - " + subject + ' ' + courseCode + ' ' + times;
    }
}

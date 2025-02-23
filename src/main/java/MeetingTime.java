import java.sql.Time;

public class MeetingTime {
    private final Time startTime;
    private final Time endTime;
    private final String day;

    public MeetingTime(Time startTime, Time endTime, String day) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public String getDay() {
        return day;
    }
}

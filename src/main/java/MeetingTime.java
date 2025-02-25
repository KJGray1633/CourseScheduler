import java.sql.Time;

public class MeetingTime {
    private Time startTime;
    private Time endTime;
    private String day;

    public MeetingTime(Time startTime, Time endTime, String day) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;


    }
   ///
    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public String getDay() {
        return day;
    }

    @Override
    public String toString() {
        return "MeetingTime{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", day='" + day + '\'' +
                '}';
    }
}

import java.sql.Time;

enum Day {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
}

public class MeetingTime {
    private final Time startTime;
    private final Time endTime;
    private final Day day;

    /**
     *
     * @param startTime
     * @param endTime
     * @param day
     */
    public MeetingTime(Time startTime, Time endTime, String day) {
        this.startTime = startTime;
        this.endTime = endTime;
        String dayCopy = day.strip().toLowerCase();
        switch (dayCopy) {
            case "m":
                this.day = Day.MONDAY;
                break;
            case "t":
                this.day = Day.TUESDAY;
                break;
            case "w":
                this.day = Day.WEDNESDAY;
                break;
            case "r":
                this.day = Day.THURSDAY;
                break;
            case "f":
                this.day = Day.FRIDAY;
                break;
            default: throw new IllegalArgumentException(dayCopy + " is not a valid day");
        }
    }

    @Override
    public String toString() {
        return "MeetingTime{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", day='" + day + '\'' +
                '}';
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public Day getDay() {
        return day;
    }
}

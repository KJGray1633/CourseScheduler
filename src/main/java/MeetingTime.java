import java.sql.Time;

enum Day {
    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY
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
            case "sunday":
                this.day = Day.SUNDAY;
                break;
            case "monday":
                this.day = Day.MONDAY;
                break;
            case "tuesday":
                this.day = Day.TUESDAY;
                break;
            case "wednesday":
                this.day = Day.WEDNESDAY;
                break;
            case "thursday":
                this.day = Day.THURSDAY;
                break;
            case "friday":
                this.day = Day.FRIDAY;
                break;
            case "saturday":
                this.day = Day.SATURDAY;
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

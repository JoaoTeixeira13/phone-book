package PhoneBook;

public class TimeDuration {

    private final long minutes;
    private final long seconds;
    private final long ms;

    public TimeDuration(long durationInMilliseconds) {
        this.minutes = durationInMilliseconds / 60000;
        this.seconds = (durationInMilliseconds % 60000) / 1000;
        this.ms = durationInMilliseconds % 1000;
    }

    public long getMinutes() {
        return minutes;
    }

    public long getSeconds() {
        return seconds;
    }

    public long getMs() {
        return ms;
    }
}

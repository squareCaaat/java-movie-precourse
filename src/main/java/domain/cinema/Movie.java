package domain.cinema;

import java.time.Duration;
import java.time.LocalDateTime;

public class Movie {
    private static long ID_COUNTER = 1L;

    private long id;
    private String title;
    private Duration runningTime;

    public Movie(String title, Duration runningTime) {
        id = ID_COUNTER++;
        this.title = title;
        this.runningTime = runningTime;
    }

    public LocalDateTime getEndTime(LocalDateTime when) {
        return when.plusMinutes(runningTime.toMinutes());
    }
}
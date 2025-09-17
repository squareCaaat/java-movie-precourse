package domain.cinema;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Cinema {
    private static final LocalTime OPEN_TIME = LocalTime.of(9, 0);
    private static final LocalTime CLOSE_TIME = LocalTime.of(23, 0);
    private static final int MAX_THEATER = 8;

    private final Theater[] theaters = new Theater[MAX_THEATER];

    private Cinema() {
        for (int i = 0; i < MAX_THEATER; i++) {
            theaters[i] = new Theater();
        }
    }

    private static class SingletonHolder {
        private static final Cinema INSTANCE = new Cinema();
    }

    public static Cinema getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Boolean isOperating(LocalDateTime when) {
        return !OPEN_TIME.isAfter(when.toLocalTime()) && !CLOSE_TIME.isBefore(when.toLocalTime());
    }

    public Boolean canBeReserved(LocalDateTime when, LocalDateTime startTime, Movie movie) {
        for (Theater theater : theaters) {
            return theater.hasValidScreen(when, startTime, movie);
        }
        return false;
    }

    public Theater getTheaterById(int theaterId) {
        for (Theater theater : theaters) {
            if (theater.getNumber() == theaterId) {
                return theater;
            }
        }
        return null;
    }

    public List<Theater> getAvailableTheaters(LocalDateTime when, LocalDateTime startTime, Movie movie) {
        List<Theater> availableTheaters = new ArrayList<>();
        for (Theater theater : theaters) {
            if (theater.isAvailable(when, startTime, movie)) {
                availableTheaters.add(theater);
            }
        }
        return availableTheaters;
    }
}
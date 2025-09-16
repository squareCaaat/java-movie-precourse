package domain.cinema;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Cinema {
    private static final LocalTime OPEN_TIME = LocalTime.of(9, 0);
    private static final LocalTime CLOSE_TIME = LocalTime.of(23, 0);
    private static final int MAX_THEATER = 8;

    private List<Theater> theaters = new ArrayList<>(MAX_THEATER);

    private Cinema() {}

    private static class SingletonHolder {
        private static final Cinema INSTANCE = new Cinema();
    }

    public static Cinema getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Boolean isOperating(LocalDateTime when) {
        return OPEN_TIME.isAfter(when.toLocalTime()) && CLOSE_TIME.isBefore(when.toLocalTime());
    }
}
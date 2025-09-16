package domain.cinema;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Screen {
    private static final int SEAT_LIMIT = 60;

    private LocalDateTime whenScreened;
    private Movie movie;
    private Boolean isPlaying;
    private List<Seat> seats = new ArrayList<>(SEAT_LIMIT);

    public Screen() {
        for (int i = 65; i <= 70; i++) {
            for(int j = 1; j <= 10; j++) {
                seats.add(new Seat((char) i, j));
            }
        }
        isPlaying = false;
    }

    private void checkIsScreening(LocalDateTime when) {
        if (whenScreened == null || when == null || movie == null) {
            return;
        }

        // 시작 시간 <= 비교 시간 <= 종료 시간
        isPlaying = !when.isBefore(whenScreened)
            && !when.isAfter(movie.getEndTime(whenScreened));
    }

    protected void playTheMovie(Cinema cinema, Movie movie, LocalDateTime when) {
        if (cinema.isOperating(when) && cinema.isOperating(movie.getEndTime(whenScreened))) {
            this.movie = movie;
            whenScreened = when;
            checkIsScreening(when);
        }
    }

    public Boolean isEmpty() {
        return seats.stream().anyMatch(s -> !s.isOccupied());
    }

    public LocalDateTime getStartTime() {
        return whenScreened;
    }

    public Boolean isScreening(LocalDateTime when) {
        checkIsScreening(when);
        return isPlaying;
    }
}
package domain.cinema;

import java.time.LocalDateTime;

public class Screen {
    private static final int ROW_LIMIT = 6;
    private static final int COLUMN_LIMIT = 10;
    private static final int ROW_START = 65;

    private LocalDateTime whenScreened;
    private Movie movie;
    private final Seat[][] seats = new Seat[ROW_LIMIT][COLUMN_LIMIT];

    public Screen() {
    private void checkIsScreening(LocalDateTime when) {
        if (whenScreened == null || when == null || movie == null) {
            return;
        }

        // 시작 시간 <= 비교 시간 <= 종료 시간
        isPlaying = !when.isBefore(whenScreened)
            && !when.isAfter(movie.getEndTime(whenScreened));
        for (int i = ROW_START; i <= 70; i++) {
            for(int j = 0; j < 10; j++) {
                seats[i % ROW_START][j] = new Seat((char) i, j);
            }
        }
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
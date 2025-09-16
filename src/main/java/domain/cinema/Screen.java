package domain.cinema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Screen {
    private static final int ROW_LIMIT = 6;
    private static final int COLUMN_LIMIT = 10;
    private static final int ROW_START = 65;

    private LocalDateTime whenScreened;
    private Movie movie;
    private final Seat[][] seats = new Seat[ROW_LIMIT][COLUMN_LIMIT];

    public Screen() {
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
        }
    }

    protected Boolean hasEmptySeat() {
        int i = ROW_START;
        int j = 0;
        while(i <= 70) {
            if (j == 10) {
                i++;
                continue;
            }
            if (!seats[i % ROW_START][j].isOccupied()) {
                return true;
            }
            j++;
        }
        return false;
    }

    public Boolean isScreening(LocalDateTime when) {
        if (whenScreened == null || when == null || movie == null) {
            return false;
        }
        return !when.isBefore(whenScreened)
                && !when.isAfter(movie.getEndTime(whenScreened));
    }

    public Boolean willBeScreening(LocalDateTime when, Movie movie) {
        return !isScreening(when) && whenScreened == when && this.movie == movie;
    }

    public LocalDateTime getStartTime() {
        return whenScreened;
    }

    public LocalDateTime getEndTime() {
        return movie.getEndTime(whenScreened);
    }

    public BigDecimal getPrice(char row,  int column) {
        Seat targetSeat = seats[row % ROW_START][column];
        return targetSeat.getPrice();
    }

    public Boolean reserveSeat(char row, int column) {
        Seat targetSeat = seats[row % ROW_START][column];
        return targetSeat.reserve();
    }
}
package domain.cinema;

import domain.cinema.policy.AmountDiscountPolicy;
import domain.cinema.policy.MovieDayCondition;
import domain.cinema.policy.MovieTimeCondition;
import domain.cinema.policy.PercentDiscountPolicy;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Screen {
    private static final int ROW_LIMIT = 6;
    private static final int COLUMN_LIMIT = 10;
    private static final int ROW_START = 65;

    private static int sequenceCounter = 1;

    private int sequence;
    private LocalDateTime whenScreened;
    private Movie movie;
    private final Seat[][] seats = new Seat[ROW_LIMIT][COLUMN_LIMIT];

    public Screen() {
        sequence = sequenceCounter++;
        for (int i = ROW_START; i <= 70; i++) {
            for(int j = 0; j < 10; j++) {
                seats[i % ROW_START][j] = new Seat((char) i, j + 1);
            }
        }
    }

    private Seat parseSeat(String seatLocation) {
        char row = seatLocation.charAt(0);
        int column = Integer.parseInt(String.valueOf(seatLocation.charAt(1))) - 1;
        Seat targetSeat = seats[row % ROW_START][column];
        return targetSeat;
    }

    protected void playTheMovie(Cinema cinema, Movie movie, LocalDateTime when) {
        if (cinema.isOperating(when) && cinema.isOperating(movie.getEndTime(when))) {
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

    protected Movie getMovie() {
        return movie;
    }

    public Boolean isScreening(LocalDateTime when) {
        if (whenScreened == null || when == null || movie == null) {
            return false;
        }
        return !when.isBefore(whenScreened)
                && !when.isAfter(movie.getEndTime(whenScreened));
    }

    public Boolean willBeScreening(LocalDateTime when, LocalDateTime startTime, Movie movie) {
        return !isScreening(when) && whenScreened == startTime && this.movie == movie;
    }

    public LocalDateTime getStartTime() {
        return whenScreened;
    }

    public LocalDateTime getEndTime() {
        return movie.getEndTime(whenScreened);
    }

    public BigDecimal getCurrentPrice(String seatLocation) {
        Seat targetSeat = parseSeat(seatLocation);
        return targetSeat.getDiscountedPrice();
    }

    public Boolean reserveSeat(String seatLocation) {
        Seat targetSeat = parseSeat(seatLocation);
        return targetSeat.reserve();
    }

    public BigDecimal calculateDiscountedPrice(String seatLocation) {
        Seat targetSeat = parseSeat(seatLocation);
        if (!targetSeat.isOccupied()) {
            return null;
        }

        PercentDiscountPolicy percentDiscountPolicy = new PercentDiscountPolicy(new MovieDayCondition());
        targetSeat.reflectDiscount(percentDiscountPolicy.calculateDiscountedPrice(this, seatLocation));

        AmountDiscountPolicy amountDiscountPolicy = new AmountDiscountPolicy(new MovieTimeCondition());
        targetSeat.reflectDiscount(amountDiscountPolicy.calculateDiscountedPrice(this, seatLocation));

        return targetSeat.getDiscountedPrice();
    }
}
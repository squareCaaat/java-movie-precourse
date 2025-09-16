package domain.reservation;

import domain.cinema.Screen;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Reservation {
    private Screen screen;
    private BigDecimal price;
    private LocalDateTime whenReservation;

    public Reservation(Screen screen, BigDecimal price, LocalDateTime whenReservation) {
        this.screen = screen;
        this.price = price;
        this.whenReservation = whenReservation;
    }
}
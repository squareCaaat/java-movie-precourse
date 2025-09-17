package domain.reservation;

import domain.cinema.Cinema;
import domain.cinema.Movie;
import domain.cinema.Screen;
import domain.cinema.Theater;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Reservation {
    private int theaterId;
    private Customer customer;
    private Screen screen;
    private BigDecimal price;
    private LocalDateTime whenReservation;
    private Boolean isConfirmed;

    public Reservation(int theaterId, Customer customer, Screen screen, BigDecimal price, LocalDateTime whenReservation) {
        this.theaterId = theaterId;
        this.customer = customer;
        this.screen = screen;
        this.price = price;
        this.whenReservation = whenReservation;
        isConfirmed = false;
    }

    private Boolean validateReservation(Cinema cinema, LocalDateTime when, Movie movie, List<Reservation> reservations) {
        for (Reservation reservation : reservations) {
            if (!reservation.getScreen().getStartTime().isAfter(when)
                    && !reservation.getScreen().getEndTime().isBefore(when)) {
                return true;
            }

            if (reservation.getScreen()
                    == cinema.getTheaterById(reservation.getTheaterId()).getScreen(when, movie)) {
                return true;
            }
        }
        return false;
    }

    private Theater getAvailableTheater(Customer customer, Cinema cinema, int theaterId, LocalDateTime when, Movie movie) {
        List<Theater> availableTheaters = findReservableTheaters(customer, cinema, when, movie);
        if (availableTheaters == null ||  availableTheaters.isEmpty()) {
            return null;
        }

        for (Theater theater : availableTheaters) {
            if (theater.getNumber() == theaterId) {
                return theater;
            }
        }
        return null;
    }

    protected Screen getScreen() {
        return screen;
    }

    protected int getTheaterId() {
        return theaterId;
    }

    protected Boolean hasDuplicatedReservation(Customer customer, Cinema cinema, LocalDateTime when, Movie movie) {
        List<Reservation> reservations = customer.getReservations();
        if (reservations == null || reservations.isEmpty()) {
            return false;
        }
        return validateReservation(cinema, when, movie, reservations);
    }

    protected List<Theater> findReservableTheaters(Customer customer, Cinema cinema, LocalDateTime when, Movie movie) {
        if (!cinema.canBeReserved(when, movie)) {
            return null;
        }

        if (hasDuplicatedReservation(customer, cinema, when, movie)) {
            return null;
        }
        return cinema.getAvailableTheaters(when, movie);
    }

    public Reservation reserve(Customer customer, Cinema cinema, int theaterId, LocalDateTime when, Movie movie, String seatLocation) {
        Theater targetTheater = getAvailableTheater(customer, cinema, theaterId, when, movie);
        if (targetTheater == null) {
            return null;
        }

        Screen screen = targetTheater.getScreen(when, movie);
        if(screen == null || !screen.reserveSeat(seatLocation)) {
            return null;

        }

        return new Reservation(theaterId, customer, screen, screen.calculateDiscountedPrice(seatLocation), LocalDateTime.now());
    }
}
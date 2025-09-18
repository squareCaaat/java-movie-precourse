package domain.reservation;

import domain.cinema.Cinema;
import domain.cinema.Movie;
import domain.cinema.Screen;
import domain.cinema.Theater;
import domain.reservation.payment.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Reservation {
    private int theaterId;
    private Customer customer;
    private Screen screen;
    private BigDecimal price;
    private LocalDateTime whenReservation;
    private String seatLocation;
    private Boolean isConfirmed;

    public Reservation(int theaterId, Customer customer, Screen screen, BigDecimal price, LocalDateTime whenReservation, String seatLocation) {
        this.theaterId = theaterId;
        this.customer = customer;
        this.screen = screen;
        this.price = price;
        this.whenReservation = whenReservation;
        this.seatLocation = seatLocation;
        isConfirmed = false;
    }

    private static Boolean validateReservation(Cinema cinema, LocalDateTime when, LocalDateTime startTime, Movie movie, List<Reservation> reservations) {
        for (Reservation reservation : reservations) {
            if (!reservation.getScreen().getStartTime().isAfter(when)
                    && !reservation.getScreen().getEndTime().isBefore(when)) {
                return true;
            }

            if (reservation.getScreen()
                    == cinema.getTheaterById(reservation.getTheaterId()).getScheduledScreen(when, startTime, movie)) {
                return true;
            }
        }
        return false;
    }

    private static Theater getAvailableTheater(Customer customer, Cinema cinema, int theaterId, LocalDateTime when, LocalDateTime startTime, Movie movie) {
        List<Theater> availableTheaters = findReservableTheaters(customer, cinema, when, startTime, movie);
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

    protected static Boolean hasDuplicatedReservation(Customer customer, Cinema cinema, LocalDateTime when, LocalDateTime startTime, Movie movie) {
        List<Reservation> reservations = customer.getReservations();
        if (reservations == null || reservations.isEmpty()) {
            return false;
        }
        return validateReservation(cinema, when, startTime, movie, reservations);
    }

    protected static List<Theater> findReservableTheaters(Customer customer, Cinema cinema, LocalDateTime when, LocalDateTime startTime, Movie movie) {
        if (!cinema.canBeReserved(when, startTime, movie)) {
            return null;
        }

        if (hasDuplicatedReservation(customer, cinema, when, startTime, movie)) {
            return null;
        }
        return cinema.getAvailableTheaters(when, startTime, movie);
    }

    public static Reservation reserve(Customer customer, Cinema cinema, int theaterId, LocalDateTime when, LocalDateTime startTime, Movie movie, String seatLocation) {
        Theater targetTheater = getAvailableTheater(customer, cinema, theaterId, when, startTime, movie);
        if (targetTheater == null) {
            return null;
        }

        Screen screen = targetTheater.getScheduledScreen(when, startTime, movie);
        if(screen == null || !screen.reserveSeat(seatLocation)) {
            return null;

        }

        return new Reservation(theaterId, customer, screen, screen.calculateDiscountedPrice(seatLocation), LocalDateTime.now(), seatLocation);
    }

    public Boolean confirmReservation(Customer customer, PaymentMethod paymentMethod, BigDecimal pointAmount) {
        if(customer.pay(paymentMethod, pointAmount, price)) {
            isConfirmed = true;
        }
        return isConfirmed;
    }
}
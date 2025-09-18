package domain.reservation;

import domain.cinema.*;
import domain.reservation.payment.CardPayment;
import domain.reservation.payment.CashPayment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReservationTest {

    @Test
    @DisplayName("정상 예매 처리")
    void reserveSuccessful() {
        // Given
        Cinema cinema = Cinema.getInstance();
        Theater theater = cinema.getTheaterById(1);
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 17, 20,30);
        Movie movie = new Movie("Movie", Duration.ofHours(2));
        theater.screenMovie(cinema, startTime, movie);
        Screen screen = theater.getScreen(startTime, movie);
        Customer customer = new Customer(BigDecimal.valueOf(20000));
        // When
        Reservation reservation = Reservation.reserve(customer, cinema, 1, LocalDateTime.of(2025,9,17,13,0), startTime, movie, "D1");
        // Then
        assertNotNull(reservation);
    }

    @Test
    @DisplayName("유효하지 않은 상영에 대한 예매 진행")
    void reserveFailInvalidScreen() {
        // Given
        Cinema cinema = Cinema.getInstance();
        Theater theater = cinema.getTheaterById(1);
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 17, 11,0);
        Movie movie = new Movie("Movie", Duration.ofHours(2));
        theater.screenMovie(cinema, startTime, movie);
        Screen screen = theater.getScreen(startTime, movie);
        Customer customer = new Customer(BigDecimal.valueOf(20000));
        // When
        Reservation reservation = Reservation.reserve(customer, cinema, 1, LocalDateTime.of(2025,9,17,13,0), startTime, movie, "D1");
        Reservation reservation2 = Reservation.reserve(customer, cinema, 1, LocalDateTime.of(2025,9,17,13,0), startTime, new Movie("mmm", Duration.ofHours(2)), "D1");
        Reservation reservation3 = Reservation.reserve(customer, cinema, 1, LocalDateTime.of(2025,9,17,13,0), LocalDateTime.of(2025,12,12,12,0), movie, "D1");
        // Then
        assertNull(reservation);
        assertNull(reservation2);
        assertNull(reservation3);
    }

    @Test
    @DisplayName("중복된 예매 내역")
    void reserveFailDuplicatedReservation() {
        // Given
        Cinema cinema = Cinema.getInstance();
        Theater theater = cinema.getTheaterById(1);
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 15, 13,0);
        Movie movie = new Movie("Movie", Duration.ofHours(2));
        theater.screenMovie(cinema, startTime, movie);
        Screen screen = theater.getScreen(startTime, movie);
        Customer customer = new Customer(BigDecimal.valueOf(20000));
        // When
        Reservation exisitingReservation = Reservation.reserve(customer, cinema, 1, LocalDateTime.of(2025,9,15,11,0), startTime, movie, "D1");
        Reservation reservation = Reservation.reserve(customer, cinema, 1, LocalDateTime.of(2025,9,15,11,0), startTime, movie, "D1");
        // Then
        assertNull(reservation);
    }

    @Test
    @DisplayName("빈 좌석이 없음")
    void reserveFailFullSeatOccupied() {
        // Given
        Cinema cinema = Cinema.getInstance();
        Theater theater = cinema.getTheaterById(1);
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 16, 13,0);
        Movie movie = new Movie("Movie", Duration.ofHours(2));
        theater.screenMovie(cinema, startTime, movie);
        Screen screen = theater.getScreen(startTime, movie);
        Customer customer = new Customer(BigDecimal.valueOf(20000));
        for(int i = 65; i <= 70; i++) {
            for (int j = 0; j < 10; j++) {
                screen.reserveSeat(String.valueOf((char) i).concat(String.valueOf(j + 1)));
            }
        }
        // When
        Reservation reservation = Reservation.reserve(customer, cinema, 1, LocalDateTime.of(2025,9,16,11,0), startTime, movie, "D1");
        // Then
        assertNull(reservation);
    }

    @Test
    @DisplayName("예매 확정 - 포인트 사용 + 카드 결제")
    void confirmReservationPayWithCard() {
        // Given
        Cinema cinema = Cinema.getInstance();
        Theater theater = cinema.getTheaterById(8);
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 17, 20,0);
        Movie movie = new Movie("Movie", Duration.ofHours(2));
        theater.screenMovie(cinema, startTime, movie);
        Customer customer = new Customer(BigDecimal.valueOf(20000));
        customer.earnPoint(BigDecimal.valueOf(5000));
        Reservation reservation = Reservation.reserve(customer, cinema, 8, LocalDateTime.of(2025,9,17,13,0), startTime, movie, "D1");
        // When
        Boolean confirmReservation = reservation.confirmReservation(customer, new CardPayment(), BigDecimal.valueOf(5000));
        // Then
        assertTrue(confirmReservation);
        assertEquals(BigDecimal.valueOf(9550), customer.getBalance());
        assertEquals(BigDecimal.ZERO, customer.getPoints());
    }

    @Test
    @DisplayName("예매 확정 - 포인트 사용 + 현금 결제")
    void confirmReservationPayWithCash() {
        // Given
        Cinema cinema = Cinema.getInstance();
        Theater theater = cinema.getTheaterById(8);
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 17, 10,0);
        Movie movie = new Movie("Movie213", Duration.ofHours(2));
        theater.screenMovie(cinema, startTime, movie);
        Customer customer = new Customer(BigDecimal.valueOf(20000));
        customer.earnPoint(BigDecimal.valueOf(5000));
        Reservation reservation = Reservation.reserve(customer, cinema, 8, LocalDateTime.of(2025,9,17,9,0), startTime, movie, "D1");
        // When
        Boolean confirmReservation = reservation.confirmReservation(customer, new CashPayment(), BigDecimal.valueOf(5000));
        // Then
        assertTrue(confirmReservation);
        assertEquals(BigDecimal.valueOf(9220), customer.getBalance());
        assertEquals(BigDecimal.ZERO, customer.getPoints());
    }

    @Test
    @DisplayName("예매 확정 - 포인트 미사용 결제")
    void confirmReservationNoPoints() {
        // Given
        Cinema cinema = Cinema.getInstance();
        Theater theater = cinema.getTheaterById(8);
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 17, 14,0);
        Movie movie = new Movie("Movie321", Duration.ofHours(2));
        theater.screenMovie(cinema, startTime, movie);
        Customer customer = new Customer(BigDecimal.valueOf(20000));
        Reservation reservation = Reservation.reserve(customer, cinema, 8, LocalDateTime.of(2025,9,17,13,0), startTime, movie, "D1");
        // When
        Boolean confirmReservation = reservation.confirmReservation(customer, new CardPayment(), BigDecimal.valueOf(5000));
        // Then
        assertTrue(confirmReservation);
        assertEquals(BigDecimal.valueOf(2000), customer.getBalance());
        assertEquals(BigDecimal.ZERO, customer.getPoints());

    }

    @Test
    @DisplayName("잔액 부족")
    void cannotConfirmReservation() {
        // Given
        Cinema cinema = Cinema.getInstance();
        Theater theater = cinema.getTheaterById(7);
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 17, 14,0);
        Movie movie = new Movie("Movie122", Duration.ofHours(2));
        theater.screenMovie(cinema, startTime, movie);
        Customer customer = new Customer(BigDecimal.ZERO);
        Reservation reservation = Reservation.reserve(customer, cinema, 7, LocalDateTime.of(2025,9,17,13,0), startTime, movie, "D1");
        // When
        Boolean confirmReservation = reservation.confirmReservation(customer, new CardPayment(), BigDecimal.ZERO);
        // Then
        assertFalse(confirmReservation);
    }
}
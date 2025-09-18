package domain.cinema;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ScreenTest {

    @Test
    @DisplayName("상영 중")
    void isScreening() {
        // Given
        Cinema cinema = Cinema.getInstance();
        Theater theater = cinema.getTheaterById(1);
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 17, 13,0);
        Movie movie = new Movie("Movie", Duration.ofHours(2));
        theater.screenMovie(cinema, startTime, movie);
        Screen screen = theater.getScreen(startTime, movie);
        // When
        Boolean isScreening = screen.isScreening(LocalDateTime.of(2025,9,17,13,30));
        // Then
        assertThat(isScreening).isTrue();
    }

    @Test
    @DisplayName("상영 중이 아님")
    void notScreening() {
        // Given
        Cinema cinema = Cinema.getInstance();
        Theater theater = cinema.getTheaterById(2);
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 17, 13,0);
        Movie movie = new Movie("Movie", Duration.ofHours(2));
        theater.screenMovie(cinema, startTime, movie);
        Screen screen = theater.getScreen(startTime, movie);
        // When
        Boolean isScreening = screen.isScreening(LocalDateTime.of(2025,9,17,20,0));
        // Then
        assertThat(isScreening).isFalse();
    }

    @Test
    @DisplayName("상영 예정")
    void willBeScreening() {
        // Given
        Cinema cinema = Cinema.getInstance();
        Theater theater = cinema.getTheaterById(3);
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 17, 15,0);
        Movie movie = new Movie("Movie", Duration.ofHours(3));
        theater.screenMovie(cinema, startTime, movie);
        Screen screen = theater.getScreen(startTime, movie);
        // When
        Boolean willBeScreening = screen.willBeScreening(LocalDateTime.of(2025,9,17,12,0), startTime, movie);
        // Then
        assertThat(willBeScreening).isTrue();
    }

    @Test
    void getStartTime() {
        // Given
        Cinema cinema = Cinema.getInstance();
        Theater theater = cinema.getTheaterById(4);
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 17, 13,0);
        Movie movie = new Movie("Movie", Duration.ofHours(2));
        theater.screenMovie(cinema, startTime, movie);
        Screen screen = theater.getScreen(startTime, movie);
        // When
        LocalDateTime startTimeExpected = screen.getStartTime();
        // Then
        assertThat(startTimeExpected).isEqualTo(startTime);
    }

    @Test
    void getEndTime() {
        // Given
        Cinema cinema = Cinema.getInstance();
        Theater theater = cinema.getTheaterById(5);
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 17, 13,0);
        Movie movie = new Movie("Movie", Duration.ofHours(2));
        theater.screenMovie(cinema, startTime, movie);
        Screen screen = theater.getScreen(startTime, movie);
        // When
        LocalDateTime endTime = screen.getEndTime();
        // Then
        assertThat(endTime).isEqualTo(LocalDateTime.of(2025,9,17,15,0));
    }

    @Test
    @DisplayName("좌석 가격 얻기")
    void getCurrentPrice() {
        // Given
        Cinema cinema = Cinema.getInstance();
        Theater theater = cinema.getTheaterById(6);
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 17, 13,0);
        Movie movie = new Movie("Movie", Duration.ofHours(2));
        theater.screenMovie(cinema, startTime, movie);
        Screen screen = theater.getScreen(startTime, movie);
        // When
        BigDecimal currentPriceS = screen.getCurrentPrice("D1");
        BigDecimal currentPriceA = screen.getCurrentPrice("E1");
        BigDecimal currentPriceB = screen.getCurrentPrice("A1");
        // Then
        assertThat(currentPriceS).isEqualByComparingTo("18000");
        assertThat(currentPriceA).isEqualByComparingTo("15000");
        assertThat(currentPriceB).isEqualByComparingTo("12000");
    }

    @Test
    @DisplayName("좌석 예약 성공")
    void reserveSeat() {
        // Given
        Cinema cinema = Cinema.getInstance();
        Theater theater = cinema.getTheaterById(7);
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 17, 13,0);
        Movie movie = new Movie("Movie", Duration.ofHours(2));
        theater.screenMovie(cinema, startTime, movie);
        Screen screen = theater.getScreen(startTime, movie);
        // When
        Boolean reserveSeat = screen.reserveSeat("D1");
        // Then
        assertThat(reserveSeat).isTrue();
    }

    @Test
    @DisplayName("좌석 예약 실패")
    void failToReserveSeat() {
        // Given
        Cinema cinema = Cinema.getInstance();
        Theater theater = cinema.getTheaterById(7);
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 17, 16,0);
        Movie movie = new Movie("Movie2", Duration.ofHours(2));
        theater.screenMovie(cinema, startTime, movie);
        Screen screen = theater.getScreen(startTime, movie);
        screen.reserveSeat("D1");
        // When
        Boolean reserveSeat = screen.reserveSeat("D1");
        // Then
        assertThat(reserveSeat).isFalse();
    }

    @Test
    @DisplayName("할인 조건 모두 만족")
    void satisfiedAllConditions() {
        // Given
        Cinema cinema = Cinema.getInstance();
        Theater theater = cinema.getTheaterById(8);
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 10, 10,0);
        Movie movie = new Movie("Movie", Duration.ofHours(2));
        theater.screenMovie(cinema, startTime, movie);
        Screen screen = theater.getScreen(startTime, movie);
        screen.reserveSeat("D1");
        // When
        BigDecimal discountedPrice = screen.calculateDiscountedPrice("D1");
        // Then
        assertThat(discountedPrice).isEqualByComparingTo("14200");
    }

    @Test
    @DisplayName("무비 데이만 만족")
    void satisfiedMovieDayCondition() {
        // Given
        Cinema cinema = Cinema.getInstance();
        Theater theater = cinema.getTheaterById(8);
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 10, 13,0);
        Movie movie = new Movie("Movie2", Duration.ofHours(2));
        theater.screenMovie(cinema, startTime, movie);
        Screen screen = theater.getScreen(startTime, movie);
        screen.reserveSeat("D1");
        // When
        BigDecimal discountedPrice = screen.calculateDiscountedPrice("D1");
        // Then
        assertThat(discountedPrice).isEqualByComparingTo("16200");
    }

    @Test
    @DisplayName("조조/심야만 만족")
    void satisfiedTimeCondition() {
        // Given
        Cinema cinema = Cinema.getInstance();
        Theater theater = cinema.getTheaterById(8);
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 17, 10,0);
        Movie movie = new Movie("Movie3", Duration.ofHours(2));
        theater.screenMovie(cinema, startTime, movie);
        Screen screen = theater.getScreen(startTime, movie);
        screen.reserveSeat("D1");
        // When
        BigDecimal discountedPrice = screen.calculateDiscountedPrice("D1");
        // Then
        assertThat(discountedPrice).isEqualByComparingTo("16000");
    }

    @Test
    @DisplayName("할인 조건 미 충족")
    void unsatisfiedAllConditions() {
        // Given
        Cinema cinema = Cinema.getInstance();
        Theater theater = cinema.getTheaterById(8);
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 17, 14,0);
        Movie movie = new Movie("Movie4", Duration.ofHours(2));
        theater.screenMovie(cinema, startTime, movie);
        Screen screen = theater.getScreen(startTime, movie);
        screen.reserveSeat("D1");
        // When
        BigDecimal discountedPrice = screen.calculateDiscountedPrice("D1");
        // Then
        assertThat(discountedPrice).isEqualByComparingTo("18000");
    }
}
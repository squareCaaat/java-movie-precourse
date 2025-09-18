package domain.cinema;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CinemaTest {
    @Test
    @DisplayName("싱글톤 객체 확인")
    void getInstance() {
        // Given
        Cinema cinema = Cinema.getInstance();
        // When
        Cinema cinema2 = Cinema.getInstance();
        // Then
        assertThat(cinema).isNotNull();
        assertThat(cinema2).isEqualTo(cinema);
    }

    @Test
    @DisplayName("운영 시간 확인")
    void isOperating() {
        // Given
        Cinema cinema = Cinema.getInstance();
        // When
        LocalDateTime when = LocalDateTime.of(2025, 9, 17, 11, 0);
        Boolean isOperating = cinema.isOperating(when);
        // Then
        assertThat(isOperating).isTrue();
    }

    @Test
    @DisplayName("운영 시간 아님")
    void isNotOperating() {
        // Given
        Cinema cinema = Cinema.getInstance();
        // When
        LocalDateTime when1 = LocalDateTime.of(2025, 9, 17, 1, 0);
        LocalDateTime when2 = LocalDateTime.of(2025, 9, 17, 23, 30);
        Boolean isOperating1 = cinema.isOperating(when1);
        Boolean isOperating2 = cinema.isOperating(when2);
        // Then
        assertThat(isOperating1).isFalse();
        assertThat(isOperating2).isFalse();
    }

    @Test
    @DisplayName("예매 가능 검증")
    void canBeReserved() {
        // Given
        Cinema cinema = Cinema.getInstance();
        Theater theater = cinema.getTheaterById(1);
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 17, 13,0);
        Movie movie = new Movie("Movie", Duration.ofHours(2));
        theater.screenMovie(cinema, startTime, movie);
        Boolean canBeReserved = cinema.canBeReserved(LocalDateTime.of(2025,9,17,12,0),startTime, movie);
        // When
        assertThat(canBeReserved).isTrue();
    }

    @Test
    @DisplayName("유효하지 않은 정보에 대한 예매 검증")
    void canNotBeReserved() {
        // Given
        Cinema cinema = Cinema.getInstance();
        Theater theater = cinema.getTheaterById(1);
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 17, 13,0);
        Movie movie = new Movie("Movie", Duration.ofHours(2));
        theater.screenMovie(cinema, startTime, movie);
        // When
        Boolean canBeReserved = cinema.canBeReserved(LocalDateTime.of(2025,10,11,11,0),LocalDateTime.of(2025,10,11,12,0), movie);
        // Then
        assertThat(canBeReserved).isFalse();
    }

    @Test
    void getTheaterById() {
        // Given
        Cinema cinema = Cinema.getInstance();
        Theater theaterA = cinema.getTheaterById(1);
        Theater theaterB = cinema.getTheaterById(2);
        // When
        Theater theaterAex = cinema.getTheaterById(1);
        // Then
        assertThat(theaterAex).isEqualTo(theaterA);
        assertThat(theaterAex).isNotEqualTo(theaterB);
    }

    @Test
    @DisplayName("예매 가능한 상영관 목록 얻기")
    void getAvailableTheaters() {
        // Given
        Cinema cinema = Cinema.getInstance();
        Theater theater1 = cinema.getTheaterById(5);
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 17, 13,0);
        Movie movie = new Movie("Movie", Duration.ofHours(2));
        theater1.screenMovie(cinema, startTime, movie);

        Theater theater2 = cinema.getTheaterById(6);
        LocalDateTime startTime2 = LocalDateTime.of(2025, 9, 17, 15,0);
        theater2.screenMovie(cinema, startTime2, movie);
        // When
        List<Theater> theaters = cinema.getAvailableTheaters(LocalDateTime.of(2025,9,17,10,0), startTime, movie);
        // Then
        assertThat(theaters)
            .isNotNull()
            .isNotEmpty()
            .contains(theater1)
            .doesNotContain(theater2);
    }
}
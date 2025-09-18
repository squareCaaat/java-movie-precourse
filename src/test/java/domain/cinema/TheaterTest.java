package domain.cinema;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TheaterTest {

    @Test
    void isAvailable() {
        // Given
        Cinema cinema = Cinema.getInstance();
        Theater theater = cinema.getTheaterById(1);
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 17, 13,0);
        Movie movie = new Movie("Movie", Duration.ofHours(2));
        theater.screenMovie(cinema, startTime, movie);
        // When
        Boolean isAvailable = theater.isAvailable(LocalDateTime.of(2025,9,17,11,0), startTime, movie);
        // Then
        assertTrue(isAvailable);
    }

    @Test
    void getScheduledScreen() {
        // Given
        Cinema cinema = Cinema.getInstance();
        Theater theater = cinema.getTheaterById(2);
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 17, 13,0);
        Movie movie = new Movie("Movie", Duration.ofHours(2));
        theater.screenMovie(cinema, startTime, movie);
        // when
        Screen screen = theater.getScheduledScreen(LocalDateTime.of(2025,9,17,11,0), startTime, movie);
        // Then
        assertNotNull(screen);
    }

    @Test
    void getNumber() {
        // Given
        Cinema cinema = Cinema.getInstance();
        Theater theater = cinema.getTheaterById(1);
        // When
        int id = theater.getNumber();
        // Then
        assertEquals(1, id);
    }
}
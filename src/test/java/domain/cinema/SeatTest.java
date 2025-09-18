package domain.cinema;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SeatTest {

    @Test
    void getLocation() {
        // Given
        Seat seat = new Seat('A', 1);
        // When
        String seatLocation = seat.getLocation();
        // Then
        assertEquals("A1", seatLocation);
    }

    @Test
    void isOccupied() {
        // Given
        Cinema cinema = Cinema.getInstance();
        Theater theater = cinema.getTheaterById(1);
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 17, 13,0);
        Movie movie = new Movie("Movie", Duration.ofHours(2));
        theater.screenMovie(cinema, startTime, movie);
        Screen screen = theater.getScheduledScreen(LocalDateTime.of(2025,9,17,11,0), startTime, movie);
        // When
        Boolean isOccupied = screen.reserveSeat("A1");
        // Then
        assertTrue(isOccupied);
    }
}
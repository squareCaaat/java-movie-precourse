package domain.cinema;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieTest {

    @Test
    void getEndTime() {
        // Given
        LocalDateTime startTime = LocalDateTime.of(2025, 9, 17, 11, 0);
        Movie movie = new Movie("영화", Duration.ofHours(2));
        // When
        LocalDateTime endTime = movie.getEndTime(startTime);
        // Then
        assertEquals(LocalDateTime.of(2025, 9, 17, 13, 0), endTime);
    }
}
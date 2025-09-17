package domain.cinema;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Theater {
    private static int idCounter = 1;

    private final int id;
    private final List<Screen> screens = new ArrayList<>();

    public Theater() {
        id = idCounter++;
    }

    protected Boolean hasValidScreen(LocalDateTime when, LocalDateTime startTime, Movie movie) {
        for (Screen screen : screens) {
            return screen.willBeScreening(when, startTime, movie);
        }
        return false;
    }

    public Boolean isAvailable(LocalDateTime when, LocalDateTime startTime, Movie movie) {
        if(!hasValidScreen(when, startTime, movie)) {
            return false;
        }
        for (Screen screen : screens) {
            return screen.hasEmptySeat();
        }
        return false;
    }

    public Screen getScreen(LocalDateTime when, LocalDateTime startTime, Movie movie) {
        for (Screen screen : screens) {
            if (screen.willBeScreening(when, startTime, movie)) {
                return screen;
            }
        }
        return null;
    }

    public int getNumber() {
        return id;
    }

    public void screenMovie(Cinema cinema, LocalDateTime when, Movie movie) {
        Screen screen = new Screen();
        screen.playTheMovie(cinema, movie, when);
        screens.add(screen);
    }
}

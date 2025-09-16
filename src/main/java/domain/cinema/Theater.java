package domain.cinema;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Theater {
    private static int idCounter = 1;

    private final int id;
    private List<Screen> screens = new ArrayList<>();

    public Theater() {
        id = idCounter++;
    }

    public Boolean hasValidScreen(LocalDateTime when, Movie movie) {
        for (Screen screen : screens) {
            return screen.willBeScreening(when, movie);
        }
        return false;
    }

    public Boolean isAvailable(LocalDateTime when, Movie movie) {
        if(!hasValidScreen(when, movie)) {
            return false;
        }
        for (Screen screen : screens) {
            return screen.hasEmptySeat();
        }
        return false;
    }

    public Screen getScreen(LocalDateTime when, Movie movie) {
        for (Screen screen : screens) {
            if (screen.willBeScreening(when, movie)) {
                return screen;
            }
        }
        return null;
    }

    public int getNumber() {
        return id;
    }
}

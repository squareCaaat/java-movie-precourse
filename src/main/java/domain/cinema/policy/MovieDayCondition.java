package domain.cinema.policy;

import domain.cinema.Screen;

import java.util.List;

public class MovieDayCondition implements DiscountCondition {
    public MovieDayCondition() {
    }

    @Override
    public Boolean isSatisfiedBy(Screen screen) {
        return List.of(10, 20, 30).contains(screen.getStartTime().getDayOfMonth());
    }
}

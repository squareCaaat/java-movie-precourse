package domain.cinema.policy;

import domain.cinema.Screen;

public class MovieTimeCondition implements DiscountCondition {
    public MovieTimeCondition() {
    }

    @Override
    public Boolean isSatisfiedBy(Screen screen) {
        return screen.getStartTime().getHour() <= 11
            ||  screen.getStartTime().getHour() > 20;
    }
}

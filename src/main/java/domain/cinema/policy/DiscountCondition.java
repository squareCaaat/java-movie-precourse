package domain.cinema.policy;

import domain.cinema.Screen;

public interface DiscountCondition {
    Boolean isSatisfiedBy(Screen screen);
}

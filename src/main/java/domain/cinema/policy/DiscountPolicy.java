package domain.cinema.policy;

import domain.cinema.Screen;

import java.math.BigDecimal;

public interface DiscountPolicy {
    BigDecimal calculateDiscountedPrice(Screen screen, String seatLocation);
}

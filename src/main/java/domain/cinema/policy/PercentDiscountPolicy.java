package domain.cinema.policy;

import domain.cinema.Screen;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PercentDiscountPolicy implements DiscountPolicy{
    private List<DiscountCondition> discountConditions = new ArrayList<>();

    public PercentDiscountPolicy(DiscountCondition... discountConditions) {
        this.discountConditions = Arrays.asList(discountConditions);
    }

    public BigDecimal calculateDiscountedPrice(Screen screen, String seatLocation) {
        for (DiscountCondition discountCondition : discountConditions) {
            if (discountCondition.isSatisfiedBy(screen)) {
                return screen.getCurrentPrice(seatLocation).multiply(BigDecimal.valueOf(0.1));
            }
        }
        return screen.getCurrentPrice(seatLocation);
    }
}

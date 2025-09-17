package domain.cinema.policy;

import domain.cinema.Screen;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AmountDiscountPolicy implements DiscountPolicy {
    private List<DiscountCondition> discountConditions = new ArrayList<>();

    public AmountDiscountPolicy(DiscountCondition... discountConditions) {
        this.discountConditions = Arrays.asList(discountConditions);
    }

    @Override
    public BigDecimal calculateDiscountedPrice(Screen screen, String seatLocation) {
        for (DiscountCondition discountCondition : discountConditions) {
            if (discountCondition.isSatisfiedBy(screen)) {
                return screen.getCurrentPrice(seatLocation).subtract(BigDecimal.valueOf(2000));
            }
        }
        return screen.getCurrentPrice(seatLocation);
    }
}

package domain.reservation;

import java.math.BigDecimal;

public class MembershipPoint {
    private BigDecimal amount;

    public MembershipPoint(BigDecimal amount) {
        this.amount = amount;
    }

    public void earnPoint(BigDecimal points) {
        this.amount = this.amount.add(points);
    }

    public void deductPoint(BigDecimal points) {
        this.amount = this.amount.subtract(points);
    }
}
package domain.reservation;

import java.math.BigDecimal;

public class MembershipPoint {
    private BigDecimal amount;

    public MembershipPoint(BigDecimal amount) {
        this.amount = amount;
    }

    protected BigDecimal getAmount() {
        return amount;
    }

    protected void add(BigDecimal points) {
        this.amount = this.amount.add(points);
    }

    protected void subtract(BigDecimal points) {
        this.amount = this.amount.subtract(points);
    }
}
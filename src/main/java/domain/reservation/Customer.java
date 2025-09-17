package domain.reservation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private BigDecimal balance;
    private MembershipPoint membershipPoint;
    private List<Reservation> reservations = new ArrayList<>();

    public Customer(BigDecimal balance) {
        this.balance = balance;
        membershipPoint = new MembershipPoint(BigDecimal.ZERO);
    }

    protected List<Reservation> getReservations() {
        return reservations;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getPoints() {
        return membershipPoint.getAmount();
    }

    public void earnPoint(BigDecimal points) {
        membershipPoint.add(points);
    }

    public void deductPoint(BigDecimal points) {
        membershipPoint.subtract(points);
    }

    public void withdraw(BigDecimal amount) {
        balance = balance.subtract(amount);
    }
}
package domain.reservation;

import domain.reservation.payment.PaymentMethod;

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

    public List<Reservation> getReservations() {
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

    public Boolean pay(PaymentMethod paymentMethod, BigDecimal pointAmount, BigDecimal paymentAmount) {
        return paymentMethod.pay(this, pointAmount, paymentAmount);
    }
}
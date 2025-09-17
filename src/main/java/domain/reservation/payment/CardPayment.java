package domain.reservation.payment;

import domain.reservation.Customer;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CardPayment extends DefaultPayment {
    public CardPayment() {
        super();
    }

    @Override
    protected Boolean tryPaymentWithDiscount(Customer customer, BigDecimal pointAmount, BigDecimal paymentAmount) {
        BigDecimal willBePaid = paymentAmount.subtract(pointAmount);
        if (customer.getPoints().compareTo(pointAmount) >= 0
                && customer.getBalance().compareTo(willBePaid) >= 0) {
            customer.deductPoint(pointAmount);
            customer.withdraw(willBePaid.multiply(BigDecimal.valueOf(0.95)).setScale(0, RoundingMode.FLOOR));
            return true;
        }
        return false;
    }
}

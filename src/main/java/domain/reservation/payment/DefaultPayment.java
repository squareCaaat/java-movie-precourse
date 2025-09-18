package domain.reservation.payment;

import domain.reservation.Customer;

import java.math.BigDecimal;

public abstract class DefaultPayment implements PaymentMethod {
    public DefaultPayment() {}

    @Override
    public Boolean pay(Customer customer, BigDecimal pointAmount, BigDecimal paymentAmount) {
        if((pointAmount.equals(BigDecimal.ZERO)
            || customer.getPoints().equals(BigDecimal.ZERO))
            && customer.getBalance().compareTo(paymentAmount) >= 0) {
            customer.withdraw(paymentAmount);
            return true;
        }

        return tryPaymentWithDiscount(customer, pointAmount, paymentAmount);
    }

    abstract protected Boolean tryPaymentWithDiscount(Customer customer, BigDecimal pointAmount, BigDecimal paymentAmount);
}

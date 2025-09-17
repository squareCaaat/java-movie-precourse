package domain.reservation.payment;

import domain.reservation.Customer;

import java.math.BigDecimal;

public interface PaymentMethod {
    Boolean pay(Customer customer, BigDecimal pointAmount, BigDecimal paymentAmount);
}

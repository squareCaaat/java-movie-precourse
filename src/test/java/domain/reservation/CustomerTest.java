package domain.reservation;

import domain.reservation.payment.CardPayment;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTest {
    @Test
    void getBalance() {
        // Given
        Customer customer = new Customer(BigDecimal.valueOf(100000));
        // When
        BigDecimal balance = customer.getBalance();
        // Then
        assertEquals(BigDecimal.valueOf(100000), balance);
    }

    @Test
    void getPoints() {
        // Given
        Customer customer = new Customer(BigDecimal.valueOf(100000));
        // When
        BigDecimal points = customer.getPoints();
        // Then
        assertEquals(BigDecimal.ZERO, points);
    }

    @Test
    void earnPoint() {
        // Given
        Customer customer = new Customer(BigDecimal.valueOf(100000));
        // When
        customer.earnPoint(BigDecimal.valueOf(1000));
        // Then
        assertEquals(BigDecimal.valueOf(1000), customer.getPoints());
    }

    @Test
    void deductPoint() {
        // Given
        Customer customer = new Customer(BigDecimal.valueOf(100000));
        // When
        customer.earnPoint(BigDecimal.valueOf(10000));
        customer.deductPoint(BigDecimal.valueOf(5000));
        // Then
        assertEquals(BigDecimal.valueOf(5000), customer.getPoints());
    }

    @Test
    void withdraw() {
        // Given
        Customer customer = new Customer(BigDecimal.valueOf(100000));
        // When
        customer.withdraw(BigDecimal.valueOf(10000));
        // Then
        assertEquals(BigDecimal.valueOf(90000), customer.getBalance());
    }

    @Test
    void pay() {
        // Given
        Customer customer = new Customer(BigDecimal.valueOf(100000));
        // When
        customer.pay(new CardPayment(), BigDecimal.ZERO, BigDecimal.valueOf(50000));
        // Then
        assertEquals(BigDecimal.valueOf(50000), customer.getBalance());
    }
}
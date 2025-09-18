package domain.reservation;

import domain.reservation.payment.CardPayment;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {
    @Test
    void getBalance() {
        // Given
        Customer customer = new Customer(BigDecimal.valueOf(100000));
        // When
        BigDecimal balance = customer.getBalance();
        // Then
        assertThat(balance).isEqualByComparingTo("100000");
    }

    @Test
    void getPoints() {
        // Given
        Customer customer = new Customer(BigDecimal.valueOf(100000));
        // When
        BigDecimal points = customer.getPoints();
        // Then
        assertThat(points).isZero();
    }

    @Test
    void earnPoint() {
        // Given
        Customer customer = new Customer(BigDecimal.valueOf(100000));
        // When
        customer.earnPoint(BigDecimal.valueOf(1000));
        // Then
        assertThat(customer.getPoints()).isEqualByComparingTo("1000");
    }

    @Test
    void deductPoint() {
        // Given
        Customer customer = new Customer(BigDecimal.valueOf(100000));
        // When
        customer.earnPoint(BigDecimal.valueOf(10000));
        customer.deductPoint(BigDecimal.valueOf(5000));
        // Then
        assertThat(customer.getPoints()).isEqualByComparingTo("5000");
    }

    @Test
    void withdraw() {
        // Given
        Customer customer = new Customer(BigDecimal.valueOf(100000));
        // When
        customer.withdraw(BigDecimal.valueOf(10000));
        // Then
        assertThat(customer.getBalance()).isEqualByComparingTo("90000");
    }

    @Test
    void pay() {
        // Given
        Customer customer = new Customer(BigDecimal.valueOf(100000));
        // When
        customer.pay(new CardPayment(), BigDecimal.ZERO, BigDecimal.valueOf(50000));
        // Then
        assertThat(customer.getBalance()).isEqualByComparingTo("50000");
    }
}
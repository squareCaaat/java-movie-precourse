package domain.cinema;

import java.math.BigDecimal;

public enum SeatClass {
    S_CLASS(BigDecimal.valueOf(18000)),
    A_CLASS(BigDecimal.valueOf(15000)),
    B_CLASS(BigDecimal.valueOf(12000));

    private final BigDecimal price;

    SeatClass(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
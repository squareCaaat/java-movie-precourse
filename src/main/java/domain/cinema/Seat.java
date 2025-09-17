package domain.cinema;

import java.math.BigDecimal;

public class Seat {
    private char row;
    private int column;
    private SeatClass seatClass;
    private Boolean isReserved;
    private BigDecimal discountedPrice;

    public Seat(char row, int column) {
        this.row = row;
        this.column = column;
        seatClass = decideSeatClass(this.row);
        discountedPrice = getRegularPrice();
        isReserved = false;
    }

    private SeatClass decideSeatClass(char row){
        if (row == 68) {
            return SeatClass.S_CLASS;
        }

        if (row == 67 || row == 69) {
            return SeatClass.A_CLASS;
        }

        return SeatClass.B_CLASS;
    }

    protected BigDecimal getRegularPrice() {
        return seatClass.getPrice();
    }

    protected BigDecimal getDiscountedPrice() {
        return discountedPrice;
    }

    protected void reflectDiscount(BigDecimal discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    protected Boolean reserve() {
        if (isReserved) {
            return false;
        }
        isReserved = true;
        return true;
    }

    public String getLocation() {
        return String.valueOf(row + column);
    }

    public Boolean isOccupied() {
        return isReserved;
    }
}
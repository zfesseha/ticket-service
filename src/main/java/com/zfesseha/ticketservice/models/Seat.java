package com.zfesseha.ticketservice.models;

/**
 * A simple POJO representing a seat.
 * A seat just has a row number and a column number.
 */
public class Seat {

    private final int row;
    private final int column;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}

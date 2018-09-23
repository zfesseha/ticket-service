package com.zfesseha.ticketservice.models;

public class Seat {

    private final int row;
    private final int column;
    private final SeatStatus status;

    public Seat(int row, int column) {
        this(row, column, SeatStatus.AVAILABLE);
    }

    public Seat(int row, int column, SeatStatus status) {
        this.row = row;
        this.column = column;
        this.status = status;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public SeatStatus getStatus() {
        return status;
    }

//    TODO: Remove
    @Override
    public String toString() {
        return "Seat{" +
                "row=" + row +
                ", column=" + column +
                ", status=" + status +
                '}';
    }
}

package com.zfesseha.ticketservice.models;

import org.joda.time.DateTime;

import java.util.Set;

public class SeatHold implements Entity<Integer> {

    private Integer id;
    private final String customerEmail;
    private final Set<Seat> seats;
    private final DateTime expirationDate;

    public SeatHold(String customerEmail, Set seats, DateTime expirationDate) {
        this(null, customerEmail, seats, expirationDate);
    }

    private SeatHold(Integer id, SeatHold seatHold) {
        this(id, seatHold.getCustomerEmail(), seatHold.getSeats(), seatHold.getExpirationDate());
    }

    private SeatHold(Integer id, String customerEmail, Set seats, DateTime expirationDate) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.seats = seats;
        this.expirationDate = expirationDate;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Entity<Integer> withId(Integer id) {
        return new SeatHold(id, this);
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public DateTime getExpirationDate() {
        return expirationDate;
    }

    //    TODO: Remove
    @Override
    public String toString() {
        return "SeatHold{" +
                "id=" + id +
                ", customerEmail='" + customerEmail + '\'' +
                ", seats=" + seats +
                ", expirationDate=" + expirationDate +
                '}';
    }
}

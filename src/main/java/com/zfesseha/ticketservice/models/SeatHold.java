package com.zfesseha.ticketservice.models;

import java.util.Set;

public class SeatHold implements Entity<Integer> {

    private Integer id;
    private final String customerEmail;
//    TODO: Add additional properties
//    private DateTime expirationDate;
    private final Set<Seat> seats;

    public SeatHold(String customerEmail, Set seats) {
        this(null, customerEmail, seats);
    }

    private SeatHold(Integer id, SeatHold seatHold) {
        this(id, seatHold.getCustomerEmail(), seatHold.getSeats());
    }

    private SeatHold(Integer id, String customerEmail, Set seats) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.seats = seats;
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

    //    TODO: Remove
    @Override
    public String toString() {
        return "SeatHold{" +
                "id='" + id + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", seats=" + seats +
                '}';
    }
}

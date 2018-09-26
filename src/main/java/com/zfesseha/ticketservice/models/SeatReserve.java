package com.zfesseha.ticketservice.models;

import java.util.Set;

public class SeatReserve implements Entity<String> {

    private String id;
    private final String customerEmail;
    private final Set<Seat> seats;

    public SeatReserve(String customerEmail, Set seats) {
        this(null, customerEmail, seats);
    }

    private SeatReserve(String id, SeatReserve seatReserve) {
        this(id, seatReserve.getCustomerEmail(), seatReserve.getSeats());
    }

    private SeatReserve(String id, String customerEmail, Set seats) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.seats = seats;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Entity<String> withId(String id) {
        return new SeatReserve(id, this);
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
        return "SeatReserve{" +
                "id='" + id + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", seats=" + seats +
                '}';
    }
}

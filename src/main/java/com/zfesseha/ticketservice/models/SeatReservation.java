package com.zfesseha.ticketservice.models;

import java.util.Set;

public class SeatReservation implements Entity<String> {

    private String id;
    private final String customerEmail;
    private final Set<Seat> seats;

    public SeatReservation(String customerEmail, Set seats) {
        this(null, customerEmail, seats);
    }

    private SeatReservation(String id, SeatReservation seatReservation) {
        this(id, seatReservation.getCustomerEmail(), seatReservation.getSeats());
    }

    private SeatReservation(String id, String customerEmail, Set seats) {
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
        return new SeatReservation(id, this);
    }

    public static SeatReservation fromSeatHold(SeatHold seatHold) {
        return new SeatReservation(seatHold.getCustomerEmail(), seatHold.getSeats());
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
        return "SeatReservation{" +
                "id='" + id + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", seats=" + seats +
                '}';
    }
}

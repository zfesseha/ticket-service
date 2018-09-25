package com.zfesseha.ticketservice.models;

import java.util.Set;

public class SeatHold {

    private final String id;
    private final String customerEmail;
//    TODO: Add additional properties
//    private DateTime expirationDate;
    private Set<Seat> seats;

    public SeatHold(String customerEmail, Set seats) {
//      TODO: Fix id
        this.id = null;
        this.customerEmail = customerEmail;
        this.seats = seats;
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

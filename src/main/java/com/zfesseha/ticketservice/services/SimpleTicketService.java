package com.zfesseha.ticketservice.services;

import com.zfesseha.ticketservice.comparators.LeftRightFrontBackComparator;
import com.zfesseha.ticketservice.models.Seat;
import com.zfesseha.ticketservice.models.SeatHold;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class SimpleTicketService implements TicketService {

    private TreeSet<Seat> seats;

    public SimpleTicketService(int rowCapacity, int columnCapacity) {
        this.seats = new TreeSet<>(new LeftRightFrontBackComparator());
        for (int i = 0; i < rowCapacity; i++) {
            for (int j = 0; j < columnCapacity; j++) {
                seats.add(new Seat(i, j));
            }
        }
    }

    @Override
    public int numSeatsAvailable() {
        return seats.size();
    }

    @Override
    public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
        Set heldSeats = new HashSet<Seat>(numSeats);
        while (numSeats > 0) {
            heldSeats.add(seats.pollFirst());
            numSeats--;
        }
        return new SeatHold(customerEmail, heldSeats);
    }

    @Override
    public String reserveSeats(int seatHoldId, String customerEmail) {
//        TODO: implement
        return null;
    }
}

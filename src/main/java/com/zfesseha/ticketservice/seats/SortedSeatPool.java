package com.zfesseha.ticketservice.seats;

import com.zfesseha.ticketservice.models.Seat;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class SortedSeatPool implements SeatPool {

    private TreeSet<Seat> seats;

    public SortedSeatPool(Comparator<Seat> comparator) {
        this.seats = new TreeSet<>(comparator);
    }

    // TODO: should not be allowed. Use builder and make immutable
    @Override
    public SeatPool add(Seat seat) {
        seats.add(seat);
        return this;
    }

    @Override
    public Set<Seat> getSeats(int numSeats) {
        Set<Seat> seatsToReturn = new HashSet<>(numSeats);
        while (numSeats > 0) {
            seatsToReturn.add(seats.pollFirst());
            numSeats--;
        }
        return seatsToReturn;
    }

    @Override
    public int getCurrentCapacity() {
        return this.seats.size();
    }
}
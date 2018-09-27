package com.zfesseha.ticketservice.seats;

import com.zfesseha.ticketservice.exceptions.NotEnoughAvailableSeatsException;
import com.zfesseha.ticketservice.models.Seat;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * An implementation of {@link SeatPool} that stores and returns {@link Seat}s based on priority order.
 * The priority order of seats is provided by the `comparator` property.
 */
public class SortedSeatPool implements SeatPool {

    private TreeSet<Seat> seats;

    public SortedSeatPool(Comparator<Seat> comparator) {
        this.seats = new TreeSet<>(comparator);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SeatPool add(Seat seat) {
        seats.add(seat);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Seat> getSeats(int numSeats) throws NotEnoughAvailableSeatsException {
        if (numSeats > this.seats.size()) {
            throw new NotEnoughAvailableSeatsException(numSeats, this.seats.size());
        }
        Set<Seat> seatsToReturn = new HashSet<>(numSeats);
        while (numSeats > 0) {
            seatsToReturn.add(seats.pollFirst());
            numSeats--;
        }
        return seatsToReturn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCurrentCapacity() {
        return this.seats.size();
    }
}

package com.zfesseha.ticketservice.seats;

import com.zfesseha.ticketservice.exceptions.NotEnoughAvailableSeatsException;
import com.zfesseha.ticketservice.models.Seat;

import java.util.Set;

/**
 * An interface that represents a pool of {@link Seat} objects.
 * This pool can be used to fulfill seat requests.
 */
public interface SeatPool {

    /**
     * Adds a seat to the pool of seats.
     *
     * @param seat  The seat to be added to the pool
     * @return  this seat pool after the given seat has been added.
     */
    SeatPool add(Seat seat);

    /**
     *
     * Returns a set of {@link Seat}s containing quantity of seats determined `numSeats`.
     *
     * @param numSeats number of seats be
     * @return  A set of {@link Seat}s.
     * @throws NotEnoughAvailableSeatsException when the number of seats requested exceeds what's available.
     */
    Set<Seat> getSeats(int numSeats) throws NotEnoughAvailableSeatsException;

    /**
     * Returns the number of seats available in the pool.
     *
     * @return  the number of seats available in the pool.
     */
    int getCurrentCapacity();
}

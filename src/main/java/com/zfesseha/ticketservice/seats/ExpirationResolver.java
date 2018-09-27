package com.zfesseha.ticketservice.seats;

import org.joda.time.DateTime;

/**
 * A simple interface that determines the expiration date for a seat hold request.
 */
public interface ExpirationResolver {

    /**
     * Returns a {@link DateTime} representing the expiration date for the seat hold request.
     *
     * @param numSeats      number of seats requested.
     * @param customerEmail email of customer making the request.
     * @return  A {@link DateTime} representing the expiration date of request.
     */
    DateTime expirationDate(int numSeats, String customerEmail);

}

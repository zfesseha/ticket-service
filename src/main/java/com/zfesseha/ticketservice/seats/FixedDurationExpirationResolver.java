package com.zfesseha.ticketservice.seats;

import org.joda.time.DateTime;

/**
 * A simple implementation of a {@link ExpirationResolver} that sets the expiration date
 * for a given seat hold request to `numSeconds` after now.
 */
public class FixedDurationExpirationResolver implements ExpirationResolver {

    private int numSeconds;

    public FixedDurationExpirationResolver(int numSeconds) {
        this.numSeconds = numSeconds;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DateTime expirationDate(int numSeats, String customerEmail) {
        return DateTime.now().plusSeconds(numSeconds);
    }
}

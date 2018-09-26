package com.zfesseha.ticketservice.seats;

import org.joda.time.DateTime;

public class FixedDurationExpirationResolver implements ExpirationResolver {

    private int numSeconds;

    public FixedDurationExpirationResolver(int numSeconds) {
        this.numSeconds = numSeconds;
    }

    @Override
    public DateTime expirationDate(int numSeats, String customerEmail) {
        return DateTime.now().plusSeconds(numSeconds);
    }
}

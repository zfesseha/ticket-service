package com.zfesseha.ticketservice.exceptions;

/**
 * A simple exception that represents an error that occurs when the number of requested
 * seats exceeds what's available in the system.
 */
public class NotEnoughAvailableSeatsException extends RuntimeException {

    public NotEnoughAvailableSeatsException(int requested, int available) {
        super(String.format("The requested number of seats, [%d], is more than what's available, [%d].",
                requested, available));
    }
}

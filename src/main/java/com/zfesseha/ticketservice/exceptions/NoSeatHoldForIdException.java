package com.zfesseha.ticketservice.exceptions;

/**
 * A simple exception that represents an error that occurs when a given
 * seatHold ID doesn't have a matching entry in the system.
 */
public class NoSeatHoldForIdException extends RuntimeException {

    public NoSeatHoldForIdException(Integer id) {
        super("Couldn't find SeatHold for given id: [" + id + "].");
    }
}

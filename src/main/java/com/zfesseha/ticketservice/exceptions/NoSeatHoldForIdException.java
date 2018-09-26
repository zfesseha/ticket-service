package com.zfesseha.ticketservice.exceptions;

public class NoSeatHoldForIdException extends RuntimeException {

    public NoSeatHoldForIdException(Integer id) {
        super("Couldn't find SeatHold for given id: [" + id + "].");
    }
}

package com.zfesseha.ticketservice.exceptions;

public class NotEnoughAvailableSeatsException extends RuntimeException {

    public NotEnoughAvailableSeatsException(int requested, int available) {
        super(String.format("The requested number of seats, [%d], is more than what's available, [%d].",
                requested, available));
    }
}

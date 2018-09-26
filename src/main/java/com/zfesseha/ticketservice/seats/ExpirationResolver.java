package com.zfesseha.ticketservice.seats;

import org.joda.time.DateTime;

public interface ExpirationResolver {

    DateTime expirationDate(int numSeats, String customerEmail);

}

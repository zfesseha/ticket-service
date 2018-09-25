package com.zfesseha.ticketservice.pool;

import com.zfesseha.ticketservice.models.Seat;

import java.util.Set;

public interface SeatPool {

    SeatPool add(Seat seat);

    Set<Seat> getSeats(int numSeats);

    int getCurrentCapacity();
}

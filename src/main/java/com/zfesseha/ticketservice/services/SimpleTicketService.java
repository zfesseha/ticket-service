package com.zfesseha.ticketservice.services;

import com.zfesseha.ticketservice.models.SeatHold;
import com.zfesseha.ticketservice.models.SeatReserve;
import com.zfesseha.ticketservice.pool.SeatPool;


public class SimpleTicketService implements TicketService {

    private SeatPool seatPool;

    public SimpleTicketService(SeatPool seatPool) {
        this.seatPool = seatPool;
    }

    @Override
    public int numSeatsAvailable() {
        return seatPool.getCurrentCapacity();
    }

    @Override
    public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
        SeatHold seatHold = new SeatHold(customerEmail, seatPool.getSeats(numSeats));
        return seatHold;
    }

    @Override
    public String reserveSeats(int seatHoldId, String customerEmail) {
//        TODO: implement
        return null;
    }
}

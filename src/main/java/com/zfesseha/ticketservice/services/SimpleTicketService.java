package com.zfesseha.ticketservice.services;

import com.zfesseha.ticketservice.models.Seat;
import com.zfesseha.ticketservice.models.SeatHold;
import com.zfesseha.ticketservice.pool.SeatPool;


public class SimpleTicketService implements TicketService {

    private SeatPool seatPool;

    public SimpleTicketService(SeatPool seatPool, int rowCapacity, int columnCapacity) {
        this.seatPool = seatPool;
        for (int i = 0; i < rowCapacity; i++) {
            for (int j = 0; j < columnCapacity; j++) {
                this.seatPool.add(new Seat(i, j));
            }
        }
    }

    @Override
    public int numSeatsAvailable() {
        return seatPool.getCurrentCapacity();
    }

    @Override
    public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
        return new SeatHold(customerEmail, seatPool.getSeats(numSeats));
    }

    @Override
    public String reserveSeats(int seatHoldId, String customerEmail) {
//        TODO: implement
        return null;
    }
}

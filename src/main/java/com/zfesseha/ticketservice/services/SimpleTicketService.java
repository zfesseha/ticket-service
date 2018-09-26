package com.zfesseha.ticketservice.services;

import com.zfesseha.ticketservice.dao.SeatHoldDAO;
import com.zfesseha.ticketservice.dao.SeatReserveDAO;
import com.zfesseha.ticketservice.exceptions.NoSeatHoldForIdException;
import com.zfesseha.ticketservice.models.SeatHold;
import com.zfesseha.ticketservice.models.SeatReserve;
import com.zfesseha.ticketservice.pool.SeatPool;

public class SimpleTicketService implements TicketService {

    private SeatPool seatPool;
    private SeatHoldDAO seatHoldDao;
    private SeatReserveDAO seatReserveDao;

    public SimpleTicketService(SeatPool seatPool, SeatHoldDAO seatHoldDAO, SeatReserveDAO seatReserveDAO) {
        this.seatPool = seatPool;
        this.seatHoldDao = seatHoldDAO;
        this.seatReserveDao = seatReserveDAO;
    }

    @Override
    public int numSeatsAvailable() {
        return seatPool.getCurrentCapacity();
    }

    @Override
    public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
        SeatHold seatHold = seatHoldDao.save(new SeatHold(customerEmail, seatPool.getSeats(numSeats)));
        return seatHold;
    }

    @Override
    public String reserveSeats(int seatHoldId, String customerEmail) {
        SeatHold seatHold = seatHoldDao.remove(seatHoldId);
        if (seatHold == null) {
            throw new NoSeatHoldForIdException(seatHoldId);
        }
        SeatReserve seatReserve = seatReserveDao.save(SeatReserve.fromSeatHold(seatHold));
        return seatReserve.getId();
    }
}

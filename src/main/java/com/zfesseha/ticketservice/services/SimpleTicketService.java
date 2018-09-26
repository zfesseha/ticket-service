package com.zfesseha.ticketservice.services;

import com.zfesseha.ticketservice.dao.SeatHoldDAO;
import com.zfesseha.ticketservice.dao.SeatReservationDAO;
import com.zfesseha.ticketservice.exceptions.NoSeatHoldForIdException;
import com.zfesseha.ticketservice.exceptions.NotEnoughAvailableSeatsException;
import com.zfesseha.ticketservice.models.SeatHold;
import com.zfesseha.ticketservice.models.SeatReservation;
import com.zfesseha.ticketservice.seats.ExpirationResolver;
import com.zfesseha.ticketservice.seats.SeatPool;
import com.zfesseha.ticketservice.tasks.ExpirationTaskManager;
import org.joda.time.DateTime;

public class SimpleTicketService implements TicketService {

    private SeatPool seatPool;
    private ExpirationResolver expirationResolver;
    private SeatHoldDAO seatHoldDao;
    private SeatReservationDAO seatReservationDao;
    private ExpirationTaskManager taskManager;

    public SimpleTicketService(SeatPool seatPool, ExpirationResolver expirationResolver,
                               SeatHoldDAO seatHoldDAO, SeatReservationDAO seatReservationDAO) {
        this.seatPool = seatPool;
        this.expirationResolver = expirationResolver;
        this.seatHoldDao = seatHoldDAO;
        this.seatReservationDao = seatReservationDAO;
        this.taskManager = new ExpirationTaskManager(this.seatPool, this.seatHoldDao);
    }

    @Override
    public int numSeatsAvailable() {
        return seatPool.getCurrentCapacity();
    }

    @Override
    public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
        if (numSeats > numSeatsAvailable()) {
            throw new NotEnoughAvailableSeatsException(numSeats, numSeatsAvailable());
        }
        DateTime expiration = expirationResolver.expirationDate(numSeats, customerEmail);
        SeatHold seatHold = seatHoldDao.save(new SeatHold(customerEmail, seatPool.getSeats(numSeats), expiration));
        taskManager.registerSeatHold(seatHold);
        return seatHold;
    }

    @Override
    public String reserveSeats(int seatHoldId, String customerEmail) {
        SeatHold seatHold = seatHoldDao.remove(seatHoldId);
        // TODO: Should this complain about non-matching email?
        if (seatHold == null) {
            throw new NoSeatHoldForIdException(seatHoldId);
        }
        taskManager.cancelSeatHold(seatHoldId);
        SeatReservation seatReservation = seatReservationDao.save(SeatReservation.fromSeatHold(seatHold));
        return seatReservation.getId();
    }
}

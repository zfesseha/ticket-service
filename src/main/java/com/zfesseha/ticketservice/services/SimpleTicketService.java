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

/**
 * An implementation of {@link TicketService} that utilizes injected properties to serve requests.
 *
 * It uses a provided {@link SeatPool} to determine which seats it should hold.
 * It uses a provided {@link ExpirationResolver} to determine the expiration time of held seats.
 * It uses provided DAOs to store held and reserved seats.
 * It uses a {@link ExpirationTaskManager} to make sure held seats are returned back to the pool once expired.
 */
public class SimpleTicketService implements TicketService {

    private SeatPool seatPool;
    private ExpirationResolver expirationResolver;
    private SeatHoldDAO seatHoldDao;
    private SeatReservationDAO seatReservationDao;
    private ExpirationTaskManager taskManager;

    /**
     * The main constructor.
     *
     * @param seatPool              used to manage the inventory of seats.
     * @param expirationResolver    used to determine the expiration time of held seats.
     * @param seatHoldDAO           used to store held seats.
     * @param seatReservationDAO    used to store reserved seats.
     */
    public SimpleTicketService(SeatPool seatPool, ExpirationResolver expirationResolver,
                               SeatHoldDAO seatHoldDAO, SeatReservationDAO seatReservationDAO) {
        this.seatPool = seatPool;
        this.expirationResolver = expirationResolver;
        this.seatHoldDao = seatHoldDAO;
        this.seatReservationDao = seatReservationDAO;
        this.taskManager = new ExpirationTaskManager(this.seatPool, this.seatHoldDao);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int numSeatsAvailable() {
        return seatPool.getCurrentCapacity();
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String reserveSeats(int seatHoldId, String customerEmail) {
        SeatHold seatHold = seatHoldDao.remove(seatHoldId);
        if (seatHold == null) {
            throw new NoSeatHoldForIdException(seatHoldId);
        }
        taskManager.cancelSeatHold(seatHoldId);
        SeatReservation seatReservation = seatReservationDao.save(SeatReservation.fromSeatHold(seatHold));
        return seatReservation.getId();
    }
}

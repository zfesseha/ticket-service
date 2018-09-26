package com.zfesseha.ticketservice.services;

import com.zfesseha.ticketservice.dao.SeatHoldDAO;
import com.zfesseha.ticketservice.dao.SeatReserveDAO;
import com.zfesseha.ticketservice.models.SeatHold;
import com.zfesseha.ticketservice.models.SeatReserve;
import com.zfesseha.ticketservice.pool.SeatPool;
import com.zfesseha.ticketservice.pool.SeatPoolFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimpleTicketServiceTest {

    private SimpleTicketService ticketService;
    private SeatHoldDAO seatHoldDAO;
    private SeatReserveDAO seatReserveDAO;

    private static final String TEST_CUSTOMER_EMAIL = "test@email.com";

    @Before
    public void setUp() throws Exception {
        SeatPool seatPool = SeatPoolFactory.leftRightFrontBackRectangularSeatPool(20, 20);
        seatHoldDAO = new SeatHoldDAO();
        seatReserveDAO = new SeatReserveDAO();
        ticketService = new SimpleTicketService(seatPool, seatHoldDAO, seatReserveDAO);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testOriginalAvailableSeats() {
        assertEquals("Number of available seats doesn't match expected", 400, ticketService.numSeatsAvailable());
    }

    @Test
    public void testAvailableSeatsAfterReservation() {
        ticketService.findAndHoldSeats(10, TEST_CUSTOMER_EMAIL);
        assertEquals("Number of available seats doesn't match expected", 390, ticketService.numSeatsAvailable());
        ticketService.findAndHoldSeats(20, TEST_CUSTOMER_EMAIL);
        assertEquals("Number of available seats doesn't match expected", 370, ticketService.numSeatsAvailable());
    }

    @Test
    public void testFindAndHoldSeats() {
        SeatHold seatHold = ticketService.findAndHoldSeats(10, TEST_CUSTOMER_EMAIL);
        System.out.println(seatHold);
        seatHold = ticketService.findAndHoldSeats(9, TEST_CUSTOMER_EMAIL);
        System.out.println(seatHold);
        seatHold = ticketService.findAndHoldSeats(3, TEST_CUSTOMER_EMAIL);
        System.out.println(seatHold);
        System.out.println("-------");
        System.out.println(seatHoldDAO.getAll());
    }

    @Test
    public void testReserveSeats() {
    }
}
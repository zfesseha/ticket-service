package com.zfesseha.ticketservice.services;

import com.zfesseha.ticketservice.dao.SeatHoldDAO;
import com.zfesseha.ticketservice.dao.SeatReservationDAO;
import com.zfesseha.ticketservice.exceptions.NoSeatHoldForIdException;
import com.zfesseha.ticketservice.models.SeatHold;
import com.zfesseha.ticketservice.models.SeatReservation;
import com.zfesseha.ticketservice.pool.SeatPool;
import com.zfesseha.ticketservice.pool.SeatPoolFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SimpleTicketServiceTest {

    private SimpleTicketService ticketService;
    private SeatHoldDAO seatHoldDAO;
    private SeatReservationDAO seatReservationDAO;

    private static final String TEST_CUSTOMER_EMAIL = "test@email.com";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        SeatPool seatPool = SeatPoolFactory.leftRightFrontBackRectangularSeatPool(20, 20);
        seatHoldDAO = new SeatHoldDAO();
        seatReservationDAO = new SeatReservationDAO();
        ticketService = new SimpleTicketService(seatPool, seatHoldDAO, seatReservationDAO);
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
    public void testHoldSeats_ReturnedHoldObjectIsGood() {
        SeatHold seatHold = ticketService.findAndHoldSeats(10, TEST_CUSTOMER_EMAIL);
        assertEquals("Number of seats held doesn't match expected.", 10, seatHold.getSeats().size());
        assertEquals("Customer email doesn't match expected.", TEST_CUSTOMER_EMAIL, seatHold.getCustomerEmail());
        verifySeatHoldsEqual(seatHold, seatHoldDAO.get(seatHold.getId()));
        seatHold = ticketService.findAndHoldSeats(9, TEST_CUSTOMER_EMAIL);
        verifySeatHoldsEqual(seatHold, seatHoldDAO.get(seatHold.getId()));
    }

    @Test
    public void testHoldSeats_DaoSavesHoldObjectsCorrectly() {
        SeatHold seatHold = ticketService.findAndHoldSeats(10, TEST_CUSTOMER_EMAIL);
        verifySeatHoldsEqual(seatHold, seatHoldDAO.get(seatHold.getId()));
        seatHold = ticketService.findAndHoldSeats(9, TEST_CUSTOMER_EMAIL);
        verifySeatHoldsEqual(seatHold, seatHoldDAO.get(seatHold.getId()));
        seatHold = ticketService.findAndHoldSeats(4, TEST_CUSTOMER_EMAIL);
        verifySeatHoldsEqual(seatHold, seatHoldDAO.get(seatHold.getId()));
        assertEquals("Number of items in seatHoldDAO doesn't match expected.", 3, seatHoldDAO.getAll().size());
    }

    @Test
    public void testReserveSeats_CanReserveHeldSeats() {
        SeatHold seatHold = ticketService.findAndHoldSeats(7, TEST_CUSTOMER_EMAIL);
        String reservationId = ticketService.reserveSeats(seatHold.getId(), seatHold.getCustomerEmail());
        SeatReservation reservation = seatReservationDAO.get(reservationId);
        verifyReservationDetails(reservation, seatHold);
        assertEquals("Number of items in seatReservationDAO doesn't match expected.", 1, seatReservationDAO.getAll().size());
    }

    @Test
    public void testReserveSeats_InvalidHoldIDThrowsException() {
        SeatHold seatHold = ticketService.findAndHoldSeats(7, TEST_CUSTOMER_EMAIL);

        thrown.expect(NoSeatHoldForIdException.class);
        thrown.expectMessage("Couldn't find SeatHold for given id: [100].");
        ticketService.reserveSeats(100, seatHold.getCustomerEmail());
    }

    @Test
    public void testReserveSeats_heldSeatsAreRemovedFromSeatHoldDao() {
        SeatHold seatHold = ticketService.findAndHoldSeats(4, TEST_CUSTOMER_EMAIL);
        SeatHold seatHold2 = ticketService.findAndHoldSeats(8, TEST_CUSTOMER_EMAIL);
        assertEquals("Number of items in seatHoldDAO doesn't match expected.", 2, seatHoldDAO.getAll().size());

        String reservationId = ticketService.reserveSeats(seatHold.getId(), seatHold.getCustomerEmail());
        assertEquals("Number of items in seatHoldDAO should decrease by 1", 1, seatHoldDAO.getAll().size());
        assertNull("SeatHold should be removed from SeatHoldDAO", seatHoldDAO.get(seatHold.getId()));
        assertNotNull("SeatHold2 should not be removed from SeatHoldDAO", seatHoldDAO.get(seatHold2.getId()));
    }

    private void verifySeatHoldsEqual(SeatHold seatHold1, SeatHold seatHold2) {
        assertEquals("Ids of the two seatHolds don't match.", seatHold1.getId(), seatHold2.getId());
        assertEquals("Customer emails of the two seatHolds don't match.", seatHold1.getCustomerEmail(), seatHold2.getCustomerEmail());
        assertEquals("Held seats of the two seatHolds don't match.", seatHold1.getSeats(), seatHold2.getSeats());
    }

    private void verifyReservationDetails(SeatReservation seatReservation, SeatHold seatHold) {
        assertEquals("Customer email of the reservation doesn't match SeatHold.", seatHold.getCustomerEmail(), seatReservation.getCustomerEmail());
        assertEquals("Reserved seats on the reservation don't match SeatHold.", seatHold.getSeats(), seatReservation.getSeats());
    }
}
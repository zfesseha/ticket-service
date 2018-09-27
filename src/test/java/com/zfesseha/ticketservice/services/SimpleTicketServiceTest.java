package com.zfesseha.ticketservice.services;

import com.zfesseha.ticketservice.dao.SeatHoldDAO;
import com.zfesseha.ticketservice.dao.SeatReservationDAO;
import com.zfesseha.ticketservice.exceptions.NoSeatHoldForIdException;
import com.zfesseha.ticketservice.exceptions.NotEnoughAvailableSeatsException;
import com.zfesseha.ticketservice.models.SeatHold;
import com.zfesseha.ticketservice.models.SeatReservation;
import com.zfesseha.ticketservice.seats.FixedDurationExpirationResolver;
import com.zfesseha.ticketservice.seats.SeatPool;
import com.zfesseha.ticketservice.seats.SeatPoolFactory;
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
    private static final int EXPIRATION_DURATION_SECONDS = 5;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        SeatPool seatPool = SeatPoolFactory.leftRightFrontBackRectangularSeatPool(20, 20);
        seatHoldDAO = new SeatHoldDAO();
        seatReservationDAO = new SeatReservationDAO();
        FixedDurationExpirationResolver expirationResolver = new FixedDurationExpirationResolver(EXPIRATION_DURATION_SECONDS);
        ticketService = new SimpleTicketService(seatPool, expirationResolver, seatHoldDAO, seatReservationDAO);
    }

    @Test
    public void testOriginalAvailableSeats() {
        assertEquals("Number of available seats doesn't match expected", 400, ticketService.numSeatsAvailable());
    }

    @Test
    public void testAvailableSeatsAfterHold() {
        ticketService.findAndHoldSeats(10, TEST_CUSTOMER_EMAIL);
        assertEquals("Number of available seats doesn't match expected", 390, ticketService.numSeatsAvailable());
        ticketService.findAndHoldSeats(20, TEST_CUSTOMER_EMAIL);
        assertEquals("Number of available seats doesn't match expected", 370, ticketService.numSeatsAvailable());
    }

    @Test
    public void testNotEnoughAvailableSeatsLargeInitialHold() {
        thrown.expect(NotEnoughAvailableSeatsException.class);
        thrown.expectMessage("The requested number of seats, [402], is more than what's available, [400].");
        ticketService.findAndHoldSeats(402, TEST_CUSTOMER_EMAIL);
    }

    @Test
    public void testNotEnoughAvailableSeatsAfterFewHolds() {
        ticketService.findAndHoldSeats(100, TEST_CUSTOMER_EMAIL);
        ticketService.findAndHoldSeats(100, TEST_CUSTOMER_EMAIL);
        ticketService.findAndHoldSeats(100, TEST_CUSTOMER_EMAIL);
        ticketService.findAndHoldSeats(98, TEST_CUSTOMER_EMAIL);

        thrown.expect(NotEnoughAvailableSeatsException.class);
        thrown.expectMessage("The requested number of seats, [3], is more than what's available, [2].");
        ticketService.findAndHoldSeats(3, TEST_CUSTOMER_EMAIL);
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
    public void testHoldSeats_HeldSeatsAreUnHeldAfterExpiration() throws Exception {
        SeatHold seatHold = ticketService.findAndHoldSeats(5, TEST_CUSTOMER_EMAIL);
        Thread.sleep(3000);
        SeatHold seatHold2 = ticketService.findAndHoldSeats(15, TEST_CUSTOMER_EMAIL);
        assertEquals("Number of items in seatHoldDAO doesn't match expected.", 2, seatHoldDAO.getAll().size());
        assertEquals("Number of available seats doesn't match expected", 380, ticketService.numSeatsAvailable());
        assertNotNull("There should be an entry for the first SeatHold object.", seatHoldDAO.get(seatHold.getId()));
        assertNotNull("There should be an entry for the second SeatHold object.", seatHoldDAO.get(seatHold2.getId()));

        Thread.sleep(3000);
        // Six seconds after original seat hold request
        assertEquals("Number of available seats should have increased by 5.", 385, ticketService.numSeatsAvailable());
        assertNull("There should NOT be an entry for the first SeatHold object.", seatHoldDAO.get(seatHold.getId()));
        assertNotNull("There should be an entry for the second SeatHold object.", seatHoldDAO.get(seatHold2.getId()));

        Thread.sleep(3000);
        // Nine seconds after original seat hold request
        assertEquals("Number of available seats should have increased by 15.", 400, ticketService.numSeatsAvailable());
        assertNull("There should NOT be an entry for the first SeatHold object.", seatHoldDAO.get(seatHold.getId()));
        assertNull("There should NOT be an entry for the second SeatHold object.", seatHoldDAO.get(seatHold2.getId()));
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
    public void testReserveSeats_ReservationShouldFailIfHoldExpires() throws Exception {
        SeatHold seatHold = ticketService.findAndHoldSeats(7, TEST_CUSTOMER_EMAIL);
        Thread.sleep(6000);
        thrown.expect(NoSeatHoldForIdException.class);
        thrown.expectMessage("Couldn't find SeatHold for given id: [" + seatHold.getId() + "].");
        ticketService.reserveSeats(seatHold.getId(), seatHold.getCustomerEmail());
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
        assertEquals("Expiration dates of the two seatHolds don't match.", seatHold1.getExpirationDate(), seatHold2.getExpirationDate());
    }

    private void verifyReservationDetails(SeatReservation seatReservation, SeatHold seatHold) {
        assertEquals("Customer email of the reservation doesn't match SeatHold.", seatHold.getCustomerEmail(), seatReservation.getCustomerEmail());
        assertEquals("Reserved seats on the reservation don't match SeatHold.", seatHold.getSeats(), seatReservation.getSeats());
    }
}
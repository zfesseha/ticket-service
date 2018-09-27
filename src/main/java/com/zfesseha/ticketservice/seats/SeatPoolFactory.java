package com.zfesseha.ticketservice.seats;

import com.zfesseha.ticketservice.comparators.LeftRightFrontBackComparator;
import com.zfesseha.ticketservice.models.Seat;

/**
 * A simple factory to create and initialize various types of {@link SeatPool}s.
 */
public class SeatPoolFactory {

    /**
     * Returns a {@link SeatPool} that represents a rectangular arrangement with the given capacity values.
     *
     * @param rowCapacity       the number of rows of the rectangular {@link SeatPool}
     * @param columnCapacity    the number of columns of the rectangular {@link SeatPool}
     * @return  A seat pool representing a rectangular arrangement..
     */
    public static SeatPool leftRightFrontBackRectangularSeatPool(int rowCapacity, int columnCapacity) {
        SeatPool seatPool = new SortedSeatPool(new LeftRightFrontBackComparator());
        for (int i = 0; i < rowCapacity; i++) {
            for (int j = 0; j < columnCapacity; j++) {
                seatPool.add(new Seat(i, j));
            }
        }
        return seatPool;
    }
}

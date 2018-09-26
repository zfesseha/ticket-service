package com.zfesseha.ticketservice.seats;

import com.zfesseha.ticketservice.comparators.LeftRightFrontBackComparator;
import com.zfesseha.ticketservice.models.Seat;

public class SeatPoolFactory {

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

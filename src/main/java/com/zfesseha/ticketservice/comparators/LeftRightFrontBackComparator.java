package com.zfesseha.ticketservice.comparators;

import com.zfesseha.ticketservice.models.Seat;

import java.util.Comparator;

/**
 * A simple comparator implementation that ranks seat as follows.
 * A seat closer to the front has higher priority than a seat further to the back.
 * If two seats are in the same row, then the seat closer to the left has more priority.
 */
public class LeftRightFrontBackComparator implements Comparator<Seat> {

    @Override
    public int compare(Seat seat1, Seat seat2) {
        int rowComparison = seat1.getRow() - seat2.getRow();
        if (rowComparison != 0) {
            return rowComparison;
        }
        return seat1.getColumn() - seat2.getColumn();
    }
}

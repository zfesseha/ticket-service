package com.zfesseha.ticketservice.comparators;

import com.zfesseha.ticketservice.models.Seat;

import java.util.Comparator;

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

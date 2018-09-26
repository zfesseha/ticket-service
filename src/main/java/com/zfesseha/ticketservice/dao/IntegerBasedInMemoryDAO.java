package com.zfesseha.ticketservice.dao;

import com.zfesseha.ticketservice.models.Entity;

public class IntegerBasedInMemoryDAO<E extends Entity<Integer>> extends InMemoryDAO<Integer, E> {

    private int incrementalId;

    public IntegerBasedInMemoryDAO() {
        super();
        this.incrementalId = 0;
    }

    @Override
    Integer newId() {
        incrementalId++;
        return incrementalId;
    }
}

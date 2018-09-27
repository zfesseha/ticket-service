package com.zfesseha.ticketservice.dao;

import com.zfesseha.ticketservice.models.Entity;

/**
 * An implementation of {@link InMemoryDAO} whose ID object is of type {@link Integer}.
 * The IDs are auto incremented integers.
 *
 * @param <E>   the type of entity for which this DAO will be used.
 */
public class IntegerBasedInMemoryDAO<E extends Entity<Integer>> extends InMemoryDAO<Integer, E> {

    private int incrementalId;

    public IntegerBasedInMemoryDAO() {
        super();
        this.incrementalId = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer newId() {
        incrementalId++;
        return incrementalId;
    }
}

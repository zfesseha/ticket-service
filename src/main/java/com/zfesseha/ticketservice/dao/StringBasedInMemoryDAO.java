package com.zfesseha.ticketservice.dao;

import com.zfesseha.ticketservice.models.Entity;

import java.util.UUID;

/**
 * An implementation of {@link InMemoryDAO} whose ID object is of type {@link Integer}.
 * The IDS are string representations of simple {@link UUID}s.
 *
 * @param <E>   the type of entity for which this DAO will be used.
 */
public class StringBasedInMemoryDAO<E extends Entity<String>> extends InMemoryDAO<String, E> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected String newId() {
        String id;
        do {
            id = UUID.randomUUID().toString();
        } while (storage.containsKey(id));
        return id;
    }
}

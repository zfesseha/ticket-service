package com.zfesseha.ticketservice.models;

/**
 * A simple interface that represents an entity with an ID.
 * Implementing classes can be used with the {@link com.zfesseha.ticketservice.dao.EntityDAO} interface.
 *
 * @param <I>   the type of the ID for the entity.
 */
public interface Entity<I> {

    /**
     * Returns the ID of this entity.
     *
     * @return the ID of this entity
     */
    I getId();

    /**
     * Only here to simulate DAO functionality. Should only be called by DAOs.
     * @param id
     */
    Entity<I> withId(I id);
}

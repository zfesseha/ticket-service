package com.zfesseha.ticketservice.dao;

import com.zfesseha.ticketservice.models.Entity;

import java.util.Collection;

/**
 * A simple DAO interface that can be used to access objects of type {@link Entity}.
 *
 * @param <I>   the type of the ID for the object.
 * @param <E>   the type of entity for which this DAO will be used.
 */
public interface EntityDAO<I, E extends Entity<I>> {

    /**
     * Saves the provided {@link Entity}, and returns the saved object.
     *
     * @param entity    the entity to be saved.
     * @return  the entity saved.
     */
    E save(E entity);

    /**
     * Returns the entity identified by the given ID.
     *
     * @param id    the ID of the entity to be retrieved.
     * @return  the entity identified by the given ID, `null` if no such entity exists.
     */
    E get(I id);

    /**
     * Removes the entity identified by the given ID.
     *
     * @param id    the ID of the entity to be removed.
     * @return  the entity identified by the given ID. `null` if no such entity exists.
     */
    E remove(I id);

    /**
     * Returns a collection of all entities stored.
     *
     * @return  a collection consisting of all saved entities. Empty collection if no entites have been saved.
     */
    Collection<E> getAll();
}

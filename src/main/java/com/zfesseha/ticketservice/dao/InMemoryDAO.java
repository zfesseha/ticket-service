package com.zfesseha.ticketservice.dao;

import com.zfesseha.ticketservice.models.Entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * An in memory implementation of {@link EntityDAO}. This class is abstract because it expects
 * extending classes to define how new IDs should be created.
 *
 * @param <I>   the type of the ID for the object.
 * @param <E>   the type of entity for which this DAO will be used.
 */
public abstract class InMemoryDAO<I, E extends Entity<I>> implements EntityDAO<I, E> {

    protected Map<I, E> storage;

    public InMemoryDAO() {
        this.storage = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E save(E entity) {
        E newEntity = entity.getId() == null ? (E) (entity.withId(newId())) : entity;
        storage.put(newEntity.getId(), newEntity);
        return newEntity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E get(I id) {
        return storage.get(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E remove(I id) {
        return storage.remove(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<E> getAll() {
        return storage.values();
    }

    /**
     * Returns a new ID with the appropriate type to be used when saving new entities.
     *
     * @return a new ID.
     */
    abstract protected I newId();
}

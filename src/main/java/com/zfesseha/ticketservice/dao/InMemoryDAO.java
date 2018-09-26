package com.zfesseha.ticketservice.dao;

import com.zfesseha.ticketservice.models.Entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class InMemoryDAO<I, E extends Entity<I>> implements EntityDAO<I, E> {

    protected Map<I, E> storage;

    public InMemoryDAO() {
        this.storage = new HashMap<>();
    }

    @Override
    public E save(E entity) {
        // TODO: Separate method for update. Check if ID exists.
        E newEntity = entity.getId() == null ? (E) (entity.withId(newId())) : entity;
        storage.put(newEntity.getId(), newEntity);
        return newEntity;
    }

    @Override
    public E get(I id) {
        return storage.get(id);
    }

    @Override
    public E remove(I id) {
        return storage.remove(id);
    }

    @Override
    public Collection<E> getAll() {
        return storage.values();
    }

    abstract I newId();
}

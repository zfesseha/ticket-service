package com.zfesseha.ticketservice.dao;

import com.zfesseha.ticketservice.models.Entity;

import java.util.Collection;

public interface EntityDAO<I, E extends Entity<I>> {

    E save(E entity);

    E get(I id);

    E remove(I id);

    Collection<E> getAll();
}

package com.zfesseha.ticketservice.dao;

import com.zfesseha.ticketservice.models.Entity;

public interface EntityDAO<I, E extends Entity<I>> {

    E save(E entity);

    E get(I id);
}

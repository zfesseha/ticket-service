package com.zfesseha.ticketservice.models;

public interface Entity<I> {

    I getId();

    /**
     * Only here to simulate DAO functionality. Should only be called by DAOs.
     * @param id
     */
    Entity<I> withId(I id);
}

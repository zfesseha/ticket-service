package com.zfesseha.ticketservice.dao;

import com.zfesseha.ticketservice.models.Entity;

import java.util.UUID;

public class StringBasedInMemoryDAO<E extends Entity<String>> extends InMemoryDAO<String, E> {

    @Override
    String newId() {
        String id;
        do {
            id = UUID.randomUUID().toString();
        } while (storage.containsKey(id));
        return id;
    }
}

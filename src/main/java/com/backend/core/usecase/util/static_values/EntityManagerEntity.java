package com.backend.core.usecase.util.staticValues;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerEntity {
    public final static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
    public final static EntityManager entityManager = entityManagerFactory.createEntityManager();
}

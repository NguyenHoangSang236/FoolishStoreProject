package com.backend.core.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.backend.core.entity.tableentity.Account;

@Repository
public class AccountInsertRepository {
    @PersistenceContext
    private EntityManager entityManager;
    
    @Transactional
    public void createNewAccount(Account account) {
        this.entityManager.persist(account);
    }
}

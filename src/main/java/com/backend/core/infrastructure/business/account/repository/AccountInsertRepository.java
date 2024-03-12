package com.backend.core.infrastructure.business.account.repository;

import com.backend.core.entity.account.model.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AccountInsertRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void createNewAccount(Account account) {
        this.entityManager.persist(account);
    }
}

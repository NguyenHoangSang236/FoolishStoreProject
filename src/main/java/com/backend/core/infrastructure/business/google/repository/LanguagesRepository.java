package com.backend.core.infrastructure.business.google.repository;

import com.backend.core.entity.language.model.Languages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguagesRepository extends JpaRepository<Languages, Integer> {
    @Query(value = "select * from languages", nativeQuery = true)
    List<Languages> getAccountByUserNameAndPassword();
}

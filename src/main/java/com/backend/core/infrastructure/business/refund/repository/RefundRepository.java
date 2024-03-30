package com.backend.core.infrastructure.business.refund.repository;

import com.backend.core.entity.refund.model.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Integer> {

}

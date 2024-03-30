package com.backend.core.infrastructure.business.online_payment.repository;

import com.backend.core.entity.invoice.model.OnlinePaymentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OnlinePaymentAccountRepository extends JpaRepository<OnlinePaymentAccount, Integer> {
    @Query(value = "select * from online_payment_accounts where type = :typeVal", nativeQuery = true)
    OnlinePaymentAccount getOnlinePaymentAccountByType(@Param("typeVal") String type);
}

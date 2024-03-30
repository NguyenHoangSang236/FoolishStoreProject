package com.backend.core.infrastructure.business.account.repository;

import com.backend.core.entity.account.model.Account;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
    @Query("select a from Account a where a.userName = :userNameVal and a.password = :passwordVal")
    Account getAccountByUserNameAndPassword(@Param("userNameVal") String userName, @Param("passwordVal") String password);

    @Query("select a from Account a where a.userName = :userNameVal")
    Account getAccountByUserName(@Param("userNameVal") String userName);

    @Query(value = "select la.* from login_accounts la join customers c on la.id = c.account_id where c.id = :cusId", nativeQuery = true)
    Account getCustomerAccountByCustomerId(@Param("cusId") int customerId);

    @Query(value = "select la.* from login_accounts la join staffs st on la.id = st.account_id where st.id = :stfId", nativeQuery = true)
    Account getStaffAccountByStaffId(@Param("stfId") int staffId);

    @Query("select a from Account a where a.id = :idVal")
    Account getAccountByID(@Param("idVal") int id);

    @Transactional
    @Modifying
    @Query(value = "update login_accounts set status = :status where id = :id", nativeQuery = true)
    void updateAccountStatus(@Param(value = "status") String status, @Param(value = "id") int id);
}

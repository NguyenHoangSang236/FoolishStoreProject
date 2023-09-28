package com.backend.core.repository.account;

import com.backend.core.entities.tableentity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query("select a from Account a where a.userName = :userNameVal and a.password = :passwordVal")
    Account getAccountByUserNameAndPassword(@Param("userNameVal") String userName, @Param("passwordVal") String password);

    @Query("select a from Account a where a.userName = :userNameVal")
    Account getAccountByUserName(@Param("userNameVal") String userName);

    @Query(value = "select la.* from login_accounts la join customers c on la.id = c.account_id where c.id = :cusId", nativeQuery = true)
    Account getCustomerAccountByCustomerId(@Param("cusId") int customerId);

    @Query(value = "select la.* from login_accounts la join staffs st on la.id = st.account_id where st.id = :stfId", nativeQuery = true)
    Account getStaffAccountByStaffId(@Param("stfId") int staffId);

    //	@Query(value = "select * from login_accounts ", nativeQuery = true)
//	List<Account> getAllAccounts();
//
//	@Query(value = "select la.* from login_accounts la join customers c on la.id = c.account_id where role = 'user'", nativeQuery = true)
//	List<Account> getAllCustomerAccounts();
//
    @Query("select a from Account a where a.id = :idVal")
    Account getAccountByID(@Param("idVal") int id);
//
//	@Query(value = "select id from login_accounts order by id desc limit 1", nativeQuery = true)
//	int getLastestAccountId();
}

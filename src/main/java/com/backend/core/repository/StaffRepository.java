package com.backend.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.core.entity.Customer;
import com.backend.core.entity.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer>{
	@Query(value = "select * from staffs where id = :idVal", nativeQuery = true)
    Staff getStaffById(@Param("idVal") int id);
    
    
    @Query(value = "select * from staffs", nativeQuery = true)
    List<Staff> getAllStaffs();
    
    
    @Query(value = "select stf.* from staffs stf join login_accounts la on la.id = stf.account_id where la.id = :idVal", nativeQuery = true)
    Staff getStaffByAccountId(@Param("idVal") int id);
}

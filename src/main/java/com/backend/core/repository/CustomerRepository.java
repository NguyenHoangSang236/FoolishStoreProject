package com.backend.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.core.entity.tableentity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{    
    @Query(value = "select * from customers where id = :idVal", nativeQuery = true)
    Customer getCustomerById(@Param("idVal") int id);
    
    
//    @Query(value = "select * from customers", nativeQuery = true)
//    List<Customer> getAllCustomers();
//
//
//    @Query(value = "select c.* from customers c join login_accounts la on la.id = c.account_id where la.id = :idVal", nativeQuery = true)
//    Customer getCustomerByAccountId(@Param("idVal") int id);


    @Query(value = "select * from customers where email = :email", nativeQuery = true)
    Customer getCustomerByEmail(@Param("email") String email);
}

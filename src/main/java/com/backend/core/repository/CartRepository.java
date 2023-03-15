package com.backend.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.core.entity.tableentity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{
    @Query(value = "select * from cart where+ customer_id = :idVal and buying_status = 0", nativeQuery = true)
    List<Cart> getCurrentCartByCustomerId(@Param("idVal") int id);
    
    
    @Query(value = "select * from cart where id = :idVal", nativeQuery = true)
    Cart getCartById(@Param("idVal") int id);

    
    @Query(value = "select c.quantity from cart c join products p on p.id = c.product_id where customer_id = :idVal and buying_status = 0 order by p.name", nativeQuery = true)
    int[] getFullCartQuantityListByCustomerId(@Param("idVal") int id);
    
    
    @Query(value = "select size from products p join cart c on p.id = c.product_id "
    			 + "							join customers cus on cus.id = c.customer_id"
    			 + "where c.customer_id = :idVal and buying_status = 0 "
    			 + "order by p.name", nativeQuery = true)
    String[] getFullCartSizeListByCustomerId(@Param("idVal") int id);
    
    
    @Query(value = "select select_status from cart c join products p on p.id = c.product_id where customer_id = :idVal and buying_status = 0 order by p.name", nativeQuery = true)
    int[] getFullCartSelectStatusListByCustomerId(@Param("idVal") int id);
    
    
    @Query(value = "select id from cart order by id desc limit 1", nativeQuery = true)
    int getLastestCartId();
    
    
    @Query(value = "select * from cart where product_management_id = :proMngIdVal and customer_id = :cusIdVal and buying_status = 0", nativeQuery = true)
    Cart getCartItemByProductManagementIdAndCustomerId(@Param("proMngIdVal") int proMngId, @Param("cusIdVal") int cusId);
    
    @Query(value = "SELECT count(*) FROM cart where customer_id = :id and buying_status = 0", nativeQuery = true)
    int getCartQuantityByCustomerId(@Param("id") int cusId);

    @Query(value = "select count(c.id) from cart c where customer_id = :customerIdVal and product_management_id = :productMngIdVal and buying_status = 0;", nativeQuery = true)
    int getExistedCartItemCountByCustomerIdAndProductManagementId(@Param("customerIdVal") int cusId, @Param("productMngIdVal") int productId);
}

package com.backend.core.infrastructure.business.cart.repository;

import com.backend.core.entity.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query(value = "select * from cart where id = :idVal", nativeQuery = true)
    Cart getCartById(@Param("idVal") int id);

    @Query(value = "select * from cart where product_management_id = :proMngIdVal and customer_id = :cusIdVal and buying_status = 'NOT_BOUGHT_YET'", nativeQuery = true)
    Cart getCartItemByProductManagementIdAndCustomerId(@Param("proMngIdVal") int proMngId, @Param("cusIdVal") int cusId);

    @Query(value = "select * from cart where product_management_id = :proMngIdVal and customer_id = :cusIdVal and buying_status = 'PENDING' and invoice_id = :invoiceIdVal", nativeQuery = true)
    Cart getPendingCartItemByProductManagementIdAndCustomerIdAndInvoiceId(@Param("proMngIdVal") int proMngId, @Param("cusIdVal") int cusId, @Param("invoiceIdVal") int invoiceId);

    @Query(value = "SELECT count(*) FROM cart where customer_id = :id and buying_status = 'NOT_BOUGHT_YET'", nativeQuery = true)
    int getCartQuantityByCustomerId(@Param("id") int cusId);

    @Query(value = "select count(c.id) from cart c where customer_id = :customerIdVal and product_management_id = :productMngIdVal and buying_status = 'NOT_BOUGHT_YET'", nativeQuery = true)
    int getExistedCartItemCountByCustomerIdAndProductManagementId(@Param("customerIdVal") int cusId, @Param("productMngIdVal") int productId);
}

package com.backend.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.core.entity.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
	@Query(value = "select * from invoice where customer_id = :idVal", nativeQuery = true)
	List<Invoice> getPaymentHistoryByCustomerId(@Param("idVal") int id);
	
	
	@Query(value = "select id from invoice order by id desc limit 1", nativeQuery = true)
	int getLastestInvoiceId();
	
	
	@Query(value = "select * from invoice", nativeQuery = true)
	List<Invoice> getAllInvoices();
	
	
	@Query(value = "select * from invoice where id = :idVal", nativeQuery = true)
	Invoice getInvoiceById(@Param("idVal") int id);
	
	
	@Query(value = "select * from invoice where Payment_Method != 'cod'", nativeQuery = true)
	List<Invoice> getOnlinePaymentInvoices();
	
	
	@Query(value = "select * from invoice where Payment_Method = 'cod' and admin_acceptance = 'waiting' and delivery_status <> 'not shipped'", nativeQuery = true)
	List<Invoice> getWaitingCodInvoices();
	
	
	@Query(value = "select * from invoice where delivery_status = 'packing'", nativeQuery = true)
	List<Invoice> getPackingCodInvoices();
	
	
	@Query(value = "select * from invoice i join delivery d on i.id = d.invoice_id where shipper_id = :idVal and delivery_status = 'shipping'", nativeQuery = true)
	List<Invoice> getShipperInvoicesList(@Param("idVal") int id);
	
	
	@Query(value = "select * from invoice i join delivery d on i.id = d.invoice_id where current_status = 'shipping'", nativeQuery = true)
	List<Invoice> getShippingInvoicesList();
	
	
	@Query(value = "select * from invoice i join delivery d on i.id = d.invoice_id where current_status = 'failed'", nativeQuery = true)
	List<Invoice> getFailedInvoicesList();
	
	
	@Query(value = "select * from invoice i join delivery d on i.id = d.invoice_id where current_status = 'success'", nativeQuery = true)
	List<Invoice> getSuccessfulInvoicesList();
	
	
	@Query(value = "select * from invoice where delivery_status = 'packing'", nativeQuery = true)
	List<Invoice> getPackingInvoicesList();
	
	
	@Query(value = "select * from invoice where delivery_status = 'not shipped'", nativeQuery = true)
	List<Invoice> getCustomerCanceledInvoicesList();
}

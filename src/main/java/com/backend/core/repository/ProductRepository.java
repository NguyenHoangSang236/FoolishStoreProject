package com.backend.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.core.entity.tableentity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> { 	
	@Query(value = "select id from products order by id desc limit 1", nativeQuery = true)
    int getLastestProductId();
	
	
	@Query(value = "select * from products", nativeQuery = true)
	List<Product> getAllProducts();
	
	
	@Query(value = "select p from Product p where p.name like %:nameVal% group by name")
	List<Product> getProductsByName(@Param("nameVal") String productName);
	
	
	@Query(value = "select size from products where name = :nameVal and color = :colorVal group by size;", nativeQuery = true)
	List<String> getAllSizesOfProductByNameAndColor(@Param("nameVal") String productName, @Param("colorVal") String color);
	
	@Query(value = "select color from products where name = :nameVal group by color;", nativeQuery = true)
    List<String> getAllColorsOfProductByName(@Param("nameVal") String productName);
	
	
	@Query(value = "select * from products where name = :nameVal and color = :colorVal group by name", nativeQuery = true)
	Product getProductByNameAndColor(@Param("nameVal") String productName, @Param("colorVal") String color);
	
	
	@Query(value = "select * from products where name = :nameVal and color = :colorVal", nativeQuery = true)
	List<Product> getProductListByNameAndColor(@Param("nameVal") String productName, @Param("colorVal") String color);
	
	
	@Query(value = "select * from products where name = :nameVal group by name", nativeQuery = true)
    Product getDefaultProductDetailsByName(@Param("nameVal") String productName);
	

	@Query(value = "select * from products where id = :idVal", nativeQuery = true)
    Product getProductById(@Param("idVal") int id);
	
	
	@Query(value = "select * from products where name = :nameVal and color = :colorVal and size = :sizeVal", nativeQuery = true)
	Product getProductDetailsByNameAndColorAndSize(@Param("nameVal") String productName, @Param("colorVal") String color, @Param("sizeVal") String size);
	
    
    @Query(value = "select c.name from products p join catalog_with_products cwp on p.name = cwp.product_name "
            + "join catalog c on c.id = cwp.catalog_id "
            + "where p.name = :nameVal "
            + "group by c.name", nativeQuery = true)
    List<String> getAllCatalogsByProductName(@Param("nameVal") String productName);
	
	
	@Query(value = "select * from products where brand = :brandVal group by name", nativeQuery = true)
	List<Product> getProductsByBrand(@Param("brandVal") String brandName);
	
	
	@Query(value = "select p.* "
    	         + "from products p join catalog_with_products cwp on p.name = cwp.product_name "
    	         + "                join catalog c on c.id = cwp.catalog_id "
    	         + "where c.name = :catalogName "
    	         + "group by p.name", nativeQuery = true)
	List<Product> getProductsUsingCatalogName(@Param("catalogName") String catalogName);
	
	
	@Query(value = "select * from products where price >= :moneyVal1 and price <= :moneyVal2 group by color", nativeQuery = true)
	List<Product> getProductsUsingPriceFilter(@Param("moneyVal1") double price1, @Param("moneyVal2") double price2);
	
	
	@Query(value = "select brand from products group by brand", nativeQuery = true)
	List<String> getAllProductBrands();
	
	
	@Query(value = "select sum(sold_quantity) from products where name = :nameVal", nativeQuery = true)
	int getTotalSoldQuantityByProductName(@Param("nameVal") String productName);
	
	
	@Query(value = "select available_quantity from products where id = :idVal", nativeQuery = true)
	int getAvailableQuantityById(@Param("idVal") int id);
	
	
	@Query(value = "select available_quantity from products p join cart c on c.product_id = p.id where c.id = :idVal", nativeQuery = true)
	int getAvailableQuantityByCartId(@Param("idVal") int id);
	
	
	@Query(value = "select * from products p join invoices_with_products iwp on p.id = iwp.Product_ID "
										  + "join invoice i on iwp.Invoice_ID = i.id "
				 + "where i.id = :idVal", nativeQuery = true)
	List<Product> getProductsListByInvoiceId(@Param("idVal") int invoiceId);

	
	@Modifying
	@Query(value = "update catalog_with_products set catalog_id = :resultId where catalog_id = :idVal and productName = :nameVal", nativeQuery = true)
	void updateCatalogWithProductsByProductNameAndCatalogId(@Param("resultId") int resultId, @Param("idVal") int catalogId, @Param("nameVal") String productName);
	
//	@Modifying
//	@Query(value = "update catalog_with_products set productName = :resultId where catalog_id = :idVal and productName = :nameVal", nativeQuery = true)
//	void updateCatalogWithProductsByProductNameAndCatalogId(@Param("resultId") int resultId, @Param("idVal") int catalogId, @Param("nameVal") String productName);
}

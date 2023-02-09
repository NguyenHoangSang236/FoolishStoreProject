package com.backend.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.core.entity.Catalog;


@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Integer>{
    @Query(value = "select count(newTable.id) from (select p.* "
                 + "from products p join catalog_with_products cwp on p.name = cwp.product_name join catalog c on c.id = cwp.catalog_id "
                 + "where c.name = :catalogNameVal "
                 + "group by p.name) as newTable", nativeQuery = true)
    int getTotalProductsNumberByCatalogName(@Param("catalogNameVal") String catalogName);
    
    
    @Query(value = "select name from catalog", nativeQuery = true)
    List<String> getAllCatalogsName();
    
    
    @Query(value = "select * from catalog", nativeQuery = true)
    List<Catalog> getAllCatalogs();
    
    
    @Query(value = "select id from catalog where name = :catalogNameVal", nativeQuery = true)
    int getCatalogIdByName(@Param("catalogNameVal") String catalogName);
    
    
    @Query(value = "select * from catalog where name = :catalogNameVal", nativeQuery = true)
    Catalog getCatalogByName(@Param("catalogNameVal") String catalogName);
    
    
    @Query(value = "select * from catalog c join catalog_with_products cwp on c.id = cwp.catalog_id join products p on p.name = cwp.product_name where p.name = :nameVal group by c.name", nativeQuery = true)
    List<Catalog> getCatalogsByProductName(@Param("nameVal") String productName);
}
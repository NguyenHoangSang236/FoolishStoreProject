package com.backend.core.infrastructure.business.category.repository;

import com.backend.core.entity.category.model.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Integer> {
//    @Query(value = "select count(newTable.id) from (select p.* "
//                 + "from products p join catalog_with_products cwp on p.name = cwp.product_name join catalog c on c.id = cwp.catalog_id "
//                 + "where c.name = :catalogNameVal "
//                 + "group by p.name) as newTable", nativeQuery = true)
//    int getTotalProductsNumberByCatalogName(@Param("catalogNameVal") String catalogName);
//
//
//    @Query(value = "select name from catalog", nativeQuery = true)
//    List<String> getAllCatalogsName();


    @Query(value = "select * from catalog", nativeQuery = true)
    List<Catalog> getAllCatalogs();


    @Query(value = "select * from catalog where id = :idVal", nativeQuery = true)
    Catalog getCatalogById(@Param("idVal") int id);


    @Query(value = "select * from catalog where name = :catalogNameVal", nativeQuery = true)
    Catalog getCatalogByName(@Param("catalogNameVal") String catalogName);


    @Query(value = "select name from catalog", nativeQuery = true)
    Set<String> getAllCatalogName();


    @Query(value = "select c.* from catalog c join catalogs_with_products cwp on c.id = cwp.catalog_id where cwp.product_id = :idVal", nativeQuery = true)
    List<Catalog> getCatalogsByProductId(@Param("idVal") int productId);
}
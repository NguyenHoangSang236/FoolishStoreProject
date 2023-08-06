package com.backend.core.repository.catalog;

import com.backend.core.entities.tableentity.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRemoveRepository extends JpaRepository<Catalog, Integer> {
//	 @Modifying
//	 @Transactional
//	 @Query(value = "delete from catalog where name = :nameVal", nativeQuery = true)
//	 void deleteCatalogByName(@Param("nameVal") String name);
//
//
//	 @Modifying
//	 @Transactional
//	 @Query(value = "delete from catalog_with_products where catalog_id = :idVal", nativeQuery = true)
//	 void deleteCatalogWithProductsById(@Param("idVal") int id);
}

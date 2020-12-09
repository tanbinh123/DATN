package com.movies_unlimited.repository;

import com.movies_unlimited.entity.ProductEntity;
import com.movies_unlimited.entity.enums.ActiveStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, Integer> {

    ProductEntity getById(int id);

    ProductEntity findByName(String name);

    @Query(value = "select p from ProductEntity p where p.status = :status")
    List<ProductEntity> findAllActiveProduct(@Param("status") ActiveStatus status);

    @Query(value = "select p from ProductEntity p where p.status = :status")
    Page<ProductEntity> findAllActiveProductPageable(@Param("status") ActiveStatus status, Pageable pageable);

    @Query(value = "select p from ProductEntity p " +
            "JOIN p.categories c " +
            "where c.id = :categoryID and p.status = :status")
    Page<ProductEntity> findProductByCategoryID(@Param("categoryID") int categoryID, @Param("status") ActiveStatus status, Pageable pageable);

    @Query(value = "select p from ProductEntity p where (p.name like %:searchText% or p.description like %:searchText%) and status = :status")
    Page<ProductEntity> findProductByAnyActive(@Param("searchText") String searchText, @Param("status") ActiveStatus status, Pageable pageable);

    @Query(value = "select p from ProductEntity p where p.name like %:searchText% or p.description like %:searchText%")
    Page<ProductEntity> findProductByAny(@Param("searchText") String searchText, Pageable pageable);

}

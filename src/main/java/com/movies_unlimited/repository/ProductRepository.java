package com.movies_unlimited.repository;

import com.movies_unlimited.entity.CategoryEntity;
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

    ProductEntity findById(int id);

    ProductEntity findByName(String name);

    @Query(value = "select p from ProductEntity p where p.status = :status")
    List<ProductEntity> findAllActiveProduct(@Param("status") ActiveStatus status);

    @Query(value = "select p from ProductEntity p where p.status = :status")
    Page<ProductEntity> findAllActiveProductPageable(@Param("status") ActiveStatus status, Pageable pageable);

    @Query(value = "select p from ProductEntity p " +
            "JOIN p.categories c " +
            "where c.id = :categoryID and p.status = :status")
    Page<ProductEntity> findProductByCategoryID(@Param("categoryID") int categoryID, @Param("status") ActiveStatus status, Pageable pageable);


}

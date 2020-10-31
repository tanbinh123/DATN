package com.movies_unlimited.repository;

import com.movies_unlimited.entity.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Integer> {

    List<FavoriteEntity> findByProduct_id(int product_id);

    @Query(value = "select count(*) from favorite where product_id = ?1", nativeQuery = true)
    int countFavoriteByProductId(int productId);

    FavoriteEntity findByAccount_idAndProduct_id(int account_id, int product_id);
}

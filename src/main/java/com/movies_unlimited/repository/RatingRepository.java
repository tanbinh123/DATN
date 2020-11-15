package com.movies_unlimited.repository;

import com.movies_unlimited.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RatingRepository extends JpaRepository<RatingEntity, Integer> {

    RatingEntity findByAccount_idAndProduct_id(int account_id, int product_id);
    Set<RatingEntity> findByAccount_id(int account_id);
}

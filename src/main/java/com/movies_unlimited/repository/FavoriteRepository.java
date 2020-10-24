package com.movies_unlimited.repository;

import com.movies_unlimited.entity.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Integer> {

    List<FavoriteEntity> findByProduct_id(int product_id);
}

package com.movies_unlimited.repository;

import com.movies_unlimited.entity.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Integer> {

}

package com.movies_unlimited.repository;

import com.movies_unlimited.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<RatingEntity, Integer> {

}

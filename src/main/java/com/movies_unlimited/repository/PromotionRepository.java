package com.movies_unlimited.repository;

import com.movies_unlimited.entity.PromotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<PromotionEntity, Integer> {

}

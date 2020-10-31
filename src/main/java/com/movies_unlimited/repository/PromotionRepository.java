package com.movies_unlimited.repository;

import com.movies_unlimited.entity.PromotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PromotionRepository extends JpaRepository<PromotionEntity, Integer> {
    PromotionEntity findById(int id);

    PromotionEntity findByNameLike(String name);

    @Query(value = "select promotion.id,promotion.name,promotion.description,promotion.discount,promotion.start_date,promotion.end_date,promotion.status from promotion join product_promotion_relation on promotion.id = product_promotion_relation.promotion_id where promotion.status = 'ACTIVE' and product_promotion_relation.product_id = ?1", nativeQuery = true )
    List<PromotionEntity> findPromotionByProductId(int id);
}

package com.movies_unlimited.repository;

import com.movies_unlimited.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    CategoryEntity findById(int id);
    CategoryEntity findByName(String name);
    @Query(value = "select category.id,category.name,category.description from category join category_product_relation on category.id = category_product_relation.category_id where category_product_relation.product_id = ?1 order by category.id asc", nativeQuery = true)
    List<CategoryEntity> findSizeByProductId(Integer id);
}

package com.movies_unlimited.repository;

import com.movies_unlimited.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    CategoryEntity findById(int id);
    CategoryEntity findByName(String name);
}

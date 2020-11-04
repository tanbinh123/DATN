package com.movies_unlimited.service;

import com.movies_unlimited.entity.CategoryEntity;
import com.movies_unlimited.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryEntity getCategoryById(int id) {
        return categoryRepository.findById(id);
    }

    public List<CategoryEntity> getCategorys() {
        return categoryRepository.findAll();
    }

    public List<CategoryEntity> getCategoryByProductId(Integer id) {
        return categoryRepository.findSizeByProductId(id);
    }
}

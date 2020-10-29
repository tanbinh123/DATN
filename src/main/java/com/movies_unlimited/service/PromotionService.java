package com.movies_unlimited.service;

import com.movies_unlimited.entity.PromotionEntity;
import com.movies_unlimited.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;

    public Page<PromotionEntity> getPromotions(Integer page) {
        Pageable pageable = PageRequest.of(page-1, 9, Sort.Direction.DESC, "id");
        return promotionRepository.findAll(pageable);
    }

    public PromotionEntity getPromotionById(int id){
        return promotionRepository.findById(id);
    }

    public PromotionEntity getPromotionByName(String name){
        return promotionRepository.findByNameLike(name);
    }

    public PromotionEntity save(PromotionEntity promo) {
        return promotionRepository.save(promo);
    }
}

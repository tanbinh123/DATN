package com.movies_unlimited.service;

import com.movies_unlimited.entity.FavoriteEntity;
import com.movies_unlimited.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    public List<FavoriteEntity> getFavoritesByProductId(int id){
        return favoriteRepository.findByProduct_id(id);
    }

}

package com.movies_unlimited.repository;

import com.movies_unlimited.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.PagingAndSortingRepository;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<OrderEntity, Integer> {
    Page<OrderEntity> findByAccount_id (int id, Pageable pageable);
    OrderEntity findById(int id);

}

package com.movies_unlimited.service;

import com.movies_unlimited.entity.OrderEntity;
import com.movies_unlimited.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    public Page<OrderEntity> getOrdersByAccountId(int id, int page){
        Pageable pageable = PageRequest.of(page-1, 9);
        return orderRepository.findByAccount_id(id,pageable);
    }

}

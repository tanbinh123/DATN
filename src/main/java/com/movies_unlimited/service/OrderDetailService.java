package com.movies_unlimited.service;

import com.movies_unlimited.entity.OrderDetailEntity;
import com.movies_unlimited.repository.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderDetailService {

    private final OrderDetailRepository orderRepository;

    public OrderDetailEntity save(OrderDetailEntity orderDetail) {
        return orderRepository.save(orderDetail);
    }
}

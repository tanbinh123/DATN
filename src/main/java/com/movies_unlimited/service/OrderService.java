package com.movies_unlimited.service;

import com.movies_unlimited.entity.OrderEntity;
import com.movies_unlimited.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    public Page<OrderEntity> getOrdersByAccountId(int id, int page){
        Pageable pageable = PageRequest.of(page-1, 9);
        return orderRepository.findByAccount_id(id,pageable);
    }

    public List<OrderEntity> getOrdersByAccountId(int id){
        return orderRepository.findByAccount_id(id);
    }

    public Page<OrderEntity> getOrders(int page){
        Pageable pageable = PageRequest.of(page-1, 9, Sort.Direction.DESC, "orderDate");
        return orderRepository.findAll(pageable);
    }

    public List<OrderEntity> getOrders(){
        return (List<OrderEntity>) orderRepository.findAll();
    }

    public OrderEntity getOrderById(int id){
        return orderRepository.findById(id);
    }

    public OrderEntity saveOrder(OrderEntity order) {
        return orderRepository.save(order);
    }

    public OrderEntity getOrderByIdAndEmail(int orderId, String email) {
        return orderRepository.findOrderByIdAndEmail(orderId, email);
    }

    public Page<OrderEntity> getOrdersByAny(String searchtext, int page){
        Pageable pageable = PageRequest.of(page-1, 9);
        Date birthday = null;
        try{
            birthday =new SimpleDateFormat("yyyy-MM-dd").parse(searchtext);
        }catch(ParseException e){

        }
        if(birthday!=null){
            return orderRepository.findOrderByDate(birthday,pageable);
        }
        return orderRepository.findOrderByAny(searchtext,pageable);
    }
}

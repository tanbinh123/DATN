package com.movies_unlimited.repository;

import com.movies_unlimited.entity.AccountEntity;
import com.movies_unlimited.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<OrderEntity, Integer> {
    Page<OrderEntity> findByAccount_id (int id, Pageable pageable);
    List<OrderEntity> findByAccount_id (int id);
    OrderEntity findById(int id);

    @Query(value = "select o from OrderEntity o where o.id = ?1 and o.shipping.email = ?2")
    OrderEntity findOrderByIdAndEmail(int id, String email);

    @Query(value = "select o from OrderEntity o where o.orderDate = ?1")
    Page<OrderEntity> findOrderByDate(Date orderDate, Pageable pageable);

    @Query(value = "select o from OrderEntity o where o.account.fullName like %:searchText% or o.account.email like %:searchText% or o.account.phone like %:searchText%")
    Page<OrderEntity> findOrderByAny(@Param("searchText") String searchText, Pageable pageable);

}

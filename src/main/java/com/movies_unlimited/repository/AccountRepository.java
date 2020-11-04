package com.movies_unlimited.repository;

import com.movies_unlimited.entity.AccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface AccountRepository extends PagingAndSortingRepository<AccountEntity, Integer> {
    AccountEntity findById(int id);

    Optional<AccountEntity> findByEmail(String email);

    AccountEntity findAccountByEmail(String email);

    @Query(value = "select account from account join orders on account.id = orders.account_id where orders.id = ?1", nativeQuery = true)
    AccountEntity findAccountByOrderId(int orderId);

    @Query(value = "select a from AccountEntity a where a.fullName like %:searchText% or a.email = :searchText or a.phone = :searchText or a.address like %:searchText%")
    Page<AccountEntity> findAccountByAny(@Param("searchText") String searchText, Pageable pageable);

}

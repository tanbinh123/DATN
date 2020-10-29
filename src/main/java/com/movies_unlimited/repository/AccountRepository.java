package com.movies_unlimited.repository;

import com.movies_unlimited.entity.AccountEntity;
import com.movies_unlimited.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
    AccountEntity findById(int id);
    Optional<AccountEntity> findByEmail(String email);
    AccountEntity findAccountByEmail(String email);
    @Query(value = "select account from account join orders on account.id = orders.account_id where orders.id = ?1", nativeQuery = true)
    AccountEntity findAccountByOrderId(int orderId);
}

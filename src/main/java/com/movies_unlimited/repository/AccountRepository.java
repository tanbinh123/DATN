package com.movies_unlimited.repository;

import com.movies_unlimited.entity.AccountEntity;
import com.movies_unlimited.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
    AccountEntity findById(int id);
    Optional<AccountEntity> findByEmail(String email);
}

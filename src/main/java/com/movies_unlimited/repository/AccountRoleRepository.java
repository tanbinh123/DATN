package com.movies_unlimited.repository;

import com.movies_unlimited.entity.AccountRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRoleRepository extends JpaRepository<AccountRoleEntity, Integer> {
    AccountRoleEntity findByName(String name);
    AccountRoleEntity findById(int id);
}

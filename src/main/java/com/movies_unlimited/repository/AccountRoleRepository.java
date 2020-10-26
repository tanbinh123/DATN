package com.movies_unlimited.repository;

import com.movies_unlimited.entity.AccountRoleEntity;
import com.movies_unlimited.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRoleRepository extends JpaRepository<AccountRoleEntity, Integer> {
    AccountRoleEntity findByName(String name);
}

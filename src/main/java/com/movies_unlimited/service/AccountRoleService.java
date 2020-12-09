package com.movies_unlimited.service;

import com.movies_unlimited.entity.AccountRoleEntity;
import com.movies_unlimited.entity.enums.Role;
import com.movies_unlimited.repository.AccountRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountRoleService {
    private final AccountRoleRepository accountRoleRepository;

    public List<AccountRoleEntity> getAccountRoles() {
        return accountRoleRepository.findAll();
    }

    public AccountRoleEntity getAccountRolesByRole(Role role) {
        return accountRoleRepository.findByName(role.toString());
    }

    public AccountRoleEntity getAccountRolesById(int id) {
        return accountRoleRepository.findById(id);
    }

}

package com.movies_unlimited.service;

import com.movies_unlimited.entity.AccountEntity;
import com.movies_unlimited.entity.ProductEntity;
import com.movies_unlimited.entity.enums.ActiveStatus;
import com.movies_unlimited.repository.AccountRepository;
import com.movies_unlimited.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;

    public void save(AccountEntity accountEntity){
        accountRepository.save(accountEntity);
    }

    public AccountEntity getAccountByEmail(String email){
        return accountRepository.findAccountByEmail(email);
    }

}

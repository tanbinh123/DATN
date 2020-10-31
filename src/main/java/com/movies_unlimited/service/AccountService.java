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

    public AccountEntity save(AccountEntity accountEntity){
        return accountRepository.save(accountEntity);
    }

    public AccountEntity getAccountByEmail(String email){
        return accountRepository.findAccountByEmail(email);
    }
    public Page<AccountEntity> getAccounts(int page){
        Pageable pageable = PageRequest.of(page-1, 9);
        return (Page<AccountEntity>) accountRepository.findAll(pageable);
    }

    public AccountEntity getAccountById(int id){
        return accountRepository.findById(id);
    }

    public AccountEntity findAccountByOrderId(int id){
        return accountRepository.findAccountByOrderId(id);
    }

    public Page<AccountEntity> searchAccounts(String searchText, int page){
        Pageable pageable = PageRequest.of(page-1, 9);
        return accountRepository.findAccountByAny(searchText, pageable);
    }
}

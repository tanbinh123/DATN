package com.movies_unlimited.config;

import com.movies_unlimited.entity.AccountEntity;
import com.movies_unlimited.entity.AccountRoleEntity;
import com.movies_unlimited.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Optional<AccountEntity> userOpt = accountRepository.findByEmail(username);

        if (userOpt.isPresent()) {

            AccountEntity emailEntity = userOpt.get();

            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

            Set<AccountRoleEntity> roles = emailEntity.getAccountRoles();

            for (AccountRoleEntity role : roles) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            }

            return new org.springframework.security.core.userdetails.User(
                    emailEntity.getEmail(), emailEntity.getPassword(), grantedAuthorities);

        }

        throw new UsernameNotFoundException("User not found");
    }
}

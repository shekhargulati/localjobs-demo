package com.localjobs.jpa.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import com.localjobs.domain.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {

    @Cacheable(value = "accounts")
    Account findAccountByUsername(String username);

}

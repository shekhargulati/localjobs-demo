package com.localjobs.jpa.repository;

import org.springframework.data.repository.CrudRepository;

import com.localjobs.domain.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {

    Account findAccountByUsername(String username);

}

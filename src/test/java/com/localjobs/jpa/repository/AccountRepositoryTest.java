package com.localjobs.jpa.repository;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.localjobs.config.ApplicationConfig;
import com.localjobs.domain.Account;
import com.localjobs.jpa.repository.AccountRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@ActiveProfiles("dev")
@Transactional
public class AccountRepositoryTest {

    @Inject
    AccountRepository accountRepository;

    @Test
    public void accountRepositoryShouldBeNotNull() {
        assertNotNull(accountRepository);
    }

    @Test
    public void shouldSaveAccount() {
        Account account = newAccount();

        accountRepository.save(account);

        assertNotNull(account.getId());
    }

    @Test
    public void testFindAccountByUsername() {
        Account account = newAccount();

        accountRepository.save(account);

        assertNotNull(accountRepository.findAccountByUsername("testuser"));

    }

    private Account newAccount() {
        return new Account("testuser", "password", "Test User", "123", "Test Address",
                Arrays.asList("skill1", "skill2"));
    }

}

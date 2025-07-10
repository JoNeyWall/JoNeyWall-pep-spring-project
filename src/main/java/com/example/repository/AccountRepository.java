package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.example.entity.Account;

/**
 * JPARepository to be accessed by AccountService.java
 */
public interface AccountRepository extends JpaRepository<Account, Long>{
    /**
     * Named query to find an account using its username.
     * @param username
     * @return account with matching username
     */
    Optional<Account> findAccountByUsername(String username);

    /**
     * Named query to find an account using username and password.
     * @param username
     * @param password
     * @return account matching both parameters
     */
    Optional<Account> findAccountByUsernameAndPassword(String username, String password);

    /**
     * Named query to find an account using accountId.
     * @param accountId
     * @return account matching the id
     */
    Optional<Account> getAccountByAccountId(Integer accountId);
}

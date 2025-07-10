package com.example.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.RegistrationException;
import com.example.exception.AccountDoesNotExistException;

/**
 * This class is responsible for calling methods on the AccountRepository to make persistant changes to the database.
 */
@Service
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    //Service methods

    /**
     * Persist and return a new account if the provided input is valid.
     * 
     * @param account to be added
     * @return fully formed account once persisted
     * @throws DuplicateUsernameException
     * @throws RegistrationException
     */
    public Account registerAccount(Account account) throws DuplicateUsernameException, RegistrationException{
        //Input validation
        if (account.getUsername().isBlank()){
            throw new RegistrationException("Username cannot be blank.");
        }
        if (account.getPassword().length() < 4){
            throw new RegistrationException("Password must be 4 or more characters long.");
        }
        if (accountRepository.findAccountByUsername(account.getUsername()).isPresent()){
            throw new DuplicateUsernameException("Username already exists.");
        }

        //Input is valid
        return accountRepository.save(new Account(account.getUsername(), account.getPassword()));
    }

    /**
     * Verify the existance of an account using both username and password.
     * 
     * @param account being verified
     * @return verified account
     * @throws AccountDoesNotExistException
     */
    public Account verifyLogin(Account account) throws AccountDoesNotExistException{
        Optional<Account> optionalAccount = accountRepository.findAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
        if (optionalAccount.isPresent()){
            return optionalAccount.get();
        }
        throw new AccountDoesNotExistException("An account matching these login details does not exist");
    }
}

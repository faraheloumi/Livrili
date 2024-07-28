package com.example.marketplace.dao;

import com.example.marketplace.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account,Long> {

    Optional<Account> findByUsername(String username);
    Account findByEmailAndPassword(String email, String password);
    Account findByUsernameAndPassword(String username, String password);

}

package com.example.marketplace.service;

import com.example.marketplace.dao.AccountRepo;
import com.example.marketplace.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepo accountDao;
    private final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
    public void save(Account account){
        account.setPassword(encoder.encode(account.getPassword()));
        account.setImage("default"+ ((int) (Math.random() * 12) + 1)+".png");
        accountDao.save(account);
    }

    public Optional<Account> getById(Long id){
        return accountDao.findById(id);
    }
    public Account getByEmail(String email,String password){
        return accountDao.findByEmailAndPassword(email,encoder.encode(password));
    }
    public Account getByUsername(String username, String password){
        Optional<Account>  found=accountDao.findByUsername(username);
        if (found.isPresent()&&encoder.matches(password,found.get().getPassword())){
            return found.get();
        }else {
            return null;
        }
    }

    public boolean verifyAccount(Long id, Long modifiedOn){
        Optional<Account> found = accountDao.findById(id);
        if(found.isEmpty()){
            return false;
        }else{
            Account account=found.get();
            return account.getModifiedOn().getTime()==modifiedOn;
        }
    }
}

package com.yingqing.service;

import com.yingqing.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account save(Account account){
        return accountRepository.save(account);
    }

    public Account findById(Long id){
        return accountRepository.findById(id).get();
    }

}

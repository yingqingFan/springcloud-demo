package com.example.demo.client;

import com.example.demo.common.SpringContextHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class AccountService {

    @Resource
    public AccountClient accountClient;


    public static AccountService getInstance(){
        return SpringContextHelper.getApplicationContext().getBean(AccountService.class);
    }

    public Account create(Account account){
        return accountClient.create(account);
    }

    public Account findById(Long id){
        return accountClient.findById(id);
    }
}

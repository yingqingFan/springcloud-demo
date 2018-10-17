package com.example.demo.client;

public class AccountFallback implements AccountClient {
    @Override
    public Account findById(Long id) {
        return null;
    }

    @Override
    public Account create(Account account) {
        return null;
    }
}

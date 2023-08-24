package org.example.service;

import org.example.dao.AccountDAO;
import org.example.model.Account;

public class AccountService {

    private AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = accountDAO;
    }

    public Account createAccount(Account account) {
        return accountDAO.createAccount(account);
    }

    public Account retrieveAccountByUsername(String username) {
        return accountDAO.retreiveAccountByUsername(username);
    }

    public Account authenticateAccount(String username, String password) {
        return null;
    }
}

package com.mycompany.passwordmanager.services;

import java.util.List;

import com.mycompany.passwordmanager.dto.AccountDto;

public interface CrudServiceInterface {

    public void insertAccount(AccountDto account, String password);

    public void updateAccount(AccountDto account, String password);

    public void deletetAccount(AccountDto account, String password);

    public List<AccountDto> getAllAccounts(String password);
}

package com.mycompany.passwordmanager.services;

import java.util.List;

import com.mycompany.passwordmanager.dto.AccountDto;

public interface CrudServiceInterface {

    public void insertAccount(AccountDto account);

    public void updateAccount(AccountDto account);

    public void deletetAccount(AccountDto account);

    public List<AccountDto> getAllAccounts();
}

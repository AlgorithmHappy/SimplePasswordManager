package com.mycompany.passwordmanager.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.mycompany.passwordmanager.data_base.HibernateUtil;
import com.mycompany.passwordmanager.dto.AccountDto;
import com.mycompany.passwordmanager.entities.Accounts;

import jakarta.persistence.Query;

public class CrudServiceImplements implements CrudServiceInterface{

    @Override
    public void insertAccount(AccountDto account) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Accounts entityAccount = account.copy().getEntityAcount();

            session.persist(entityAccount);

            transaction.commit();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAccount(AccountDto account) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Accounts entityAccount = session.get(Accounts.class, account.getId());
            if (entityAccount != null) {
                entityAccount.setAccount(account.getAccount() );
                entityAccount.setComments(account.getComments() );
                entityAccount.setEmail(account.getEmail() );
                entityAccount.setPassword(account.getPassword() );
                entityAccount.setPhone(account.getPhone() );
                entityAccount.setToken(account.getToken() );
                entityAccount.setUrl(account.getUrl() );
                entityAccount.setUser_name(account.getUserName() );
            }

            session.merge(entityAccount);

            transaction.commit();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletetAccount(AccountDto account) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletetAccount'");
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<AccountDto> lstAccountDtos = new ArrayList<>();
        try {

            Query query = session.createQuery("FROM Accounts");
            List<Accounts> lstAccounts = query.getResultList();

            if(lstAccounts != null && !lstAccounts.isEmpty() ){
                lstAccountDtos = lstAccounts.stream()
                    .map(it -> new AccountDto(it) )
                    .collect(Collectors.toList() );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return lstAccountDtos;
    }

}

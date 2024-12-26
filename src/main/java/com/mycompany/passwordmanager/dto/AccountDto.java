/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.passwordmanager.dto;

import com.mycompany.passwordmanager.entities.Accounts;
import com.mycompany.passwordmanager.vo.TableAccountVo;
import java.util.Objects;

/**
 *
 * @author Luis Gerardo Marquez
 */
public class AccountDto {
   
    private Integer id;
    
    private String account;
    
    private String userName;
    
    private String password;
    
    private String token;
    
    private String email;
    
    private Long phone;
    
    private String url;
   
    private String comments;

    public AccountDto(Integer id, String account, String userName, String password) {
        this.id = id;
        this.account = account;
        this.userName = userName;
        this.password = password;
    }

    public AccountDto(String account, String userName, String password) {
        this.account = account;
        this.userName = userName;
        this.password = password;
    }

    public AccountDto(Integer id, String account, String userName, String password, String token, String email, Long phone, String url, String comments) {
        this.id = id;
        this.account = account;
        this.userName = userName;
        this.password = password;
        this.token = token;
        this.email = email;
        this.phone = phone;
        this.url = url;
        this.comments = comments;
    }

    public AccountDto(String account, String userName, String password, String token, String email, Long phone, String url, String comments) {
        this.account = account;
        this.userName = userName;
        this.password = password;
        this.token = token;
        this.email = email;
        this.phone = phone;
        this.url = url;
        this.comments = comments;
    }
    
    public AccountDto(TableAccountVo tableAccountVo){
        this.id = tableAccountVo.getId();
        this.account = tableAccountVo.getAccount();
        this.userName = tableAccountVo.getUserName();
        this.email = tableAccountVo.getEmail();
        this.phone = tableAccountVo.getPhone();
        this.url = tableAccountVo.getUrl();
        this.comments = tableAccountVo.getComments();

    }

    public AccountDto(Accounts accounts){
        this.id = accounts.getId();
        this.account = accounts.getAccount();
        this.userName = accounts.getUser_name();
        this.password = accounts.getPassword();
        this.token = accounts.getToken();
        this.email = accounts.getEmail();
        this.phone = accounts.getPhone();
        this.url = accounts.getUrl();
        this.comments = accounts.getComments();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }   
    
    public TableAccountVo getTableAccountVo(){
        return new TableAccountVo(id, account, userName, password, token, email, phone, url, comments);
    }

    public Accounts getEntityAcount(){
        return new Accounts(id, account, userName, password, token, email, phone, url, comments);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AccountDto other = (AccountDto) obj;
        return Objects.equals(this.id, other.id);
    }

    public AccountDto copy(){
        return new AccountDto(this.id, this.account, this.userName, this.password, this.token, this.email, this.phone, this.url, this.comments);
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.passwordmanager.vo;

import java.util.Objects;

/**
 *
 * @author Luis Gerardo Marquez
 */
public class TableAccountVo {
    private Integer id;
    
    private String account;
    
    private String userName;
    
    private String password;
    
    private String token;
    
    private String email;
    
    private Long phone;
    
    private String url;
   
    private String comments;

    public TableAccountVo(Integer id, String account, String userName, String password) {
        this.id = id;
        this.account = account;
        this.userName = userName;
        this.password = password.replace(".", "*");
    }

    public TableAccountVo(Integer id, String account, String userName, String password, String token, String email, Long phone, String url, String comments) {
        this.id = id;
        this.account = account;
        this.userName = userName;
        this.password = password.replaceAll(".", "*");
        if(token != null)
            this.token = token.replaceAll(".", "*");
        if(email != null)
            this.email = email;
        if(phone != null)
            this.phone = phone;
        if(url != null)
            this.url = url;
        if(comments != null)
            this.comments = comments;
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
        this.password = password.replaceAll(".", "*");
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        if(token != null)
            this.token = token.replaceAll(".", "*");
        else
            this.token = null;
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
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final TableAccountVo other = (TableAccountVo) obj;
        return Objects.equals(this.id, other.id);
    }
    
}

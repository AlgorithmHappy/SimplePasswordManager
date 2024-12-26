/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.passwordmanager.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 *
 * @author Luis Gerardo Marquez
 */
@Entity
public class Accounts {

    @Id
    private Integer id;
    
    @Column(unique = true, nullable = false, length = 20)
    private String account;

    @Column(nullable = false, length = 20)
    private String user_name;

    @Column(nullable = false, length = 18)
    private String password;

    @Column(length = 257)
    private String token;

    @Column(length = 30)
    private String email;

    @Column(length = 500, precision = 12, scale = 0)
    private Long phone;

    @Column(length = 100)
    private String url;

    @Column(length = 500)
    private String comments;

    public Accounts(Integer id, String account, String user_name, String password, String token, String email,
            Long phone, String url, String comments) {
        this.id = id;
        this.account = account;
        this.user_name = user_name;
        this.password = password;
        this.token = token;
        this.email = email;
        this.phone = phone;
        this.url = url;
        this.comments = comments;
    }

    public Accounts(Integer id, String account, String user_name, String password) {
        this.id = id;
        this.account = account;
        this.user_name = user_name;
        this.password = password;
    }

    public Accounts() {
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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

    
}

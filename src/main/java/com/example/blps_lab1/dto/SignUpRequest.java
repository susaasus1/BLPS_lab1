package com.example.blps_lab1.dto;

import java.util.Set;

public class SignUpRequest {
    private String login;
    private String password;
    private String email;

    private Set<String> roles;


    public SignUpRequest(String login, String password, String email, Set<String> roles) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}

package com.example.mypharmacyapp;

public class User {
    private String username;
    private String password;
    private String email;
    private String role;
    private String ownerKey;

    public User(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.ownerKey = null;
    }

    public User(String username, String password, String email, String role, String ownerKey) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.ownerKey = ownerKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getOwnerKey() {
        return ownerKey;
    }

    public void setOwnerKey(String ownerKey) {
        this.ownerKey = ownerKey;
    }
}

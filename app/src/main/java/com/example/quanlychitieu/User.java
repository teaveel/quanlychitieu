package com.example.quanlychitieu;

public class User {
    private String email, username;
    private float wallet;
    public User(){}
    public User(String email, String username, float wallet)
    {
        this.email = email;
        this.username = username;
        this.wallet = wallet;
    }
    public String getEmail()
    {
        return email;
    }
    public String getUsername()
    {
        return username;
    }
    public float getWallet()
    {
        return wallet;
    }
}

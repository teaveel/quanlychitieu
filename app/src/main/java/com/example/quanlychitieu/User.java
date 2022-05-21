package com.example.quanlychitieu;

public class User {
    private String email, name;
    private float wallet;
    public User(){}
    public User(String email, String name, float wallet)
    {
        this.email = email;
        this.name = name;
        this.wallet = wallet;
    }
    public String getEmail()
    {
        return email;
    }
    public String getName()
    {
        return name;
    }
    public float getWallet()
    {
        return wallet;
    }
}

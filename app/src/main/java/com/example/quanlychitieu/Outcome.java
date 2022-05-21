package com.example.quanlychitieu;

import java.time.*;
public class Outcome
{
    private int type;
    private float amount;
    private String note;
    private String email;
    public Outcome()
    {

    }
    public Outcome(String email, int type, float amount, String note)
    {
        this.email = email;
        this.type = type;
        this.amount = amount;
        this.note = note;
    }
    public String getEmail(){ return email;}
    public int getType()
    {
        return type;
    }
    public float getAmount()
    {
        return amount;
    }
    public String getNote()
    {
        return note;
    }
}

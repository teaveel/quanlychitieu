package com.example.quanlychitieu;

import java.time.*;
import java.util.*;
public class Income
{
    private int type;
    private float amount;
    private String note;
    private String email;
    private Date date;
    public Income()
    {

    }
    public Income(String email, int type, float amount, String note, Date date)
    {
        this.email = email;
        this.type = type;
        this.amount = amount;
        this.note = note;
        this.date = date;
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
    public Date getDate()
    {
        return date;
    }
}

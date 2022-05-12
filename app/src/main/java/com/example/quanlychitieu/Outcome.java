package com.example.quanlychitieu;

import java.time.*;
public class Outcome
{
    private int id,type;
    private float amount;
    private String note;
    private String date;
    public Outcome()
    {

    }
    public Outcome(int id, int type, float amount, String note, String date)
    {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.note = note;
        this.date = date;
    }
    public int getId()
    {
        return id;
    }
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
    public String getDate()
    {
        return date;
    }
}

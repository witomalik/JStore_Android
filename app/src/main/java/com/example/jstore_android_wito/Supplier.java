package com.example.jstore_android_wito;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Supplier {
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private Location location;
    public Supplier(int id, String name, String email, String phoneNumber, Location location)
    {
        // initialise instance variables
        setId(id);
        setName(name);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setLocation(location);
    }

    public int getId()
    {
        // return dari accessor
        return id;
    }

    public String getName()
    {
        // return dari accessor
        return name;
    }

    public String getEmail()
    {
        // return dari accessor
        return email;
    }

    public String getPhoneNumber()
    {
        // return dari accessor
        return phoneNumber;
    }

    public Location getLocation()
    {
        // return dari accessor
        return location;
    }

    public void setId(int id)
    {
        // initialise instance variables
        this.id=id;
    }

    public void setName(String name)
    {
        // initialise instance variables
        this.name=name;
    }

    public void setEmail(String email)
    {
        // initialise instance variables
        this.email=email;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        // initialise instance variables
        this.phoneNumber=phoneNumber;
    }

    public void setLocation(Location location)
    {
        // initialise instance variables
        this.location=location;
    }
}

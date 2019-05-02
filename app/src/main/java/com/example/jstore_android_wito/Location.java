package com.example.jstore_android_wito;

public class Location {
    private String province;
    private String description;
    private String city;
    public Location(String city, String province, String description)
    {
        // initialise instance variables
        setCity(city);
        setProvince(province);
        setDescription(description);
    }

    public String getProvince()
    {
        // return dari accessor
        return province;
    }

    public String getCity()
    {
        // return dari accessor
        return city;
    }

    public String getDesciption()
    {
        // return dari accessor
        return description;
    }

    public void setProvince(String province)
    {
        // initialise instance variables
        this.province=province;
    }

    public void setCity(String city)
    {
        // initialise instance variables
        this.city=city;
    }

    public void setDescription(String description)
    {
        // initialise instance variables
        this.description=description;
    }
}

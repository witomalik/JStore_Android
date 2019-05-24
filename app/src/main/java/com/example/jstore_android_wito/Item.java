package com.example.jstore_android_wito;

public class Item {
    private int id;
    private String name;
    private int price;
    private String category;
    private String status;
    private Supplier supplier;
    public Item(int id, String name, int price, String category, String status, Supplier supplier)
    {
        // initialise instance variables
        setId(id);
        setName(name);
        setPrice(price);
        setCategory(category);
        setStatus(status);
        setSupplier(supplier);
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

    public int getPrice()
    {
        // return dari accessor
        return price;
    }

    public String getCategory()
    {
        // return dari accessor
        return category;
    }

    public String getStatus()
    {
        // return dari accessor
        return status;
    }

    public Supplier getSupplier()
    {
        // return dari accessor
        return supplier;
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

    public void setPrice(int price)
    {
        // initialise instance variables
        this.price=price;
    }

    public void setCategory(String category)
    {
        // initialise instance variables
        this.category=category;
    }

    public void setStatus(String status)
    {
        // initialise instance variables
        this.status=status;
    }

    public void setSupplier(Supplier supplier)
    {
        // initialise instance variables
        this.supplier=supplier;
    }
}

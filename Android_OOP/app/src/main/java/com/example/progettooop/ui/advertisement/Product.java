package com.example.progettooop.ui.advertisement;

public class Product {
    private String Name;
    private String Quantity;
    private String Expiration;


    public Product(String name, String quantity, String expiration) {
        Name = name;
        Quantity = quantity;
        Expiration = expiration;
    }

    public String getQuantity() {
        return Quantity;
    }

    public String getExpiration() {
        return Expiration;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public void setExpiration(String expiration) {
        Expiration = expiration;

    }
}

package com.example.progettooop.ui.Objects;

public class Product {
    private String Name;
    private String Quantity;
    private String Expiration;
    private String UserId;
    private String product;


    public Product(String name, String quantity, String expiration,String Uid, String productId) {
        Name = name;
        Quantity = quantity;
        Expiration = expiration;
        UserId = Uid;
        product = productId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
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

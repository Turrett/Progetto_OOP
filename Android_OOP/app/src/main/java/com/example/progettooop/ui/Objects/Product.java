package com.example.progettooop.ui.Objects;

public class Product {
    private String Name;
    private String Quantity;
    private String Expiration;
    private String UserId;
    private String productId;
    private String State;


    public Product(String name,
                   String quantity,
                   String expiration,
                   String Uid,
                   String productId,
                   String state) {
        Name = name;
        Quantity = quantity;
        Expiration = expiration;
        UserId = Uid;
        this.productId = productId;
        State=state;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

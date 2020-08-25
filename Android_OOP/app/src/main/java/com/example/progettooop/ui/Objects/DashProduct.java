package com.example.progettooop.ui.Objects;

public class DashProduct {
    //TODO transform it as an extension of wishedProduct
    private String Namedash;
    private String Quantitydash;
    private String Expirationdash;
    private String ProductId;
    private String WishedID;
    private String UserId;
    private String State;


    public DashProduct(String name, String quantity, String expiration,String Pid,String state) {
        Namedash = name;
        Quantitydash = quantity;
        Expirationdash = expiration;
        ProductId = Pid;
        State=state;
    }

    public DashProduct(String name, String quantity, String expiration,String Pid,String state,String userId,String wishedID) {
        Namedash = name;
        Quantitydash = quantity;
        Expirationdash = expiration;
        ProductId = Pid;
        State=state;

        UserId = userId;
        WishedID =wishedID;
    }

    public String getWishedID() {
        return WishedID;
    }

    public void setWishedID(String wishedID) {
        WishedID = wishedID;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }


    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getUsername() {
        return UserId;
    }

    public void setUsername(String userId) {
        UserId = userId;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getNamedash() {
        return Namedash;
    }

    public String getQuantitydash() {
        return Quantitydash;
    }

    public String getExpirationdash() {
        return Expirationdash;
    }

    public void setNamedash(String namedash) {
        Namedash = namedash;
    }

    public void setQuantitydash(String quantitydash) {
        Quantitydash = quantitydash;
    }

    public void setExpirationdash(String expirationdash) {
        Expirationdash = expirationdash;
    }
}

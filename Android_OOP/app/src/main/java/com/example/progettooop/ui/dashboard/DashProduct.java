package com.example.progettooop.ui.dashboard;

public class DashProduct {
    private String Namedash;
    private String Quantitydash;
    private String Expirationdash;

    public DashProduct(String name, String quantity, String expiration, String Uid) {
        Namedash = name;
        Quantitydash = quantity;
        Expirationdash = expiration;
    }

    public String getQuantity() {
        return Quantitydash;
    }

    public String getExpiration() {
        return Expirationdash;
    }

    public String getName() {
        return Namedash;
    }

    public void setName(String name) {
        Namedash = name;
    }

    public void setQuantity(String quantity) {
        Quantitydash = quantity;
    }

    public void setExpiration(String expiration) {
        Expirationdash = expiration;
    }

}

package com.example.progettooop.ui.dashboard;

public class DashProduct {
    private String Namedash;
    private String Quantitydash;
    private String Expirationdash;

    public DashProduct(String name, String quantity, String expiration) {
        Namedash = name;
        Quantitydash = quantity;
        Expirationdash = expiration;
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

package com.example.progettooop.ui.Objects;

public class DashProduct {
    private String Namedash;
    private String Quantitydash;
    private String Expirationdash;
    private String ProductId;

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public DashProduct(String name, String quantity, String expiration,String Pid) {
        Namedash = name;
        Quantitydash = quantity;
        Expirationdash = expiration;
        ProductId = Pid;
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

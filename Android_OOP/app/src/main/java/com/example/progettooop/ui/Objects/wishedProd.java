package com.example.progettooop.ui.Objects;

public class wishedProd extends Product {
    private String userAddingId;
    private String wishedId;


    public wishedProd(String name, String quantity, String expiration, String userPostingId, String productId,String userAddingId,String wishedId) {
        super(name, quantity, expiration, userPostingId, productId);
        this.userAddingId=userAddingId;
        this.wishedId=wishedId;
    }

    public String getUserAddingId() {
        return userAddingId;
    }

    public void setUserAddingId(String userAddingId) {
        this.userAddingId = userAddingId;
    }

    public String getWishedId() {
        return wishedId;
    }

    public void setWishedId(String wishedId) {
        this.wishedId = wishedId;
    }
}

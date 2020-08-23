package com.example.progettooop.ui.Objects;

public class Request {
    String productId,userId,message,requestId,when;

    public Request(String productId, String userId, String message,String when,String requestId) {
        this.productId = productId;
        this.userId = userId;
        this.message = message;
        this.requestId = requestId;
        this.when = when ;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

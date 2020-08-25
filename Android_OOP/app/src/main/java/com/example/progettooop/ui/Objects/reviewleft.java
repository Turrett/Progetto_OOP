package com.example.progettooop.ui.Objects;

public class reviewleft {
    private String textreview;
    private double ratingleft;
    private String state;

    public reviewleft(String textreview, Double ratingleft, String state) {
        this.textreview = textreview;
        this.ratingleft = ratingleft;
        this.state = state;
    }

    public String getTextreview() {
        return textreview;
    }

    public void setTextreview(String textreview) {
        this.textreview = textreview;
    }

    public Double getRatingleft() {
        return ratingleft;
    }

    public void setRatingleft(float ratingleft) {
        this.ratingleft = ratingleft;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

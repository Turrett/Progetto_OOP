package com.example.progettooop.ui.Objects;

public class reviewleft {
    private String textprodotto;
    private String textreview; //testo della recensione
    private float ratingleft; //numero di stelle lasciare


    public reviewleft(String textprodotto, String textreview, float ratingleft) {
        this.textreview = textreview;
        this.ratingleft = ratingleft;
        this.textprodotto =textprodotto;

    }

    public String getTextreview() {
        return textreview;
    }

    public void setTextreview(String textreview) {
        this.textreview = textreview;
    }

    public float getRatingleft() {
        return ratingleft;
    }

    public void setRatingleft(float ratingleft) {
        this.ratingleft = ratingleft;
    }

    public String getTextprodotto() {
        return textprodotto;
    }

    public void setTextprodotto(String textprodotto) {
        this.textprodotto = textprodotto;
    }
}

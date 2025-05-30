package com.example.orderingapp.Domain;

public class BannerModel {
    private String url;

    // Default constructor
    public BannerModel() {
        this.url = "";
    }

    // Constructor with url parameter
    public BannerModel(String url) {
        this.url = url;
    }

    // Getter
    public String getUrl() {
        return url;
    }

    // Setter
    public void setUrl(String url) {
        this.url = url;
    }
}

package com.javamentoring.javamentoring.apigateway.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Product {

    private String name;
    private String price;
    private String pictureUrl;

    public Product(String json) {
        Gson gson = new Gson();
        Product request = gson.fromJson(json, Product.class);
        this.name = request.getName();
        this.price = request.getPrice();
        this.pictureUrl = request.getPictureUrl();
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}

package com.example.crawler;

public abstract class Equipment {
    // equipment class abstract
    private String name;
    private int price;

    public String getName() {
        return name;
    }
    public void setName(String nameIn) {
        name = nameIn;
    }
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

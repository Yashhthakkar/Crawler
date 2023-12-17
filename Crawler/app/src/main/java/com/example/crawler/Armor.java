package com.example.crawler;

public class Armor extends Equipment {

    private int reduction;

    public Armor() {
        reduction = 2;
        super.setName("Iron Armor");
        setPrice(5);
    }
    public Armor(int reductionIn, String nameIn, int priceIn) {
        reduction = reductionIn;
        super.setName(nameIn);
        super.setPrice(priceIn);
    }

    public int getReduction() {
        return reduction;
    }

    public void setReduction(int reduction) {
        this.reduction = reduction;
    }

}

package com.example.crawler;

public class Weapon extends Equipment {
    //weapon
    private int damage;

    public Weapon() {
        damage = 5;
        super.setName("Wooden Sword");
        super.setPrice(5);
    }
    public Weapon(int newDamage, String newName, int newPrice) {
        damage = newDamage;
        super.setName(newName);
        super.setPrice(newPrice);
    }
    public int getDamage() {
        return damage;
    }
    public void setDamage(int newDamage) {
        this.damage = newDamage;
    }
}

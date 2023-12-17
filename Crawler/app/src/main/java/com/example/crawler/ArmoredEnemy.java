package com.example.crawler;

public class ArmoredEnemy extends Enemy {
    public ArmoredEnemy(Player player, String difficulty) {
        super(player, difficulty);
        setName("Armored Enemy");
        setDamage(2);
        setHP(150);
        setReward(20);
    }
}
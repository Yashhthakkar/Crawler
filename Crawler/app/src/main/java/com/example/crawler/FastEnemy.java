package com.example.crawler;

public class FastEnemy extends Enemy {
    public FastEnemy(Player player, String difficulty) {
        super(player, difficulty);
        setName("Fast Enemy");
        setDamage(2);
        setHP(50);
        setReward(5);
    }
}
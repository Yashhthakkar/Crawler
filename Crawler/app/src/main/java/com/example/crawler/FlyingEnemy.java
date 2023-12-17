package com.example.crawler;

public class FlyingEnemy extends Enemy {
    // enemy that can fly
    public FlyingEnemy(Player player, String difficulty) {
        super(player, difficulty);
        setName("Flying Enemy");
        setDamage(2);
        setHP(40);
        setReward(15);
    }
}

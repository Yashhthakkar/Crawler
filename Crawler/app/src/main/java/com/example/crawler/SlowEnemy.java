package com.example.crawler;

public class SlowEnemy extends Enemy {
    public SlowEnemy(Player player, String difficulty) {
        super(player, difficulty);
        setName("Slow Enemy");
        setDamage(2);
        setHP(100);
        setReward(10);
    }
}
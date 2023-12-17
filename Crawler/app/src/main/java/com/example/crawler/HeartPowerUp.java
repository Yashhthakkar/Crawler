package com.example.crawler;

public class HeartPowerUp extends PowerUp {
    public HeartPowerUp(Player player) {
        setName("heart");
        setHp(50);
        setUsed(false);
    }

    @Override
    public void usePowerUp(Player player) {
        if (isUsed()) {
            return;
        }
        player.setHealth(player.getHealth() + getHp());
        setUsed(true);
    }
}

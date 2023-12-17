package com.example.crawler;

public class FirePowerUp extends PowerUp {
    private int score = 50;

    public FirePowerUp(Player player) {
        setName("fire");
        setUsed(false);
    }

    @Override
    public void usePowerUp(Player player) {
        if (isUsed()) {
            return;
        }
        player.setScore(player.getScore() + score);
        setUsed(true);
    }
}

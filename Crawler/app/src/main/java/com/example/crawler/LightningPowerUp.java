package com.example.crawler;

public class LightningPowerUp extends PowerUp {

    public LightningPowerUp(Player player) {
        setName("lightning");
        setSpeed(2);
        setUsed(false);
    }

    @Override
    public void usePowerUp(Player player) {
        if (isUsed()) {
            return;
        }
        player.setSpeedFactor(player.getSpeedFactor() + getSpeed());
        setUsed(true);
    }
}

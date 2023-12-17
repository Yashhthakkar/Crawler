package com.example.crawler;

public class PowerUp extends Equipment {
    private int damage;
    private int hp;
    private int reduction;

    private int speed;
    private boolean used = false;

    public PowerUp() {
        damage = 2;
        hp = 2;
        reduction = 2;
        super.setName("PowerUp");
        super.setPrice(5);
    }
    public PowerUp(int damageIn, int hpIn, int reductionIn, String nameIn, int priceIn) {
        damage = damageIn;
        hp = hpIn;
        reduction = reductionIn;
        super.setName(nameIn);
        super.setPrice(priceIn);
    }

    public void usePowerUp(Player player) {
        player.setHealth(player.getHealth() + 10);
    }

    public int getReduction() {
        return reduction;
    }

    public void setReduction(int reduction) {
        this.reduction = reduction;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}


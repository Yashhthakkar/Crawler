package com.example.crawler;

public class Enemy implements Observer {
    private int health;
    private int damage;
    private int x;
    private int y;
    private String name;
    private int reward;
    private int speed;
    private int direction; // 1 for right, -1 for left
    private Player player;
    private String difficulty;
    private boolean isActive = true;

    private int dmgCount = 0;

    public Enemy(Player player, String difficulty) {
        this.player = player;
        this.difficulty = difficulty;
    }

    public Enemy() {
    }
    @Override
    public void update(int playerX, int playerY) {
        if (this.isActive) {
            checkCollisionWithPlayer(playerX, playerY);
        }
    }


    public void move() {
        int movement = speed;
        movement *= direction;
        x += movement;
    }

    private void checkCollisionWithPlayer(int playerX, int playerY) {
        if (Math.abs(this.x - playerX) <= 300 && Math.abs(this.y - playerY) <= 300) {
            if (dmgCount <= 0) {
                handleCollision();
            } else {
                dmgCount--;
            }
        }
    }

    public void deactivate() {
        this.isActive = false;
    }

    // Check if the enemy is active
    public boolean isActive() {
        return isActive;
    }

    private void handleCollision() {
        int damage = this.damage;

        // Adjust damage based on difficulty (you can customize this)
        if ("Medium".equals(difficulty)) {
            damage *= 1.5;
        } else if ("Hard".equals(difficulty)) {
            damage *= 2;
        }

        player.setHealth(player.getHealth() - damage);
        dmgCount = 35;
    }

    public int getHP() {
        return health;
    }
    public void setHP(int newHP) {
        health = newHP;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void resetDmgCounter() {
        dmgCount = 0;
    }
}
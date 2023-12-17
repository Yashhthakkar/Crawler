package com.example.crawler;

class MoveRightStrategy implements MovementStrategy {
    public void move(Player player) {
        int newX = player.getX() + player.getSpeedFactor();
        if (newX < 300) {
            player.setX(newX);
        }
    }
}


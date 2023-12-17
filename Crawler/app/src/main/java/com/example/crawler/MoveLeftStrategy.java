package com.example.crawler;

class MoveLeftStrategy implements MovementStrategy {
    public void move(Player player) {
        int newX = player.getX() - player.getSpeedFactor();
        if (newX > 0) {
            player.setX(newX);
        }
    }
}

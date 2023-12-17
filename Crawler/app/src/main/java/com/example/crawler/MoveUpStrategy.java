package com.example.crawler;

class MoveUpStrategy implements MovementStrategy {
    public void move(Player player) {
        int newY = player.getY() - player.getSpeedFactor();
        if (newY > 0) {
            player.setY(newY);
        }
    }
}


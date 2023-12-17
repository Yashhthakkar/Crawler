package com.example.crawler;

class MoveDownStrategy implements MovementStrategy {
    public void move(Player player) {
        int newY = player.getY() + player.getSpeedFactor(); // Use speed factor
        if (newY < 500) {
            player.setY(newY);
        }
    }
}


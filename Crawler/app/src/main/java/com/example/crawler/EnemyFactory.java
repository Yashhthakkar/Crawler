package com.example.crawler;

public class EnemyFactory {

    public Enemy createEnemy(String type, Player player, String difficulty) {
        if (player == null || difficulty == null || type == null) {
            throw new IllegalArgumentException("Player, difficulty, and type must not be null");
        }

        switch (type.toUpperCase()) {
        case "FAST":
            return new FastEnemy(player, difficulty);
        case "SLOW":
            return new SlowEnemy(player, difficulty);
        case "ARMORED":
            return new ArmoredEnemy(player, difficulty);
        case "FLYING":
            return new FlyingEnemy(player, difficulty);
        default:
            throw new IllegalArgumentException("Unknown enemy type: " + type);
        }
    }
}

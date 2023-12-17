package com.example.crawler;

public class AttackLeftStrategy implements AttackStrategy {
    public void attack(Player player, Enemy enemy) {
        int x = player.getX() - 300;
        int y = player.getY();
        if (Math.abs(x - enemy.getX()) <= 300 && Math.abs(y - enemy.getY()) <= 300) {
            enemy.setHP(enemy.getHP() - player.getWeapon().getDamage());
        }
    }
}

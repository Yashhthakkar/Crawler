package com.example.crawler;

public class AttackDownStrategy implements AttackStrategy {
    public void attack(Player player, Enemy enemy) {
        int x = player.getX();
        int y = player.getY() + 301;
        if (Math.abs(x - enemy.getX()) <= 300 && Math.abs(y - enemy.getY()) <= 300) {
            enemy.setHP(enemy.getHP() - player.getWeapon().getDamage());
        }
    }
}

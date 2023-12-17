package com.example.crawler;
import org.junit.Test;

import static org.junit.Assert.*;

public class Sprint4Tests {
    @Test
    public void playerDamaged() {
        Player player = new Player();
        Enemy enemy = new FastEnemy(player, "Easy");
        player.setHealth(1000);
        assertEquals(1000,player.getHealth());
        player.setX(0);
        player.setY(0);
        enemy.setX(0);
        enemy.setY(0);
        enemy.update(player.getX(), player.getY());
        assertEquals(1000 - enemy.getDamage(), player.getHealth());
    }
    @Test
    public void playerDamagedTopRight() {
        Player player = new Player();
        Enemy enemy = new FastEnemy(player, "Easy");
        player.setHealth(1000);
        assertEquals(1000,player.getHealth());
        player.setX(0);
        player.setY(200);
        enemy.setX(250);
        enemy.setY(0);
        enemy.update(player.getX(), player.getY());
        assertEquals(1000 - enemy.getDamage(), player.getHealth());
    }
    @Test
    public void playerDamagedBottomLeft() {
        Player player = new Player();
        Enemy enemy = new FastEnemy(player, "Easy");
        player.setHealth(1000);
        assertEquals(1000,player.getHealth());
        player.setX(300);
        player.setY(300);
        enemy.setX(0);
        enemy.setY(550);
        enemy.update(player.getX(), player.getY());
        assertEquals(1000 - enemy.getDamage(), player.getHealth());
    }
    @Test
    public void playerDamagedBottomRight() {
        Player player = new Player();
        Enemy enemy = new FastEnemy(player, "Easy");
        player.setHealth(1000);
        assertEquals(1000,player.getHealth());
        player.setX(0);
        player.setY(0);
        enemy.setX(300);
        enemy.setY(300);
        enemy.update(player.getX(), player.getY());
        assertEquals(1000 - enemy.getDamage(), player.getHealth());
    }
    @Test
    public void playerNotDamaged() {
        Player player = new Player();
        Enemy enemy = new FastEnemy(player, "Easy");
        player.setHealth(1000);
        assertEquals(1000,player.getHealth());
        player.setX(0);
        player.setY(0);
        enemy.setX(400);
        enemy.setY(400);
        enemy.update(player.getX(), player.getY());
        assertEquals(1000, player.getHealth());
    }
    @Test
    public void mediumChangesDamage() {
        Player player = new Player();
        Enemy enemy = new FastEnemy(player, "Easy");
        player.setHealth(1000);
        assertEquals(1000,player.getHealth());
        player.setX(0);
        player.setY(0);
        enemy.setX(0);
        enemy.setY(0);

        enemy.update(player.getX(), player.getY());
        assertEquals(1000 - enemy.getDamage(), player.getHealth());

        player.setHealth(1000);
        enemy.resetDmgCounter();
        enemy.setDifficulty("Medium");
        enemy.update(player.getX(), player.getY());
        assertEquals(1000 - (enemy.getDamage() * 1.5), player.getHealth(), .00001);


    }
    @Test
    public void hardChangesDamage() {
        Player player = new Player();
        Enemy enemy = new FastEnemy(player, "Easy");
        player.setHealth(1000);
        assertEquals(1000,player.getHealth());
        player.setX(0);
        player.setY(0);
        enemy.setX(0);
        enemy.setY(0);

        enemy.update(player.getX(), player.getY());
        assertEquals(1000 - enemy.getDamage(), player.getHealth());

        player.setHealth(1000);
        enemy.resetDmgCounter();
        enemy.setDifficulty("Hard");
        enemy.update(player.getX(), player.getY());
        assertEquals(1000 - (enemy.getDamage() * 2), player.getHealth(), .00001);


    }

    @Test
    public void EnemyMoveRight() {

        Enemy enemy = new Enemy();
        enemy.setX(100);
        enemy.setY(100);
        enemy.setDirection(1); //right
        enemy.setSpeed(1);

        assertEquals(100, enemy.getX());
        enemy.move();
        assertEquals(101, enemy.getX());


    }

    @Test
    public void EnemyMoveLeft() {

        Enemy enemy = new Enemy();
        enemy.setX(100);
        enemy.setY(100);
        enemy.setDirection(-1); //left
        enemy.setSpeed(1);

        assertEquals(100, enemy.getX());
        enemy.move();
        assertEquals(99, enemy.getX());

    }
    @Test
    public void EnemySpeedChangesDistance() {
        Enemy enemy = new Enemy();
        enemy.setX(100);
        enemy.setY(100);
        enemy.setDirection(1); //right
        enemy.setSpeed(10);

        assertEquals(100, enemy.getX());
        enemy.move();
        assertEquals(110, enemy.getX());
    }

}

package com.example.crawler;
import org.junit.Test;
import static org.junit.Assert.*;
public class Sprint5Tests {

    @Test
    public void firePowerUp() {
        Player player = new Player();
        PowerUp fire = new FirePowerUp(player);

        player.setScore(100);
        assertEquals(100, player.getScore());

        fire.usePowerUp(player);
        assertEquals(150, player.getScore());
    }
    @Test
    public void lightningPowerUp() {
        Player player = new Player();
        PowerUp lightning = new LightningPowerUp(player);

        player.setSpeedFactor(10);
        assertEquals(10, player.getSpeedFactor());

        lightning.usePowerUp(player);
        assertEquals(12, player.getSpeedFactor());
    }
    @Test
    public void heartPowerUp() {
        Player player = new Player();
        PowerUp heart = new HeartPowerUp(player);

        player.setHealth(200);
        assertEquals(200, player.getHealth());

        heart.usePowerUp(player);
        assertEquals(250, player.getHealth());
    }
    @Test public void PowerUpsOnceUse() {
        Player player = new Player();
        PowerUp fire = new FirePowerUp(player);
        PowerUp lightning = new LightningPowerUp(player);
        PowerUp heart = new HeartPowerUp(player);

        player.setScore(100);
        assertEquals(100, player.getScore());
        fire.usePowerUp(player);
        assertEquals(150, player.getScore());
        fire.usePowerUp(player);
        assertEquals(150, player.getScore());

        player.setSpeedFactor(10);
        assertEquals(10, player.getSpeedFactor());
        lightning.usePowerUp(player);
        assertEquals(12, player.getSpeedFactor());
        lightning.usePowerUp(player);
        assertEquals(12, player.getSpeedFactor());

        player.setHealth(200);
        assertEquals(200, player.getHealth());
        heart.usePowerUp(player);
        assertEquals(250, player.getHealth());
        heart.usePowerUp(player);
        assertEquals(250, player.getHealth());

    }

    @Test
    public void canAttackTest() {
        Player player = new Player();
        Enemy enemy = new Enemy();
        enemy.setHP(100);
        player.equipWeapon(new Spear());
        int dmg = player.getEquippedWeapon().getDamage();

        assertEquals(100, enemy.getHP());
        player.attack(enemy);
        assertEquals(100 - dmg, enemy.getHP());
    }

    @Test
    public void attackCanMiss() {
        Player player = new Player();
        Enemy enemy = new Enemy();
        enemy.setHP(100);

        assertEquals(100, enemy.getHP());
        player.attack(enemy);
        assertEquals(100, enemy.getHP());
    }

    @Test
    public void weaponAttacksOnce() {
        Player player = new Player();
        Enemy enemy = new Enemy();
        enemy.setHP(100);
        Spear spear = new Spear();
        spear.setDamage(10);
        player.equipWeapon(spear);

        assertEquals(100, enemy.getHP());
        player.attack(enemy);
        assertEquals(90, enemy.getHP());
        player.attack(enemy);
        assertEquals(90, enemy.getHP());
    }
    @Test
    public void killIncreasesScore() {
        Player player = new Player();
        Enemy enemy = new Enemy();
        player.setScore(100);
        Spear spear = new Spear();
        spear.setDamage(10);
        player.equipWeapon(spear);
        enemy.setHP(10);

        assertEquals(100, player.getScore());
        player.attack(enemy);
        assertEquals(150, player.getScore());
    }

    @Test
    public void ScoreStaysWhenNoKill() {
        Player player = new Player();
        Enemy enemy = new Enemy();
        player.setScore(100);
        Spear spear = new Spear();
        spear.setDamage(10);
        player.equipWeapon(spear);
        enemy.setHP(100);

        assertEquals(100, player.getScore());
        player.attack(enemy);
        assertEquals(100, player.getScore());
    }

    @Test
    public void canUseMultipleWeapons() {
        Player player = new Player();
        Enemy enemy = new Enemy();
        player.setScore(100);
        enemy.setHP(30);

        Spear spear = new Spear();
        spear.setDamage(10);
        Club club = new Club();
        club.setDamage(10);
        Sword sword = new Sword();
        sword.setDamage(10);

        player.equipWeapon(spear);
        assertEquals(30, enemy.getHP());
        assertEquals(100, player.getScore());
        player.attack(enemy);
        assertEquals(100, player.getScore());
        assertEquals(20, enemy.getHP());
        player.attack(enemy);
        assertEquals(100, player.getScore());
        assertEquals(20, enemy.getHP());
        player.equipWeapon(club);
        player.attack(enemy);
        assertEquals(100, player.getScore());
        assertEquals(10, enemy.getHP());
        player.equipWeapon(sword);
        player.attack(enemy);
        assertEquals(150, player.getScore());
        assertEquals(0, enemy.getHP());
    }

}
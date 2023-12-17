package com.example.crawler;

import org.junit.Test;


import static org.junit.Assert.*;
public class CrawlerTests1 {

    @Test
    public void playerSetHPTest() {
        Player player = new Player();
        player.setHealth(100);
        assertEquals(100, player.getHealth());
        player.setHealth(1000);
        assertEquals(1000, player.getHealth());
    }
    @Test
    public void EnemySetDamageTest() {
        Enemy enemy = new Enemy();
        enemy.setDamage(100);
        assertEquals(100, enemy.getDamage());
    }
    @Test
    public void createWeaponTest() {
        Weapon weapon = new Weapon(100, "RedSword", 15);
        assertEquals(100, weapon.getDamage());
        assertEquals("RedSword", weapon.getName());
        assertEquals(15, weapon.getPrice());
    }

    @Test
    public void setReductionTest() {
        Armor armor = new Armor();
        armor.setReduction(32);
        assertEquals(32, armor.getReduction());
    }
    @Test
    public void EquipmentTest() {
        Armor armor = new Armor();
        Weapon weapon = new Weapon();
        armor.setPrice(100);
        weapon.setPrice(100);

        Equipment list[] = {armor, weapon};
        assertEquals(100, list[0].getPrice());
        assertEquals(100, list[1].getPrice());

    }

    @Test
    public void PlayerHasWeapon() {
        Weapon weapon = new Weapon(50, "RedSword", 15);
        Player player = new Player();
        player.setWeapon(weapon);
        assertEquals(50, player.getWeapon().getDamage());
        assertEquals("RedSword", player.getWeapon().getName());
        assertEquals(15, player.getWeapon().getPrice());
    }

    @Test
    public void PowerUpReductionDamage() {
        PowerUp power = new PowerUp();
        power.setDamage(200);
        power.setReduction(100);
        assertEquals(200, power.getDamage());
        assertEquals(100, power.getReduction());
    }

    @Test
    public void PlayerEnemyNameTest() {
        Player player = new Player();
        player.setName("Kevin");

        Enemy enemy = new Enemy();
        enemy.setName("Skeleton");

        assertEquals("Kevin", player.getName());
        assertEquals("Skeleton", enemy.getName());

    }

    @Test
    public void PlayerEquipmentTest() {
        Player player = new Player();
        Weapon weapon = new Weapon(15, "BlueSword", 10);
        Armor armor = new Armor(20, "RedArmor", 15);

        player.setWeapon(weapon);
        player.setArmor(armor);
    }



}

package com.example.crawler;

import java.util.List;
import java.util.ArrayList;

public class Player implements Subject {

    private String name;
    private int health;
    private Weapon weapon;
    private Armor armor;
    private int x;
    private int y;
    private int coins;
    private int speedFactor = 5;
    private Weapon equippedWeapon;
    private int score;

    private MovementStrategy movementStrategy;

    private AttackStrategy attackStrategy;
    private List<Observer> observers = new ArrayList<>();

    // Constructor to initialize player with default values
    public Player() {
        this.name = "Player";
        this.health = 600;
        this.weapon = new Weapon();
        this.armor = new Armor();
        this.x = 1;
        this.y = 1;
        this.coins = 0;
    }

    // Constructor to initialize player with specific values
    public Player(String name, int health, Weapon weapon, int x, int y, int coins) {
        this.name = name;
        this.health = health;
        this.weapon = weapon;
        this.x = x;
        this.y = y;
        this.coins = coins;
    }


    public void reset() {
        this.health = 600; // Reset health to default
        this.weapon = new Weapon(); // Reset weapon
        this.armor = new Armor(); // Reset armor
        this.x = 1; // Reset position X
        this.y = 1; // Reset position Y
        this.coins = 0; // Reset coins
        this.speedFactor = 5; // Reset speed factor
        this.equippedWeapon = null; // Reset equipped weapon
        this.score = 0; // Reset score
        // Reset any other relevant attributes here
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(x, y);
        }
    }

    public void equipWeapon(Weapon weapon) {
        this.equippedWeapon = weapon;
    }

    // Overload the attack method to include weapon damage
    public void attack(Enemy enemy) {
        if (equippedWeapon != null) {
            enemy.setHP(enemy.getHP() - equippedWeapon.getDamage());
            if (enemy.getHP() <= 0) {
                setScore(getScore() + 50); // Increase score by 50 points
            }
            equippedWeapon = null;
        }
    }


    // Getter and Setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }


    public Armor getArmor() {
        return armor;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        notifyObservers();
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        notifyObservers();
    }

    public int getCoins() {
        return coins;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public int getSpeedFactor() {
        return speedFactor;
    }

    public void setSpeedFactor(int speedFactor) {
        this.speedFactor = speedFactor;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    // Method to set movement strategy
    public void setMovementStrategy(MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }

    public MovementStrategy getMovementStrategy() {
        return movementStrategy;
    }

    public AttackStrategy getAttackStrategy() {
        return attackStrategy;
    }

    public void setAttackStrategy(AttackStrategy attackStrategy) {
        this.attackStrategy = attackStrategy;
    }

    // Method to move the player according to the strategy set
    public void move() {
        if (movementStrategy != null) {
            movementStrategy.move(this);
        }
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }
}
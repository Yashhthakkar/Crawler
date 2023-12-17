package com.example.crawler;
import org.junit.Test;

import static org.junit.Assert.*;
public class Sprint3Tests {
    @Test
    public void moveWithNoStrategy() {
        Player player = new Player();
        int x = player.getX();
        int y = player.getY();
        player.move();

        assertEquals(x, player.getX());
        assertEquals(y, player.getY());
    }

    @Test
    public void setMovementStrategyTest() {
        Player player = new Player();
        assertEquals(null, player.getMovementStrategy());
        MovementStrategy move = new MoveDownStrategy();
        player.setMovementStrategy(move);
        assertEquals(move, player.getMovementStrategy());
    }

    @Test
    public void moveRightTest() {
        Player player = new Player();
        int x = player.getX();
        MovementStrategy right = new MoveRightStrategy();
        player.setMovementStrategy(right);
        player.move();
        assertEquals(x + 5, player.getX());
    }

    @Test
    public void moveDown() {
        Player player = new Player();
        int y = player.getY();
        MovementStrategy down = new MoveDownStrategy();
        player.setMovementStrategy(down);
        player.move(); //should not actually move as will go out of bounds
        assertEquals(y + 5, player.getY());
    }

    @Test
    public void moveLeft() {
        Player player = new Player();
        int x = 150;
        player.setX(x);
        MovementStrategy Left = new MoveLeftStrategy();
        player.setMovementStrategy(Left);
        player.move(); //should not actually move as will go out of bounds
        assertEquals(x - 5, player.getX());
    }

    @Test
    public void moveUp() {
        Player player = new Player();
        int y = 200;
        player.setY(y);
        MovementStrategy up = new MoveUpStrategy();
        player.setMovementStrategy(up);
        player.move(); //should not actually move as will go out of bounds
        assertEquals(y - 5, player.getY());
    }



    @Test
    public void moveLeftOutofBounds() {
        Player player = new Player();
        int x = player.getX();
        MovementStrategy Left = new MoveLeftStrategy();
        player.setMovementStrategy(Left);
        player.move(); //should not actually move as will go out of bounds
        assertEquals(x, player.getX());
    }
    @Test
    public void moveUpOutofBounds() {
        Player player = new Player();
        int y = player.getY();
        MovementStrategy up = new MoveUpStrategy();
        player.setMovementStrategy(up);
        player.move(); //should not actually move as will go out of bounds
        assertEquals(y, player.getY());
    }

    @Test
    public void moveRightOutofBounds() {
        Player player = new Player();
        int x = 299;
        player.setX(x);
        MovementStrategy right = new MoveRightStrategy();
        player.setMovementStrategy(right);
        player.move();
        assertEquals(x, player.getX());
    }

    @Test
    public void moveDownOutofBounds() {
        Player player = new Player();
        int y = 499;
        player.setY(y);
        MovementStrategy down = new MoveDownStrategy();
        player.setMovementStrategy(down);
        player.move(); //should not actually move as will go out of bounds
        assertEquals(y, player.getY());
    }



}
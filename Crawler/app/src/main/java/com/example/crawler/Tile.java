package com.example.crawler;

public class Tile {
    private boolean walkable;

    public Tile(boolean walkable) {
        this.walkable = walkable;
    }

    public boolean isWalkable() {
        return walkable;
    }
}


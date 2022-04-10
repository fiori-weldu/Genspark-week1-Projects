package org.example;


public class Goblin extends boardObj {

    private float health;

    public Goblin(int x, int y) {
        super(x, y, "\uD83D\uDC7A");
        health = 50.0f;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float value) {
        this.health = value;
    }
}
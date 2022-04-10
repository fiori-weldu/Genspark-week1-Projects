package org.example;

public class Human extends boardObj {

    private float health;

    public Human(int x, int y) {
        super(x, y, "\uD83D\uDC6E");
        health = 100;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float value) {
        this.health = value;
    }
}

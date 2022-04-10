package org.example;

public class boardObj {
    String type;
    private int x;
    private int y;

    public boardObj(int x, int y, String type) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return String.valueOf(type);
    }

}

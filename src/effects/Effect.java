package effects;

import enums.Id;

import java.awt.*;

public abstract class Effect {
    public static int EFFECT_SIZE = 32;

    protected int frame;
    protected int frameDelay;


    //Coordinate
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    //info
    protected Id id;
    protected boolean isDead;

    public Effect(int x, int y, int width, int height, Id id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        frame = 0;
        frameDelay = 0;
        isDead = false;
    }

    //getters and setters
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x += x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y += y;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public Id getId() {
        return id;
    }

    public boolean isDead() {
        return isDead;
    }

    // Drawing method
    public abstract void paint(Graphics g);

    // Update method
    public abstract void update();

    // Die
    public void die() {
        isDead = true;
    }
}

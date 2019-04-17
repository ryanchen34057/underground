package effects;

import character.Id;

import java.awt.*;

public abstract class Effect {
    public static int EFFECT_SIZE = 32;

    protected int frame;
    protected int frameDelay;


    //Coordinate
    private int x;
    private int y;
    private int width;
    private int height;

    //info
    private Id id;

    public Effect(int x, int y, int width, int height, Id id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        frame = 0;
        frameDelay = 0;
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

    // Drawing method
    public abstract void paint(Graphics g);

    // Update method
    public abstract void update();

}

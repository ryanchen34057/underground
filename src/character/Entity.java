package character;

import enums.Id;

import java.awt.*;

public abstract class Entity {
    //Coordinate
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected double velX;
    protected double velY;
    protected int facing;  //-1 -> left, 1 -> right

    //Physics
    protected double gravity;

    //info
    protected Id id;


    public Entity(int x, int y, int width, int height, Id id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velX = 0;
        this.velY = 0;
        gravity = 0;
        this.id = id;
        facing = 0;
    }

    //getters and setters
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
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
    public double getVelX() {
        return velX;
    }
    public void setVelX(double velX) {
        this.velX = velX;
    }
    public double getVelY() {
        return velY;
    }
    public void setVelY(double velY) {
        this.velY = velY;
    }
    public int getFacing() {
        return facing;
    }
    public Id getId() {
        return id;
    }
    public double getGravity() {
        return gravity;
    }
    public void setGravity(double gravity) {
        this.gravity = gravity;
    }
    public void setFacing(int facing) {
        this.facing = facing;
    }

    // Drawing method
    public abstract void paint(Graphics g);

    // Update method
    public abstract void update();

    // Collision test
    public abstract Rectangle getBounds();
    public abstract Rectangle getBoundsTop();
    public abstract Rectangle getBoundsBottom();
    public abstract Rectangle getBoundsLeft();
    public abstract Rectangle getBoundsRight();

}

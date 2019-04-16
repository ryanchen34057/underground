package character;

import java.awt.*;

public abstract class Entity {
    //Coordinate
    private int x;
    private int y;
    private int width;
    private int height;
    private double velX;
    private double velY;
    private int facing;  //-1 -> left, 1 -> right

    //Physics
    private double gravity;

    //info
    private Id id;


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
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), width, height);
    }
    public Rectangle getBoundsTop() {
        return new Rectangle(getX()+40, getY(), width-80,1 );
    }
    public Rectangle getBoundsBottom() {
        return new Rectangle(getX()+40, getY()+height, width-80,1 );
    }
    public Rectangle getBoundsLeft() {
        return new Rectangle(getX()+25, getY()+20, 1,height-40 );
    }
    public Rectangle getBoundsRight() {
        return new Rectangle(getX()+width-30, getY()+20, 1,height-40 );
    }

}

package level;

import character.Id;

import java.awt.*;

public abstract class Tile {
    //Coordinate
    private int x;
    private int y;
    private int width;
    private int height;

    //Physics
    private double friction;

    //State
    private boolean breakable;

    //info
    private Id id;

    public Tile(int x, int y, int width, int height, boolean breakable, Id id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        this.breakable = breakable;
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

    // Collision test
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), width, height);
    }
    public Rectangle getBoundsTop() {
        return new Rectangle(getX()+10, getY(), width-20,1 );
    }
    public Rectangle getBoundsBottom() {
        return new Rectangle(getX()+10, getY()+height, width-20,1 );
    }
    public Rectangle getBoundsLeft() {
        return new Rectangle(getX(), getY()+20, 1,height-40 );
    }
    public Rectangle getBoundsRight() { return new Rectangle(getX()+width, getY()+20, 1,height-40 ); }
}

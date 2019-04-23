package gameObject.tiles;

import UI.Game;
import enums.Id;
import gameObject.ICollidable;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Tile implements ICollidable {
    //Coordinate
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    //State
    protected boolean breakable;

    //info
    protected Id id;
    protected boolean isDead;

    //BufferedImage
    protected BufferedImage bufferedImage;

    public Tile(int x, int y, int width, int height, boolean breakable, Id id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        this.breakable = breakable;
        this.isDead = false;
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

    // Collision test
    public abstract Rectangle getBounds();
    public abstract Rectangle getBoundsTop();
    public abstract Rectangle getBoundsBottom();
    public abstract Rectangle getBoundsLeft();
    public abstract Rectangle getBoundsRight();

    // Die
    public abstract void die();

    public boolean inTheScreen(Tile t) {
        return (t.getX() >= x - (Game.WIDTH * Game.SCALE) / 1.5) && (t.getX() < x + (Game.WIDTH * Game.SCALE))
                && (t.getY() >= y - (Game.HEIGHT * Game.SCALE) / 1.5) && (t.getY() < y + (Game.HEIGHT * Game.SCALE) / 1.5);
    }

}

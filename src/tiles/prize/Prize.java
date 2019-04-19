package tiles.prize;

import enums.Id;
import tiles.Tile;

import java.awt.*;

public abstract class Prize extends Tile {
    //Coordinate
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    //info
    private Id id;
    private int point;

    public Prize(int x, int y, int width, int height,  boolean breakable, int point, Id id) {
        super(x, y, width, height, breakable, id);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        this.point = point;
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
    public int getPoint() {
        return point;
    }
    public void setPoint(int point) {
        this.point = point;
    }

    // Drawing method
    public abstract void paint(Graphics g);

    // Update method
    public abstract void update();
}

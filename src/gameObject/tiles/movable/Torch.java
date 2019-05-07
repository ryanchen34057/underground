package gameObject.tiles.movable;

import enums.Direction;
import enums.Id;
import gameObject.ICollidable;
import graphics.FrameManager;
import gameObject.tiles.Tile;
import util.CollisionCondition;

import java.awt.*;

public class Torch extends Tile {
    public static int TILE_SIZE = 80;
    private int frame;
    private int frameDelay;
    public Torch(int x, int y, int width, int height, Id id) {
        super(x, y, width, height, id);
        frame = 0;
        frameDelay = 0;

    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(FrameManager.getDiamondFrame()[frame].getBufferedImage(), x, y,
                width, height, null);
    }

    @Override
    public void update() {
        frameDelay++;
        if (frameDelay >= 10) {
            frame++;
            if (frame >= FrameManager.getDiamondFrame().length) {
                frame = 0;
            }
            frameDelay = 0;
        }
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

    @Override
    public Rectangle getBoundsTop() {
        return null;
    }

    @Override
    public Rectangle getBoundsBottom() {
        return null;
    }

    @Override
    public Rectangle getBoundsLeft() {
        return null;
    }

    @Override
    public Rectangle getBoundsRight() {
        return null;
    }

    @Override
    public void die() {

    }

    @Override
    public boolean collidesWith(ICollidable other, CollisionCondition collisionCondition) {
        return false;
    }

    @Override
    public void handleCollision(ICollidable other, Direction direction) {

    }

    @Override
    public void reactToCollision(ICollidable other, Direction direction) {

    }
}

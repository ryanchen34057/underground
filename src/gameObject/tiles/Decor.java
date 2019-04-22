package gameObject.tiles;

import enums.Direction;
import enums.Id;
import gameObject.ICollidable;
import util.CollisionCondition;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Decor extends Tile {
    public Decor(int x, int y, int width, int height, boolean breakable, Id id, BufferedImage bufferedImage) {
        super(x, y, width, height, breakable, id);
        this.bufferedImage = bufferedImage;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(bufferedImage, x, y,
                width, height, null);
    }

    @Override
    public void update() {

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
    public Direction collidesWith(ICollidable other, CollisionCondition collisionCondition) {
        return null;
    }

    @Override
    public void handleCollision(ICollidable other, Direction direction) {

    }

    @Override
    public void reactToCollision(ICollidable other, Direction direction) {

    }
}

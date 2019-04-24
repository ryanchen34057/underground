package gameObject.tiles.wall;

import UI.Game;
import enums.Direction;
import enums.Id;
import gameObject.ICollidable;
import gameObject.tiles.Tile;
import graphics.FrameManager;
import util.CollisionCondition;

import java.awt.*;

public class VanishingRock extends Tile {
    private boolean isStepOn;
    private int frame;
    private int frameDelay;
    public VanishingRock(int x, int y, int width, int height, Id id) {
        super(x, y, width, height,id);
        isStepOn = false;
        frame = 0;
        frameDelay = 0;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(FrameManager.getVanishingRockFrame()[frame].getBufferedImage(), x, y, width, height, null);
        if(Game.debugMode) {
            g.setColor(Color.GRAY);
            g.drawRect(x, y, width,height);
        }
    }

    @Override
    public void update() {
        if(isStepOn) {
            frameDelay++;
            if (frameDelay >= 8) {
                frame++;
                if (frame >= FrameManager.getVanishingRockFrame().length) {
                    frame = 0;
                    die();
                }
                frameDelay = 0;
            }
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
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
        isDead = true;
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
        if(direction == Direction.BOTTOM) {
            isStepOn = true;
        }
    }
}

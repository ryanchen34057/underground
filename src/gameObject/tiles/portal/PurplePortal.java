package gameObject.tiles.portal;

import UI.Game;
import enums.Direction;
import enums.Id;
import gameObject.ICollidable;
import graphics.FrameManager;
import gameObject.tiles.Tile;
import util.CollisionCondition;

import java.awt.*;

public class PurplePortal extends Tile {
    public static final int PORTAL_SIZE = 200;
    private int frame;
    private int frameDelay;
    public PurplePortal(int x, int y, int width, int height, boolean breakable, Id id) {
        super(x, y, width, height, breakable, id);
        frame = 0;
        frameDelay = 0;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(FrameManager.getPurplePortalFramePortalFrame()[frame].getBufferedImage(), x, y,
                width, height, null);

        if (Game.debugMode) {
            g.setColor(Color.MAGENTA);
            g.drawRect(x+20, y, width-50, height);
        }
    }

    @Override
    public void update() {
        frameDelay++;
        if (frameDelay >= 3) {
            frame++;
            if (frame >= FrameManager.getBluePortalFrame().length) {
                frame = 0;
            }
            frameDelay = 0;
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x+20, y, width-50, height);
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

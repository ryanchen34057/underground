package gameObject.tiles.portal;

import UI.Game;
import enums.Direction;
import enums.Id;
import gameObject.ICollidable;
import gameObject.tiles.Tile;
import graphics.FrameManager;
import util.CollisionCondition;

import java.awt.*;

public class Portal extends Tile {
    private Color color;
    private Direction direction;
    public static final int PORTAL_SIZE = (int)(Game.WIDTH*Game.SCALE*0.15625);
    private int frame;
    private int frameDelay;
    public Portal(int x, int y, int width, int height, Id id, Color color, Direction direction) {
        super(x, y, width, height, id);
        this.color = color;
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(FrameManager.getPortalFrame(color, direction)[frame].getBufferedImage(), x, y,
                    width, height, null);
        if (Game.debugMode) {
            g.setColor(Color.RED);
            g.drawRect(getX()+20, getY(), super.getWidth()-50, getHeight());
        }
    }

    @Override
    public void update() {
        frameDelay++;
        if (frameDelay >= 3) {
            frame++;
            if (frame >= FrameManager.getPortalFrame(color, direction).length) {
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

    }
}

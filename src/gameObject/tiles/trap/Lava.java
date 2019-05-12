package gameObject.tiles.trap;

import UI.Game;
import enums.Direction;
import enums.Id;
import gameObject.ICollidable;
import gameObject.tiles.Tile;
import graphics.FrameManager;
import util.CollisionCondition;

import java.awt.*;

public class Lava extends Tile {
    public static final int LAVA_WIDTH = (int)(300* Game.widthRatio);
    public static final int LAVA_HEIGHT = (int)(89* Game.heightRatio);
    private int frame;
    private int frameDelay;
    private float velY;

    public Lava(int x, int y, int width, int height, Id id) {
        super(x, y, width, height, id);
        velY = 0;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(FrameManager.getLavaFrame()[frame].getBufferedImage(), x, y,
                width, height, null);
    }

    @Override
    public void update() {
        y += Math.round(velY);
        frameDelay++;
        if (frameDelay >= 10/Game.UpdatesRatio) {
            frame++;
            if (frame >= FrameManager.getLavaFrame().length) {
                frame = 0;
            }
            frameDelay = 0;
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

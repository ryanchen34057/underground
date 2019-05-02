package gameObject.tiles.trap;

import UI.Game;
import enums.Direction;
import enums.Id;
import gameObject.ICollidable;
import gameObject.tiles.Tile;
import graphics.FrameManager;
import util.CollisionCondition;

import java.awt.*;

public class Spring extends Tile {
    private int frame;
    private int frameDelay;
    private int[] heightList;
    private boolean isStepOn;
    public Spring(int x, int y, int width, int height, Id id) {
        super(x, y, width, height, id);
        frame = 0;
        frameDelay = 0;
        isStepOn = false;
        heightList = new int[]{15, 16, 32, 64, 63, 63, 63, 62, 42, 29, 15};
    }

    public void setStepOn(boolean stepOn) {
        isStepOn = stepOn;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(FrameManager.getSpringFrame()[frame].getBufferedImage(), x, y,
                width, height, null);
        if(Game.debugMode) {
            g.drawRect(x, y + height - heightList[frame], width, heightList[frame]);
        }
    }

    @Override
    public void update() {
        if(isStepOn) {
            frameDelay++;
            if (frameDelay >= 2) {
                frame++;
                if (frame >= FrameManager.getSpringFrame().length) {
                    frame = 0;
                    isStepOn = false;
                }
                frameDelay = 0;
            }
        }

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y + (height - heightList[frame]), width, heightList[frame]);
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

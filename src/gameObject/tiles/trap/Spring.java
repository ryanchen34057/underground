package gameObject.tiles.trap;

import UI.Game;
import enums.Direction;
import enums.Id;
import gameObject.ICollidable;
import gameObject.tiles.Tile;
import graphics.FrameManager;
import util.CollisionCondition;

import java.awt.*;

import static enums.Direction.*;

public class Spring extends Tile {
    private int frame;
    private int frameDelay;
    private int[] heightList;
    private boolean isStepOn;
    private Direction direction;
    public Spring(int x, int y, int width, int height, Id id, Direction direction) {
        super(x, y, width, height, id);
        frame = 0;
        frameDelay = 0;
        isStepOn = false;
        heightList = new int[]{(int)(height*(15.0/64.0)), (int)(height*(16.0/64.0)), (int)(height*(32.0/64.0)), (int)(height*(64.0/64.0)),
                (int)(height*(63.0/64.0)), (int)(height*(63.0/64.0)), (int)(height*(63.0/64.0)), (int)(height*(62.0/64.0)), (int)(height*(42.0/64.0)), (int)(height*(29.0/64.0)), (int)(height*(15.0/64.0))};
        this.direction = direction;
    }

    public void setStepOn(boolean stepOn) {
        isStepOn = stepOn;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(FrameManager.getSpringFrame(direction)[frame].getBufferedImage(), x, y, width, height, null);
        if(Game.debugMode) {
            if(direction == UP) {
                g.drawRect(x, y + height - heightList[frame], width, heightList[frame]);
            }
            else if(direction == DOWN) {
                g.drawRect(x, y, width, heightList[frame]);
            }
            else if(direction == LEFT) {
                g.drawRect(x + height - heightList[frame], y, heightList[frame], height);
            }
            else if(direction == RIGHT) {
                g.drawRect(x, y, heightList[frame], height);
            }

        }
    }

    @Override
    public void update() {
        if(isStepOn) {
            frameDelay++;
            if (frameDelay >= 2/Game.UpdatesRatio) {
                frame++;
                if (frame >= FrameManager.getSpringFrame(direction).length) {
                    frame = 0;
                    isStepOn = false;
                }
                frameDelay = 0;
            }
        }

    }

    @Override
    public Rectangle getBounds() {
        switch (direction) {
            case UP:
                return new Rectangle(x, y + height - heightList[frame], width, heightList[frame]);
            case DOWN:
                return new Rectangle(x, y, width, heightList[frame]);
            case LEFT:
                return new Rectangle(x + height - heightList[frame], y, heightList[frame], height);
            case RIGHT:
                return new Rectangle(x, y, heightList[frame], height);
        }
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

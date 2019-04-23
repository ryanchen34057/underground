package gameObject.tiles.prize;

import UI.Game;
import enums.Direction;
import enums.Id;
import gameObject.ICollidable;
import graphics.FrameManager;
import util.CollisionCondition;

import java.awt.*;

public class Coin extends Prize {
    public static final int PRIZE_SIZE = 64;
    private int frameDelay, frame;
    public Coin(int x, int y, int width, int height, boolean breakable, int point, Id id) {
        super(x, y, width, height, breakable, point, id);
        frameDelay = 0;
        frame = 0;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(FrameManager.getPrizeFrame()[frame].getBufferedImage(), super.getX(), super.getY(),
                super.getWidth(), super.getHeight(), null);
        if(Game.debugMode) {
            g.setColor(Color.YELLOW);
            g.drawRect(x+10, y+10, width-20,height-20);
        }
    }

    @Override
    public void update() {
        frameDelay++;
        if (frameDelay >= 5) {
            frame++;
            if (frame >= FrameManager.getPrizeFrame().length) {
                frame = 0;
            }
            frameDelay = 0;
        }
    }

    @Override
    public void die() {
        isDead = true;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x+10, y+10, width-20,height-20);
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
    public boolean collidesWith(ICollidable other, CollisionCondition collisionCondition) {
        return false;
    }

    @Override
    public void handleCollision(ICollidable other, Direction direction) {

    }

    @Override
    public void reactToCollision(ICollidable other, Direction direction) {
        die();
    }
}

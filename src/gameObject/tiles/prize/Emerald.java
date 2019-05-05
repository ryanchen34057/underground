package gameObject.tiles.prize;

import UI.Game;
import audio.SoundEffectPlayer;
import enums.Direction;
import enums.Id;
import gameObject.ICollidable;
import graphics.FrameManager;
import util.CollisionCondition;

import java.awt.*;

public class Emerald extends Prize {
    private int serial;
    private boolean isEaten;
    public static final int PRIZE_SIZE = (int)(64* Game.widthRatio);
    private int frameDelay, frame;
    public Emerald(int x, int y, int width, int height, int point, Id id, int serial) {
        super(x, y, width, height, point, id);
        frameDelay = 0;
        frame = 0;
        this.serial = serial;
        isEaten = false;
        boundsRectangle = new Rectangle(x+10, y+10, width-20,height-20);
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

    public int getSerial() {
        return serial;
    }

    public boolean isEaten() {
        return isEaten;
    }

    public void setEaten(boolean eaten) {
        isEaten = eaten;
    }

    @Override
    public void die() {
        isDead = true;
    }

    @Override
    public Rectangle getBounds() {
        return boundsRectangle;
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
        SoundEffectPlayer.playSoundEffect("Prize");
        isEaten = true;
        die();
    }
}

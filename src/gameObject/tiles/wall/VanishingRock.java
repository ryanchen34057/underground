package gameObject.tiles.wall;

import UI.Game;
import enums.Direction;
import enums.Id;
import gameObject.ICollidable;
import gameObject.tiles.Tile;
import graphics.SpriteManager;
import util.CollisionCondition;

import java.awt.*;

public class VanishingRock extends Tile {
    private boolean isStepOn;
    private float alpha;
    private int frame;
    private int frameDelay;
    private Direction direction;
    public VanishingRock(int x, int y, int width, int height, Id id) {
        super(x, y, width, height,id);
        isStepOn = false;
        frame = 0;
        frameDelay = 0;
        alpha = 1;
    }

    public boolean isStepOn() {
        return isStepOn;
    }

    @Override
    public void paint(Graphics g) {
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
        Graphics2D g2 = (Graphics2D) g;
        g2.setComposite(ac);
        g2.drawImage(SpriteManager.vanishingRock.getBufferedImage(), x, y, width, height, null);
        if(Game.debugMode) {
            g.setColor(Color.GRAY);
            g.drawRect(x, y, width,height);
        }
        ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1);
        g2.setComposite(ac);
    }

    @Override
    public void update() {
        if(isStepOn) {
            frameDelay++;
            if (frameDelay >= 3) {
                frame++;
                if (frame >= 3) {
                    frame = 0;
                    alpha -= 0.1;
                }
                frameDelay = 0;
            }
            if(alpha <= 0) {
                die();
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
        isStepOn = true;
    }
}

package gameObject.tiles.trap;

import UI.Game;
import enums.Direction;
import enums.Id;
import gameObject.ICollidable;
import graphics.SpriteManager;
import util.CollisionCondition;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RightwardSpike extends Spike {

    public RightwardSpike(int x, int y, int width, int height, boolean breakable, Id id, BufferedImage bufferedImage) {
        super(x, y, width, height, breakable, id, bufferedImage);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(bufferedImage, super.getX(), super.getY(),
                super.getWidth(), super.getHeight(), null);
        if(Game.debugMode) {
            g.drawRect(x, y, width/2-5, height);
            g.drawRect(x+width-35, y+6, 1,height-12);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width/2-5, height);
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
        return new Rectangle(x+width-35, y+6, 1,height-12);
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

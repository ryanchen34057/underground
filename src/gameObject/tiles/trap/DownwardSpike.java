package gameObject.tiles.trap;

import UI.Game;
import enums.Direction;
import enums.Id;
import gameObject.ICollidable;
import graphics.SpriteManager;
import util.CollisionCondition;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DownwardSpike extends Spike {
    public DownwardSpike(int x, int y, int width, int height, boolean breakable, Id id, BufferedImage bufferedImage) {
        super(x, y, width, height, breakable, id, bufferedImage);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(bufferedImage, super.getX(), super.getY(),
                super.getWidth(), super.getHeight(), null);
        if(Game.debugMode) {
            g.drawRect(x, y, width, height/2-5);
            g.drawRect(x+6, y+30, width-12,1);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height/2-5);
    }

    @Override
    public  Rectangle getBoundsTop() {
        return null;
    }
    @Override
    public  Rectangle getBoundsBottom() {
        return new Rectangle(x+6, y+30, width-12,1 );
    }
    @Override
    public  Rectangle getBoundsLeft() {
        return null;
    }
    @Override
    public  Rectangle getBoundsRight() { return null; }

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

package gameObject.tiles.trap;

import UI.Game;
import enums.Direction;
import enums.Id;
import gameObject.ICollidable;
import gameObject.tiles.Tile;
import util.CollisionCondition;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Spike extends Tile {
    private Direction direction;
    public Spike(int x, int y, int width, int height, Id id, BufferedImage bufferedImage, Direction direction) {
        super(x, y, width, height, id);
        this.bufferedImage = bufferedImage;
        this.direction = direction;
        if(id == Id.upwardSpike) {
            boundsRectangle =  new Rectangle(x, y+height/2, width, height/2);
        }
        else if(id == Id.downwardSpike) {
            boundsRectangle = new Rectangle(x, y, width, height/2-5);
        }
        else if(id == Id.leftwardSpike) {
            boundsRectangle = new Rectangle(x+width/2+5, y, width/2, height);
        }
        else {
            boundsRectangle = new Rectangle(x, y, width/2-5, height);
        }
    }

    @Override
    public void paint(Graphics g) {
        if(direction == Direction.UP) {
            g.drawImage(bufferedImage, x, y, width, height, null);
            if(Game.debugMode) {
                g.drawRect(x, y+(int)(height/1.6), width, (int)(height/1.6));
                g.setColor(Color.RED);
                g.drawRect(x+(int)(width*0.07), y+(int)(height/1.6), width-2*(int)(width*0.07),2);
                g.setColor(Color.GREEN);
            }
        }
        else if(direction == Direction.DOWN) {
            g.drawImage(bufferedImage, x, y, width, height, null);
            if(Game.debugMode) {
                g.drawRect(x, y, width, (int)(height/2.5));
                g.setColor(Color.RED);
                g.drawRect(x+(int)(width*0.07), y+(height-(int)(height/1.6)), width-2*(int)(width*0.07),2);
                g.setColor(Color.GREEN);
            }
        }
        else if(direction == Direction.LEFT) {
            g.drawImage(bufferedImage, x, y, width, height, null);
            if(Game.debugMode) {
                g.drawRect(x+(int)(width/1.63), y, (int)(width/2.5), height);
                g.setColor(Color.RED);
                g.drawRect(x+(int)(width/1.63), y, 2,height);
                g.setColor(Color.GREEN);
            }
        }
        else if(direction == Direction.RIGHT)  {
            g.drawImage(bufferedImage, x, y, width, height, null);
            if(Game.debugMode) {
                g.drawRect(x, y, (int)(width/2.5), height);
                g.setColor(Color.RED);
                g.drawRect(x+(int)(width/2.5), y, 2,height);
                g.setColor(Color.GREEN);
            }
        }
    }

    @Override
    public void update() {

    }

    @Override
    public Rectangle getBounds() {
        return boundsRectangle;
    }

    @Override
    public Rectangle getBoundsTop() {
        if(id == Id.upwardSpike) {
            return new Rectangle(x+(int)(width*0.07), y+(int)(height/1.6), width-2*(int)(width*0.07),2);
        }
        return null;
    }

    @Override
    public Rectangle getBoundsBottom() {
        if(id == Id.downwardSpike) {
            return new Rectangle(x+(int)(width*0.07), y+(height-(int)(height/1.6)), width-2*(int)(width*0.07),2);
        }
        return null;
    }

    @Override
    public Rectangle getBoundsLeft() {
        if(id == Id.leftwardSpike) {
            return new Rectangle(x+(int)(width/1.63), y, 2,height);
        }
        return null;
    }

    @Override
    public Rectangle getBoundsRight() {
        if(id == Id.rightwardSpike) {
            return new Rectangle(x+width-35, y+6, 1,height-12);
        }
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

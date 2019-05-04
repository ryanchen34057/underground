package gameObject.tiles.wall;

import UI.Game;
import enums.Direction;
import enums.Id;
import gameObject.ICollidable;
import gameObject.tiles.Tile;
import util.CollisionCondition;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall extends Tile {
    public static final int TILE_SIZE = (int) (64 * Game.widthRatio);
    public Wall(int x, int y, int width, int height, Id id, BufferedImage bufferedImage) {
        super(x, y, width, height, id);
        this.bufferedImage = bufferedImage;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(bufferedImage, x, y,
                    width, height, null);
        if(Game.debugMode) {
            g.setColor(Color.GREEN);
            if(id == Id.halfHeightWall) {
                g.drawRect(x, y, width,height/2);
            }
            else if(id == Id.halfWidthWall) {
                g.drawRect(x, y, width/2,height);
            }
            else {
                g.drawRect(x, y, width,height);
            }
        }
    }

    @Override
    public void update() {

    }

    @Override
    public Rectangle getBounds() {
        if(id == Id.halfHeightWall) {
            return new Rectangle(x, y, width, height/2);
        }
        else if(id == Id.halfWidthWall) {
            return new Rectangle(x, y, width/2, height);
        }
        return new Rectangle(x, y, width, height);
    }

    @Override
    public  Rectangle getBoundsTop() {
        return new Rectangle(x+10, y, width-20,1 );
    }
    @Override
    public  Rectangle getBoundsBottom() {
        return new Rectangle(x+10, y+height, width-20,1 );
    }
    @Override
    public  Rectangle getBoundsLeft() {
        return new Rectangle(x, y+20, 1,height-40 );
    }
    @Override
    public  Rectangle getBoundsRight() { return new Rectangle(x+width, y+20, 1,height-40 ); }

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

package gameObject.tiles.wall;

import UI.Game;
import enums.Direction;
import enums.Id;
import gameObject.ICollidable;
import gameObject.tiles.Tile;
import graphics.SpriteManager;
import util.CollisionCondition;

import java.awt.*;

public class Wall extends Tile {
    public static final int TILE_SIZE = 64;
    public Wall(int x, int y, int width, int height,boolean breakable,  Id id) {
        super(x, y, width, height,breakable, id);
    }

    @Override
    public void paint(Graphics g) {
        if(!breakable) {
            g.drawImage(SpriteManager.wall1.getBufferedImage(), x, y,
                    width, height, null);
        }
        else {
            g.drawImage(SpriteManager.wall2Breakable.getBufferedImage(), x, y,
                    width, height, null);
        }

        if(Game.debugMode) {
            g.setColor(Color.GREEN);
            g.drawRect(x, y, width,height);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public Rectangle getBounds() {
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

package gameObject.tiles.wall;

import UI.Game;
import enums.Direction;
import enums.Id;
import gameObject.ICollidable;
import gameObject.tiles.Tile;
import graphics.SpriteManager;
import util.CollisionCondition;

import java.awt.*;
import java.awt.image.BufferedImage;

public class IceWall extends Tile {
    public static int ICE_ACCELERATION = 8;
    public IceWall(int x, int y, int width, int height, Id id, BufferedImage bufferedImage) {
        super(x, y, width, height, id);
        this.bufferedImage = bufferedImage;
        boundsRectangle = new Rectangle(x, y, width, height);
    }

    @Override
    public void paint(Graphics g) {
        if(id == Id.icewall1) {
            g.drawImage(bufferedImage, x,y,
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

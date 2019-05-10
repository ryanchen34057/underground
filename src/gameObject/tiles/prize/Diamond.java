package gameObject.tiles.prize;

import UI.Game;
import audio.SoundEffectPlayer;
import enums.Direction;
import enums.Id;
import gameObject.ICollidable;
import gameObject.tiles.Tile;
import graphics.FrameManager;
import graphics.SpriteManager;
import util.CollisionCondition;

import java.awt.*;

public class Diamond extends Prize {
    private int frameDelay, frame;
    private static final int RESPAWN_RATE = 300;
    private int count;
    private boolean isEaten;
    public Diamond(int x, int y, int width, int height, int point, Id id) {
        super(x, y, width, height, point, id);
        frameDelay = 0;
        frame = 0;
        boundsRectangle = new Rectangle(x+10, y+10, width-20,height-20);
        isEaten = false;
        count = 0;
    }

    public Diamond getInstance() { return new Diamond(x, y, width, height, 0, Id.diamond); }

    public boolean isEaten() {
        return isEaten;
    }

    @Override
    public void paint(Graphics g) {
        if(isEaten) {
            g.drawImage(SpriteManager.emptyDiamond.getBufferedImage(), super.getX(), super.getY(),
                    super.getWidth(), super.getHeight(), null);
        }
        else {
            g.drawImage(FrameManager.getDiamondFrame()[frame].getBufferedImage(), super.getX(), super.getY(),
                    super.getWidth(), super.getHeight(), null);
        }
        if(Game.debugMode) {
            g.setColor(Color.YELLOW);
            g.drawRect(x+10, y+10, width-20,height-20);
        }
    }

    @Override
    public void update() {
        if(isEaten) {
            count++;
            if(count >= RESPAWN_RATE) {
                isEaten = false;
                count = 0;
            }
        }
        else {
            frameDelay++;
            if (frameDelay >= 5/Game.UpdatesRatio) {
                frame++;
                if (frame >= FrameManager.getEmeralFrame().length) {
                    frame = 0;
                }
                frameDelay = 0;
            }
        }

    }

    @Override
    public void die() {
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
    }
}

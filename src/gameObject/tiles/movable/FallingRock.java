package gameObject.tiles.movable;

import UI.Game;
import effects.LandingEffect;
import enums.Direction;
import enums.Id;
import gameObject.ICollidable;
import gameObject.character.Player;
import gameObject.tiles.Tile;
import gameStates.GameState;
import util.CollisionCondition;
import util.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FallingRock extends Tile {
    private int originalX;
    private int originalY;
    private boolean isFalling;
    private boolean isShaking;
    private int intensity;
    private int shakingCounter;
    private final int SHAKING_LENGTH = 30;
    private final int FALLING_SPEED = 25;
    private boolean fallen;

    public FallingRock(int x, int y, int width, int height, boolean breakable, Id id, BufferedImage bufferedImage) {
        super(x, y, width, height, breakable, id);
        originalX = x;
        originalY = y;
        isFalling = false;
        isShaking = false;
        fallen = false;
        this.bufferedImage = bufferedImage;
        intensity = 10;
        shakingCounter = 0;
    }

    public void setShaking(boolean shaking) {
        isShaking = shaking;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(bufferedImage, x, y, width, height, null);
        if(Game.debugMode) {
            g.setColor(Color.GREEN);
            g.drawRect(x+11, y, width-30,height);
        }
    }

    @Override
    public void update() {
        if(isShaking) {
            x += Math.random() * intensity - intensity/ 2;
            y += Math.random() * intensity - intensity/ 2;
            shakingCounter++;
        }

        if(shakingCounter == SHAKING_LENGTH) {
            x = originalX;
            shakingCounter = 0;
            isFalling = true;
            isShaking = false;

        }
        if(isFalling) {
            y += FALLING_SPEED;
        }
        Tile t;
        for (int i = 0; i < Handler.tiles.size(); i++) {
            t = Handler.tiles.get(i);
            if(!inTheScreen(t)) { continue; }
            if(t.getBounds() != null && collidesWith(t, Tile::getBounds)){
                handleCollision(t, null);
            }
        }
    }

    public boolean isFalling() {
        return isFalling;
    }

    public boolean isFallen() {
        return fallen;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x+11, y, width-30, height);
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
        if(collisionCondition.checkCollision((Tile) other).intersects(getBoundsBottom())) {
            return true;
        }
        return false;
    }

    @Override
    public void handleCollision(ICollidable other, Direction direction) {
        reactToCollision(other, direction);
    }

    @Override
    public void reactToCollision(ICollidable other, Direction direction) {
        if(!(other instanceof Player)) {
            if(isFalling) {
                y = ((Tile) other).getY() - height;
            }
            isFalling = false;
            if(!fallen) {
                Handler.addEffect(LandingEffect.getInstance(this));
                GameState.cam.setShaking(true, 20, 10);
            }
            fallen = true;
        }
    }
}

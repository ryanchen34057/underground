package gameObject.tiles.movable;

import UI.Game;
import audio.SoundEffectPlayer;
import effects.Effect;
import effects.LandingEffect;
import enums.Direction;
import enums.Id;
import gameObject.ICollidable;
import gameObject.character.Player;
import gameObject.tiles.Tile;
import gameObject.tiles.prize.Prize;
import util.CollisionCondition;
import java.awt.*;
import java.awt.image.BufferedImage;

public class FallingRock extends Tile {
    private int originalX;
    private int originalY;
    private boolean isFalling;
    private boolean isShaking;
    private int intensity;
    private int shakingCounter;
    private final float SHAKING_LENGTH = 0.6f * Game.UPDATES;
    private final float FALLING_SPEED = 30f * Game.UpdatesRatio * Game.heightRatio;
    private boolean fallen;
    private Effect currentEffect;
    private boolean onTheGround;

    public FallingRock(int x, int y, int width, int height, Id id, BufferedImage bufferedImage) {
        super(x, y, width, height, id);
        originalX = x;
        originalY = y;
        isFalling = false;
        isShaking = false;
        onTheGround = false;
        fallen = false;
        this.bufferedImage = bufferedImage;
        intensity = (int)(20 * Game.UpdatesRatio * Game.widthRatio);
        shakingCounter = 0;
        currentEffect = null;
    }

    public void setShaking(boolean shaking) {
        isShaking = shaking;
    }

    public void setOnTheGround(boolean onTheGround) {
        this.onTheGround = onTheGround;
    }

    public boolean isOnTheGround() {
        return onTheGround;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(bufferedImage, x, y, width, height, null);
        if(Game.debugMode) {
            g.drawRect(x+(int)(width*0.08), y, width-2*(int)(width*0.08), height);
            g.setColor(Color.RED);
            g.drawRect(x+width/3, y + height, (int)(width-width/1.5),1);
            g.setColor(Color.GREEN);
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
    }

    public boolean isFalling() {
        return isFalling;
    }

    public boolean isFallen() {
        return fallen;
    }

    public void setFalling(boolean falling) {
        isFalling = falling;
    }

    public Effect getCurrentEffect() {
        return currentEffect;
    }

    public void setCurrentEffect(Effect currentEffect) {
        this.currentEffect = currentEffect;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x+(int)(width*0.08), y, width-2*(int)(width*0.08), height);
    }

    @Override
    public  Rectangle getBoundsTop() {
        return new Rectangle(x+10, y, width-20,1 );
    }
    @Override
    public  Rectangle getBoundsBottom() {
        return new Rectangle(x+width/4, y + height, width-width/2,1);
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
        if(!(other instanceof Player) && !(other instanceof Prize)) {
            if(isFalling) {
                SoundEffectPlayer.playSoundEffect("FallingRockHit");
                y = (int)((Tile) other).getBounds().getY() - height;
                isFalling = false;
            }
            if(!fallen) {
                SoundEffectPlayer.playSoundEffect("FallingRockShaking");
                currentEffect = LandingEffect.getInstance(this);
                fallen = true;
            }
        }
    }
}

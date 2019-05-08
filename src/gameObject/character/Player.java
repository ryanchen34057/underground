package gameObject.character;

import UI.Game;
import audio.SoundEffectPlayer;
import effects.LandingEffect;
import enums.Direction;
import enums.Id;
import gameObject.ICollidable;
import gameObject.tiles.movable.FallingRock;
import gameObject.tiles.portal.Portal;
import gameObject.tiles.trap.Spring;
import gameObject.tiles.wall.IceWall;
import graphics.FrameManager;
import input.Input;
import gameObject.tiles.Tile;
import states.PlayerState;
import states.Sliding;
import util.CollisionCondition;


import java.awt.*;

import static enums.Direction.*;


public class Player extends Entity {

    private int frame;
    private int frameDelay;
    private float friction;

    //Stats
    public static final int WIDTH = (int) (96 * Game.widthRatio);
    public static final int HEIGHT = (int) (96 * Game.widthRatio);
    private final int STAMINA = Game.UPDATES *4;

    // Running
    // 8f * Game.UpdatesRatio * Game.widthRatio
    public static final float STEP = 8 * Game.UpdatesRatio * Game.widthRatio;

    // StandingJump
    // 18f * Game.UpdatesRatio  *
    public static final float STANDINGJUMPING_GRAVITY = 18f * Game.UpdatesRatio * Game.heightRatio;
    public static final float STANDINGJUMPING_VELX_OFFSET = 2.5f * Game.UpdatesRatio * Game.widthRatio;
    public static final float STANDINGJUMPING_GRAVITY_OFFSET = (STANDINGJUMPING_GRAVITY / (18f/0.8f)) * Game.UpdatesRatio;

    // RunningJump
    public static final float RUNNINGJUMPING_GRAVITY = 17f * Game.UpdatesRatio *  Game.heightRatio;
    public static final float RUNNINGJUMPING_GRAVITY_OFFSET = (RUNNINGJUMPING_GRAVITY / 17f/1.2f) * Game.UpdatesRatio;
    public static final float RUNNINGJUMPING_STEP = 6.5f * Game.UpdatesRatio * Game.widthRatio;


    // Dash
    public static final float DASH_SPEED = 10 * Game.UpdatesRatio * Game.widthRatio;
    public static final float DASH_TIMER = (Game.UPDATES /1000f) * Game.UPDATES * (1.5f/3.6f);
    public static final float ICESKATING_SPEED = 15 * Game.UpdatesRatio * Game.widthRatio;
    public float currentDashSpeed;
    public double currentDashTimer;
    public static final float DASH_SPEED_BUMP = 0.005f * Game.UpdatesRatio * Game.widthRatio;

    // DashJumping
    public static final float DASHJUMPING_GRAVITY = 17 * Game.UpdatesRatio * Game.heightRatio;
    public static final float DASHJUMPING_GRAVITY_OFFSET = (DASHJUMPING_GRAVITY / (17f/0.8f)) * Game.UpdatesRatio;

    // Sliding and Bouncing
    public static final float BOUNCING_RANGE = 3.2f * Game.UpdatesRatio * Game.widthRatio;
    public static final float BOUNCING_GRAVITY = 18 * Game.UpdatesRatio *  Game.heightRatio;
    public static final float BOUNCING_GRAVITY_OFFSET = (BOUNCING_GRAVITY / (17f/0.6f)) * Game.UpdatesRatio;

    // Falling
    public static final float FALLING_GRAVITY_VEL = 0.3f * (Game.UpdatesRatio) *  Game.heightRatio;
    public static final float FALLING_VELX = 5.3f * Game.UpdatesRatio *  Game.widthRatio;

    // Vertical Dashing
    public static final float VERTICALDASHING_SPEED = 9f * Game.UpdatesRatio * Game.heightRatio;
    public static final float VERTICALDASHING_TIMER = (Game.UPDATES /1000f) * Game.UPDATES * (1.2f/3.6f);
    public static final float VERTICALDASHING_VELX = 9 * Game.UpdatesRatio * Game.widthRatio;

    // State
    private boolean isJumped;
    private boolean isOnTheWall;
    private boolean isGoaled;
    private boolean keyLocked;

    public Player(int width, int height, Id id) {
        super(width, height, id);
        currentState = PlayerState.idle;
        //velX = 5 * Game.widthRatio;
        frameDelay = 0;
        frame = 0;
        friction = 0;
        fatigue = 0;
        prevState = null;
        currentEffect = null;
        isTired = false;
        isGoaled = false;
        isOnTheGround = false;
        isJumped = false;
        isOnTheWall = false;
        keyLocked = false;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void paint(Graphics g) {
        if(!isDead) {
            if (facing == -1) {
                g.drawImage(FrameManager.getPlayerMoveFrame(currentState)[frame + 4].getBufferedImage(), x, y,
                        WIDTH, HEIGHT, null);
            } else {
                g.drawImage(FrameManager.getPlayerMoveFrame(currentState)[frame].getBufferedImage(), x, y,
                        WIDTH, HEIGHT, null);
            }
            if(Game.debugMode) {
                g.setColor(Color.BLUE);
                //g.drawRect(x+ (int)(WIDTH /3.2), y, WIDTH - (int)(WIDTH /1.8), HEIGHT);
                //TOP
                g.drawRect(x + (int)(WIDTH/2.5), y, WIDTH - (int)(WIDTH /1.4), (int)(15*Game.widthRatio));
                //BOTTOM
                g.drawRect(x + (int)(WIDTH/2.5), y+HEIGHT - (int)(15*Game.widthRatio), WIDTH - (int)(WIDTH /1.4), (int)(15*Game.widthRatio));
                //LEFT
                g.drawRect(x + (WIDTH/3) , y+HEIGHT/6, (int)(20*Game.widthRatio), HEIGHT-HEIGHT/3);
                //RIGHT
                g.drawRect(x + (WIDTH/3) + (int)(getBounds().width/1.2) , y+HEIGHT/6, (int)(20*Game.widthRatio), HEIGHT-HEIGHT/3);
                // Original Size
                g.setColor(Color.RED);
                g.drawRect(x, y, WIDTH, HEIGHT);
            }
        }
    }


    @Override
    // Collision test
    public Rectangle getBounds() { return new Rectangle(x + (int)(WIDTH/2.5), y, WIDTH - (int)(WIDTH /1.4), (int)(15*Game.widthRatio)); }

    public Rectangle getBoundsTop() {
        return new Rectangle(x + (int)(WIDTH/2.5), y, WIDTH - (int)(WIDTH /1.4), (int)(15*Game.widthRatio));
    }
    public Rectangle getBoundsBottom() {
        return new Rectangle(x + (int)(WIDTH/2.5), y+HEIGHT - (int)(15*Game.widthRatio), WIDTH - (int)(WIDTH /1.4), (int)(15*Game.widthRatio));
    }
    public Rectangle getBoundsLeft() {
        return new Rectangle(x + (WIDTH/3) , y+HEIGHT/6, (int)(20*Game.widthRatio), HEIGHT-HEIGHT/3);
    }
    public Rectangle getBoundsRight() {
        return new Rectangle(x + (WIDTH/3) + (int)(getBounds().width/1.2) , y+HEIGHT/6, (int)(20*Game.widthRatio), HEIGHT-HEIGHT/3);
    }

    @Override
    public void update() {
        if(!isDead) {
//            if (prevState != currentState) {
//                prevState = currentState;
//                System.out.println(prevState);
//            }
            x += Math.round(velX);
            y += Math.round(velY);

            currentState.update(this);
            frameSpeedManager();
        }
        else {
            SoundEffectPlayer.playSoundEffect("Death");
            // Don't move if dead
            velX = 0;
            velY = 0;
        }

    }

    // Getter and Setter
    public int getSTAMINA() {
        return STAMINA;
    }
    public int getFatigue() {
        return fatigue;
    }
    public void accumulateFatigue() {
        this.fatigue++;
    }
    public boolean isOnTheGround() {
        return isOnTheGround;
    }
    public boolean isTired() {
        return isTired;
    }
    public void setTired(boolean tired) {
        isTired = tired;
    }
    public boolean isJumped() {
        return isJumped;
    }
    public void setJumped(boolean jumped) {
        isJumped = jumped;
    }
    public boolean isOnTheWall() {
        return isOnTheWall;
    }
    public float getFriction() {
        return friction;
    }
    public void setFriction(float friction) {
        this.friction = friction;
    }
    public boolean isGoaled() {
        return isGoaled;
    }

    public void setOnTheWall(boolean onTheWall) {
        isOnTheWall = onTheWall;
    }

    // Handle keyInput from player
    public void handleKeyInput() {
        currentState.handleKeyInput(this, Input.keys);
        if(!keyLocked) {
            if(Input.keys.get(6).down) {
                Game.infinityMode = !Game.infinityMode;
                keyLocked = true;
            }
        }
        if(!Input.keys.get(6).down) {
            keyLocked = false;
        }

    }

    // Determine the frame to use depending on the currentState
    private void frameSpeedManager() {
        if(currentState == PlayerState.standing) {
            frameDelay++;
            if (frameDelay >= Game.UPDATES *0.4) {
                frame++;
                if (frame >= FrameManager.playerMoveFrame.length / 2) {
                    frame = 0;
                }
                frameDelay = 0;
            }
        }
        else if(currentState == PlayerState.iceSkating || currentState == PlayerState.springHorizontal) {
            frameDelay++;
            if (frameDelay >= 30/Game.UpdatesRatio) {
                frame++;
                if (frame >= FrameManager.playerMoveFrame.length / 2) {
                    frame = 0;
                }
                frameDelay = 0;
            }
        }
        else if(currentState == PlayerState.dashing) {
            frameDelay++;
            if (frameDelay >= 8/Game.UpdatesRatio) {
                frame++;
                if (frame >= FrameManager.playerMoveFrame.length / 2) {
                    frame = 0;
                }
                frameDelay = 0;
            }
        }
        else if(currentState == PlayerState.verticalDashing || currentState == PlayerState.dashingInTheAir) {
            frameDelay++;
            if (frameDelay >= 3/Game.UpdatesRatio) {
                frame++;
                if (frame >= FrameManager.playerMoveFrame.length / 2) {
                    frame = 0;
                }
                frameDelay = 0;
            }
        }
        else {
            frameDelay++;
            if (frameDelay >= 4/Game.UpdatesRatio) {
                frame++;
                if (frame >= FrameManager.playerMoveFrame.length / 2) {
                    frame = 0;
                }
                frameDelay = 0;
            }
        }
    }

    @Override
    public void die() {
        if(!Game.infinityMode) isDead = true;
    }

    public Direction checkCollisionBounds(Tile t, CollisionCondition collisionCondition) {
        if(t instanceof Portal) {
            if(getBounds().intersects(t.getBounds())) {
                t.die();
            }
        }
        if(getBoundsTop().intersects(collisionCondition.checkCollision(t))) {
               return Direction.TOP;
        }

        if(getBoundsBottom().intersects(collisionCondition.checkCollision(t))) {
            return Direction.BOTTOM;
        }

        if(getBoundsLeft().intersects(collisionCondition.checkCollision(t))) {
            return Direction.LEFT;
        }
        if(getBoundsRight().intersects(collisionCondition.checkCollision(t))) {
            return Direction.RIGHT;
        }

        return null;
    }

    public boolean isInTheAir() {
        return currentState == PlayerState.standingJumping ||
                currentState == PlayerState.runningJumping ||
                currentState == PlayerState.dashJumping ||
                currentState == PlayerState.falling ||
                currentState == PlayerState.sliding ||
                currentState == PlayerState.bouncing ||
                currentState == PlayerState.dashingInTheAir ||
                currentState == PlayerState.verticalDashing;
    }

    @Override
    public boolean collidesWith(ICollidable other, CollisionCondition collisionCondition) {
        return getBounds().intersects(collisionCondition.checkCollision((Tile) other));
    }

    @Override
    public void handleCollision(ICollidable other, Direction direction) {
        if(direction == null) {
            return;
        }
        if(!isDead) {
            this.reactToCollision(other, direction);
        }
        other.reactToCollision(this, direction);

    }

    @Override
    public void reactToCollision(ICollidable other, Direction direction) {
        Tile t = (Tile) other;
        switch (t.getId()) {
            case diamond:
                if(isTired) {
                    isTired = false;
                }
                break;
            case hole:
                ifHitWall(t, direction);
                die();
                break;
            case fallingRock:
                if(((FallingRock) t).isFalling()) {
                    die();
                }
                ifHitWall(t, direction);
                break;
            case upwardSpike:
                if(direction == Direction.BOTTOM) {
//                    System.out.println("upward");
                    die();
                }
                ifHitWall(t, direction);
                break;
            case downwardSpike:
                if(direction == Direction.TOP) {
//                    System.out.println("downward");
                    die();
                }
                ifHitWall(t, direction);
                break;
            case leftwardSpike:
                if(direction == Direction.RIGHT) {
//                    System.out.println("leftward");
                    die();
                }
                ifHitWall(t, direction);
                break;
            case rightwardSpike:
                if(direction == Direction.LEFT) {
//                    System.out.println("rightward");
                    die();
                }
                ifHitWall(t, direction);
                break;
            case breakableWall:
            case icewall1:
            case vanishingRock:
            case spring:
            case halfHeightWall:
            case halfWidthWall:
            case wall:
                ifHitWall(t, direction);
                break;
            case bluePortal:
                SoundEffectPlayer.playSoundEffect("Portal");
                if(((Portal)t).getDirection() == Direction.LEFT) {
                    t.die();
                }
                else {
                    t.die();
                }

                break;
            case purplePortal:
                isGoaled = true;
                break;
        }
    }

    private void ifHitWall(Tile t, Direction direction) {
        Rectangle collisionRect = t.getBounds();
        switch (direction) {
            case TOP:
                //System.out.println("TOP");
                // If bump into top down spring
                if(t instanceof Spring && ((Spring) t).getDirection() == DOWN) {
                    gravity = STANDINGJUMPING_GRAVITY;
                    y = collisionRect.y + (int)collisionRect.getHeight();
                    ((Spring) t).setStepOn(true);
                    SoundEffectPlayer.playSoundEffect("SpringJumping");
                    isJumped = true;
                    currentState = PlayerState.falling;
                }
                else {
                    gravity = 0;
                    velY = 0;
                    y = collisionRect.y + (int)collisionRect.getHeight();
                    currentState = PlayerState.falling;
                }
                break;
            case BOTTOM:
                if(t instanceof Spring && ((Spring) t).getDirection() == UP) {
                    y = collisionRect.y - HEIGHT;
                    ((Spring) t).setStepOn(true);
                    SoundEffectPlayer.playSoundEffect("SpringJumping");
                    isJumped = true;
                    isTired = false;
                    gravity = (int)(STANDINGJUMPING_GRAVITY*1.5);
                    currentState = PlayerState.standingJumping;
                }
                //System.out.println("BOTTOM");
                else if(currentState == PlayerState.falling || currentState == PlayerState.sliding
                        || currentState == PlayerState.verticalDashing) {
                    velY = 0;
                    gravity = 0;
                    friction = 0;
                    y = collisionRect.y - HEIGHT+1;
                    currentState = PlayerState.standing;
                    SoundEffectPlayer.playSoundEffect("Landing");
                    // Don't create landing effect when died
                    if(!isDead()) {
                        currentEffect = LandingEffect.getInstance(this);
                    }
                }
                // On the ice is true when player is not running or iceSkating
                if(t instanceof IceWall && (currentState == PlayerState.running || currentState == PlayerState.iceSkating)) {
                    isOnTheIce = true;
                }
                else {
                    // If slide out of the ice
                    if(currentState == PlayerState.iceSkating) {
                        currentState = PlayerState.standing;
                    }
                    isOnTheIce = false;
                }
                isOnTheGround = true;
                isOnTheWall = false;
                fatigue = 0;
                break;
            case LEFT:
                //System.out.println("LEFT");
                if(!(t instanceof Spring)) {
                    velX = 0;
                    x = collisionRect.x + collisionRect.width - (getBoundsLeft().x - x);
                }
                if(t instanceof Spring && ((Spring) t).getDirection() == RIGHT) {
                    x = collisionRect.x + collisionRect.width - (getBoundsLeft().x - x);
                    currentState = PlayerState.springHorizontal;
                    currentDashSpeed = (int)(DASH_SPEED*1.5);
                    currentDashTimer = DASH_TIMER/2;
                    SoundEffectPlayer.playSoundEffect("SpringJumping");
                    ((Spring) t).setStepOn(true);
                }
                if(t.getId() != Id.icewall1 && currentState == PlayerState.falling && Input.keys.get(2).down && fatigue < STAMINA) {
                    currentState = PlayerState.sliding;
                    SoundEffectPlayer.playSoundEffect("Landing");
                    isOnTheWall = true;
                }
                if(currentState == PlayerState.iceSkating && velX == 0) {
                    currentState = PlayerState.standing;
                }
                break;
            case RIGHT:
                //System.out.println("RIGHT");
                if(!(t instanceof Spring)) {
                    velX = 0;
                    x = collisionRect.x - width + ((x+width)-(getBoundsRight().x+getBoundsRight().width));
                }
                if(t instanceof Spring && ((Spring) t).getDirection() == Direction.LEFT) {
                    x = collisionRect.x - width + ((x+width)-(getBoundsRight().x+getBoundsRight().width));
                    currentState = PlayerState.springHorizontal;
                    currentDashSpeed = (int)(DASH_SPEED*1.5);
                    currentDashTimer = DASH_TIMER/2;
                    SoundEffectPlayer.playSoundEffect("SpringJumping");
                    ((Spring) t).setStepOn(true);
                }
                // If player slide on the wall
                if(t.getId() != Id.icewall1 && currentState == PlayerState.falling
                        && Input.keys.get(3).down && fatigue < STAMINA) {
                    currentState = PlayerState.sliding;
                    SoundEffectPlayer.playSoundEffect("Landing");
                    isOnTheWall = true;
                }
                if(currentState == PlayerState.iceSkating && velX == 0) {
                    currentState = PlayerState.standing;
                }
                break;
        }
    }
}
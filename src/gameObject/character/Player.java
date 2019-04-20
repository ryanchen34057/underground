package gameObject.character;

import UI.Game;
import effects.LandingEffect;
import enums.Direction;
import enums.Id;
import gameObject.ICollidable;
import graphics.FrameManager;
import input.Input;
import gameObject.tiles.Tile;
import org.omg.PortableInterceptor.INACTIVE;
import states.PlayerState;
import util.CollisionCondition;


import java.awt.*;


public class Player extends Entity {

    private int frame;
    private int frameDelay;

    //Stats
    public static final int WIDTH = 96;
    public static final int HEIGHT = 96;
    private final int STAMINA = 100;

    // Running
    public static final int STEP = 4;

    // StandingJump
    public static final float STANDINGJUMPING_GRAVITY = 18f;
    public static final float STANDINGJUMPING_VELX_OFFSET = 1.5f;
    public static final float STANDINGJUMPING_GRAVITY_OFFSET = 1.1f;

    // RunningJump
    public static final float RUNNINGJUMPING_GRAVITY = 16;
    public static final float RUNNINGJUMPING_GRAVITY_OFFSET = 1f;
    public static final int RUNNINGJUMPING_STEP = 5;


    // Dash
    public static final int DASH_SPEED = 10;
    public static final float DASH_TIMER = 1.5f;
    public float currentDashSpeed = 10;
    public static final float DASH_SPEED_BUMP = 0.1f;

    // DashJumping
    public static final float DASHJUMPING_GRAVITY = 16;
    public static final float DASHJUMPING_GRAVITY_OFFSET = 0.8f;

    // Sliding and Bouncing
    public static final float BOUNCING_RANGE = 3.0f;
    public static final float BOUNCING_GRAVITY_OFFSET = 0.6f;
    public static final float BOUNCING_GRAVITY = 13;

    // Falling
    public static final float FALLING_GRAVITY_VEL = 0.5f;
    public static final int FALLING_VELX = 6;

    // Vertical Dashing
    public static final float VERTICALDASHING_SPEED = 8;
    public static final float VERTICALDASHING_TIMER = 1.2f;
    public static final float VERTICALDASHING_VELX = 8;

    public Player(int x, int y, int width, int height, Id id) {
        super(x, y, width, height, id);
        velX = 5;
        frameDelay = 0;
        frame = 0;
        isOnTheGround = false;
        prevState = null;
        currentState = PlayerState.idle;
        currentEffect = null;
        fatigue = 0;
        isTired = false;
    }

    @Override
    public void paint(Graphics g) {
        if (facing == -1) {
            g.drawImage(FrameManager.getPlayerMoveFrame(currentState)[frame + 4].getBufferedImage(), x, y,
                    WIDTH, HEIGHT, null);
        } else {
            g.drawImage(FrameManager.getPlayerMoveFrame(currentState)[frame].getBufferedImage(), x, y,
                    WIDTH, HEIGHT, null);
        }
        if(Game.debugMode) {
            g.setColor(Color.BLUE);
            g.drawRect(x+ WIDTH /4, y, WIDTH - WIDTH /2, HEIGHT);
            //TOP
            g.drawRect(x+40, y, WIDTH -80, 1);
            //BOTTOM
            g.drawRect(x+40, y+ HEIGHT, WIDTH -80, 1);
            //LEFT
            g.drawRect(x+25, y+20, 1, WIDTH -40);
            //RIGHT
            g.drawRect(x+ WIDTH -30, y+20,1, WIDTH -40);
        }

    }

    @Override
    public void update() {
        if(Game.debugMode) {
            if (prevState != currentState) {
                prevState = currentState;
                System.out.println(prevState);
            }
        }

        // Predict collision
        x += velX;
        y += velY;

        handleKeyInput();
        currentState.update(this);
        isOnTheGround = false;
        Tile t;
        for (int i = 0; i < Game.handler.getTiles().size(); i++) {
            t = Game.handler.getTiles().get(i);
            if(t.getBounds() != null) {
                handleCollision(t, checkCollisionBounds(t, Tile::getBounds));
            }
        }

        //Check if on the ground
        if(isOnTheGroundCondition()) {
            currentState = PlayerState.falling;
        }

        if(currentState == PlayerState.standing) {
            frameDelay++;
            if (frameDelay >= 30) {
                frame++;
                if (frame >= FrameManager.playerMoveFrame.length / 2) {
                    frame = 0;
                }
                frameDelay = 0;
            }
        }
        else if(currentState == PlayerState.dashing || currentState == PlayerState.dashJumping) {
            frameDelay++;
            if (frameDelay >= 4) {
                frame++;
                if (frame >= FrameManager.playerMoveFrame.length / 2) {
                    frame = 0;
                }
                frameDelay = 0;
            }
        }
        else {
            frameDelay++;
            if (frameDelay >= 5) {
                frame++;
                if (frame >= FrameManager.playerMoveFrame.length / 2) {
                    frame = 0;
                }
                frameDelay = 0;
            }
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

    public void handleKeyInput() {
        currentState.handleKeyInput(this, Input.keys);
    }

    @Override
    // Collision test
    public Rectangle getBounds() { return new Rectangle(x+ WIDTH /4, y, WIDTH - WIDTH /2, HEIGHT);}
    public Rectangle getBoundsTop() {
        return new Rectangle(getX()+40, getY(), WIDTH -80,1 );
    }
    public Rectangle getBoundsBottom() {
        return new Rectangle(getX()+40, getY()+ HEIGHT, WIDTH -80,1 );
    }
    public Rectangle getBoundsLeft() {
        return new Rectangle(getX()+25, getY()+20, 1, HEIGHT -40 );
    }
    public Rectangle getBoundsRight() {
        return new Rectangle(getX()+ WIDTH -30, getY()+20, 1, HEIGHT -40 );
    }

    @Override
    public void die() {
        isDead = true;
    }

    private Direction checkCollisionBounds(Tile t, CollisionCondition collisionCondition) {
        if(getBoundsTop().intersects(collisionCondition.checkCollision(t))) return Direction.TOP;
        else if(getBoundsBottom().intersects(collisionCondition.checkCollision(t))) return Direction.BOTTOM;
        else if(getBoundsLeft().intersects(collisionCondition.checkCollision(t))) return Direction.LEFT;
        else if(getBoundsRight().intersects(collisionCondition.checkCollision(t))) return Direction.RIGHT;
        return null;
    }

    private boolean isOnTheGroundCondition() {
        return !isOnTheGround &&
                currentState != PlayerState.standingJumping &&
                currentState != PlayerState.runningJumping &&
                currentState != PlayerState.dashJumping &&
                currentState != PlayerState.falling &&
                currentState != PlayerState.standing &&
                currentState != PlayerState.sliding &&
                currentState != PlayerState.bouncing &&
                currentState != PlayerState.dashingInTheAir &&
                currentState != PlayerState.verticalDashing;
    }

    @Override
    public Direction collidesWith(ICollidable other, CollisionCondition collisionCondition) {
        Tile t = (Tile) other;
        return checkCollisionBounds(t, collisionCondition);
    }

    @Override
    public void handleCollision(ICollidable other, Direction direction) {
        if(direction == null) {
            return;
        }
        this.reactToCollision(other, direction);
        other.reactToCollision(this, direction);

    }

    @Override
    public void reactToCollision(ICollidable other, Direction direction) {
        Tile t = (Tile) other;
        switch (t.getId()) {
            case upwardSpike:
                if(direction == Direction.BOTTOM) {
                    die();
                }
            case downwardSpike:
                if(direction == Direction.TOP) {
                    die();
                }
            case leftwardSpike:
                if(direction == Direction.RIGHT) {
                    die();
                }
            case rightwardSpike:
                if(direction == Direction.LEFT) {
                    die();
                }
            case breakableWall:
            case wall:
                ifHitWall(t, direction);
                break;
            case coin:
                break;
            case bluePortal:
                velX += 3;
                break;

        }
    }

    private void ifHitWall(Tile t, Direction direction) {
        switch (direction) {
            case TOP:
                gravity = 0;
                velY = 0;
                y = t.getY() + t.getHeight();
                currentState = PlayerState.falling;
                break;
            case BOTTOM:
                if(currentState == PlayerState.falling || currentState == PlayerState.sliding
                        || currentState == PlayerState.verticalDashing) {
                    velY = 0;
                    gravity = 0;
                    y = t.getY() - getHeight();
                    currentState = PlayerState.standing;
                    if(!isDead()) {
                        currentEffect = LandingEffect.getInstance(this);
                    }
                }
                isOnTheGround = true;
                fatigue = 0;
                break;
            case LEFT:
                velX = 0;
                x = t.getX() + t.getWidth() - 25;
                if(currentState == PlayerState.falling &&
                        Input.keys.get(2).down && fatigue < STAMINA) {
                    currentState = PlayerState.sliding;
                }
                break;
            case RIGHT:
                velX = 0;
                x = t.getX() - getWidth() + 28;
                if(currentState == PlayerState.falling&& Input.keys.get(3).down && fatigue < STAMINA) {
                    currentState = PlayerState.sliding;
                }
                break;
        }
    }
}
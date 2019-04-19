package character;

import UI.Game;
import effects.DeathParticle;
import effects.LandingEffect;
import enums.Id;
import graphics.FrameManager;
import input.Input;
import tiles.Tile;
import states.PlayerState;
import states.StateMachine;
import tiles.trap.Spike;
import util.Handler;
import util.TileCollisionCondition;


import java.awt.*;

public class Player extends Entity {

    private int frame;
    private int frameDelay;

    //Stats
    public static final int WIDTH = 96;
    public static final int HEIGHT = 96;
    private final int STAMINA = 150;

    // Running
    public static final int STEP = 5;

    // StandingJump
    public static final float STANDINGJUMPING_GRAVITY = 18;
    public static final float STANDINGJUMPING_VELX_OFFSET = 1.5f;
    public static final float STANDINGJUMPING_GRAVITY_OFFSET = 1.1f;

    // RunningJump
    public static final float RUNNINGJUMPING_GRAVITY = 16;
    public static final float RUNNINGJUMPING_GRAVITY_OFFSET = 1f;
    public static final int RUNNINGJUMPING_STEP = 5;


    // Dash
    public static final int DASH_SPEED = 10;
    public static final float DASH_TIMER = 1.5f;
    public float CURRENT_DASH_SPEED = 10;
    public static final float DASH_SPEED_BUMP = 0.1f;

    // DashJumping
    public static final float DASHJUMPING_GRAVITY = 16;
    public static final float DASHJUMPING_GRAVITY_OFFSET = 0.8f;

    // Sliding and Bouncing
    public static final float BOUNCING_RANGE = 4.0f;
    public static final float BOUNCING_GRAVITY_OFFSET = 0.6f;
    public static final float BOUNCING_GRAVITY = 13;

    // Falling
    public static final float FALLING_GRAVITY_VEL = 0.5f;
    public static final int FALLING_VELX = 6;

    // Vertical Dashing
    public static final float VERTICAL_DASH_SPEED = 8;
    public static final float VERTICAL_DASH_TIMER = 1.2f;
    public static final float VERTICAL_DASHING_VELX = 8;


    // State
    public  StateMachine currentState;
    public StateMachine prevState;
    public static boolean isOnTheGround;
    public int fatigue;
    public static boolean isTired;
    private boolean isDead;

    public Player(int x, int y, int WIDTH, int HEIGHT, Id id) {
        super(x, y, WIDTH, HEIGHT, id);
        velX = 5;
        frameDelay = 0;
        frame = 0;
        isOnTheGround = false;
        prevState = null;
        currentState = PlayerState.idle;
        fatigue = 0;
        isTired = false;
        isDead = false;
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
        for (int i = 0; i < Handler.tiles.size(); i++) {
            t = Handler.tiles.get(i);
            // Spike collision test
            if(t instanceof Spike) {
                checkHitTrap(t);
            }
            //Prize
            if (t.getId() == Id.coin) {
                if (checkGetPrize(t)) {
                    Handler.tiles.remove(t);
                }
            }

            //Wall collision test
            if (ifHitWall(t)) {
                if (checkCollisionTop(t, Tile::getBounds)) {
//                    System.out.println("TOP");
                    gravity = 0;
                    velY = 0;
                    y = t.getY() + t.getHeight();
                    currentState = PlayerState.falling;

                }
                if (checkCollisionBottom(t, Tile::getBounds)) {
                    //System.out.println("BOTTOM");
                    if(currentState == PlayerState.falling || currentState == PlayerState.sliding
                            || currentState == PlayerState.verticalDashing) {
                        velY = 0;
                        gravity = 0;
                        y = t.getY() - getHeight();
                        currentState = PlayerState.standing;
                        Handler.addObject(LandingEffect.getInstance(this));
                    }
                    isOnTheGround = true;
                    fatigue = 0;
                }
                if (checkCollisionLeft(t, Tile::getBounds)) {
//                    System.out.println("LEFT");
                    velX = 0;
                    x = t.getX() + t.getWidth() - 25;
                    if(currentState == PlayerState.falling &&
                            Input.keys.get(2).down && fatigue < STAMINA) {
                        currentState = PlayerState.sliding;
                    }
                }
                if (checkCollisionRight(t, Tile::getBounds)) {
//                    System.out.println("RIGHT");
                    velX = 0;
                    x = t.getX() - getWidth() + 28;
                    if(currentState == PlayerState.falling&& Input.keys.get(3).down && fatigue < STAMINA) {
                        currentState = PlayerState.sliding;
                    }
                }
            }

            if(t.getId() == Id.bluePortal) {
                if(checkCollisionBounds(t, Tile::getBounds)) {
                    velX += 3;
                }
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

    public int getSTAMINA() {
        return STAMINA;
    }
    public int getFatigue() {
        return fatigue;
    }
    public void accumulateFatigue() {
        this.fatigue++;
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

    private boolean checkCollisionBounds(Tile t, TileCollisionCondition tileCollisionCondition) {
        return getBounds().intersects(tileCollisionCondition.checkCollision(t));
    }
    private boolean checkCollisionTop(Tile t, TileCollisionCondition tileCollisionCondition) {
        return getBoundsTop().intersects(tileCollisionCondition.checkCollision(t));
    }
    private boolean checkCollisionBottom(Tile t, TileCollisionCondition tileCollisionCondition) {
        return getBoundsBottom().intersects(tileCollisionCondition.checkCollision(t));
    }
    private boolean checkCollisionLeft(Tile t, TileCollisionCondition tileCollisionCondition) {
        return getBoundsLeft().intersects(tileCollisionCondition.checkCollision(t));
    }
    private boolean checkCollisionRight(Tile t, TileCollisionCondition tileCollisionCondition) {
        return getBoundsRight().intersects(tileCollisionCondition.checkCollision(t));
    }
    private void checkHitTrap(Tile t) {
        // Spike collision test
        if(t.getId() == Id.upwardSpike) {
            if ((checkCollisionBounds(t, Tile::getBoundsTop)
                    || (checkCollisionBottom(t, Tile::getBoundsTop)
                    || (checkCollisionLeft(t, Tile::getBoundsTop)
                    || (checkCollisionRight(t, Tile::getBoundsTop)))))) {
                isDead = true;
            }
        }
        else if(t.getId() == Id.downwardSpike) {
            if((checkCollisionBounds(t, Tile::getBoundsBottom)
                    || (checkCollisionBottom(t, Tile::getBoundsBottom)
                    || (checkCollisionLeft(t, Tile::getBoundsBottom)
                    || (checkCollisionRight(t, Tile::getBoundsBottom)))))){
                isDead = true;
            }
        }
        else if(t.getId() == Id.leftwardSpike) {
            if((checkCollisionBounds(t, Tile::getBoundsLeft)
                    || (checkCollisionBottom(t, Tile::getBoundsLeft)
                    || (checkCollisionLeft(t, Tile::getBoundsLeft)
                    || (checkCollisionRight(t, Tile::getBoundsLeft)))))){
                isDead = true;
            }
        }
        else if(t.getId() == Id.rightwardSpike) {
            if((checkCollisionBounds(t, Tile::getBoundsRight)
                    || (checkCollisionBottom(t, Tile::getBoundsRight)
                    || (checkCollisionLeft(t, Tile::getBoundsRight)
                    || (checkCollisionRight(t, Tile::getBoundsRight)))))){
                isDead = true;
            }
        }

        //Check if player died
        if(isDead) {
            if(Handler.particles.size() != 0)
                return;
            for(int i=0;i<8;i++) {
                DeathParticle deathParticle = new DeathParticle(x-(int)velX, y-(int)velY, (int)(DeathParticle.RANGE * Math.cos(i * 45 * 2 * Math.PI / 360) + x-(int)velX),
                        (int)(DeathParticle.RANGE * Math.sin(i * 45 * 2 * Math.PI / 360) + y-(int)velY), 64, 5);
                Handler.addObject(deathParticle);
            }
            Handler.entities.remove(this);
        }
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
    private boolean ifHitWall(Tile t) {
        return t.getId() == Id.wall
                || t.getId() == Id.rightwardSpike
                || t.getId() == Id.leftwardSpike
                || t.getId() == Id.upwardSpike
                || t.getId() == Id.downwardSpike
                || t.getId() == Id.breakableWall;
    }
    private boolean checkGetPrize(Tile p) {
        return checkCollisionBounds(p, Tile::getBounds);
    }

}
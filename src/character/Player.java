package character;

import UI.Game;
import effects.DeathParticle;
import effects.LandingEffect;
import graphics.FrameManager;
import input.Input;
import tiles.Tile;
import tiles.prize.Prize;
import states.PlayerState;
import states.StateMachine;
import tiles.trap.Spike;
import util.Handler;
import util.TileCollisionCondition;


import java.awt.*;

public class Player extends Entity {

    private int frame;
    private int frameDelay;
    public static final int STEP = 5;

    //Stats
    public static final int width = 96;
    public static final int height = 96;
    private final int STAMINA = 150;
    public static final int DASH_SPEED = 10;
    public static final float VERTICAL_DASH_SPEED = 12;
    public static final float DASH_TIMER = 1.5f;
    public static final float VERTICAL_DASH_TIMER = 1.0f;
    public float CURRENT_DASH_SPEED = 12;
    public static final float DASH_SPEED_BUMP = 0.1f;
    public static final float BOUNCING_RANGE = 2.0f;
    public static final float STANDINGJUMPING_GRAVITY = 10.0f;
    public static final float STANDINGJUMPING_VELX_OFFSET = 1.5f;
    public static final float GRAVITY_OFFSET = 0.35f;
    public static final float SLIDING_GRAVITY = 10;
    public static final float FALLING_GRAVITY_VEL = 0.7f;
    public static final float RUNNINGJUMPING_GRAVITY = 10;
    public static final float RUNNINGJUMPING_GRAVITY_OFFSET = 0.35f;
    public static final int FALLING_VELX = 3;
    public static final float DASHJUMPING_GRAVITY_OFFSET = 0.25f;
    public static final float DASHJUMPING_GRAVITY = 8;
    public static final float VERTICAL_DASHING_VELX = 10;


    // State
    public  StateMachine currentState;
    public StateMachine prevState;
    public static boolean isOnTheGround;
    public int fatigue;
    public static boolean isTired;
    private boolean isDead;

    public Player(int x, int y, int width, int height, Id id) {
        super(x, y, width, height, id);
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
                    width, height, null);
        } else {
            g.drawImage(FrameManager.getPlayerMoveFrame(currentState)[frame].getBufferedImage(), x, y,
                    width, height, null);
        }
        if(Game.debugMode) {
            g.setColor(Color.BLUE);
            g.drawRect(x+25, y, width-50, height);
            //TOP
            g.drawRect(x+40, y, width-80, 1);
            //BOTTOM
            g.drawRect(x+40, y+height, width-80, 1);
            //LEFT
            g.drawRect(x+25, y+20, 1, width-40);
            //RIGHT
            g.drawRect(x+width-30, y+20,1, width-40);
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
                    gravity += FALLING_GRAVITY_VEL;
                    velY = 0;
                    y = t.getY() + t.getHeight();
                    currentState = PlayerState.falling;

                }
                if (checkCollisionBottom(t, Tile::getBounds)) {
                    //System.out.println("BOTTOM");
                    if(currentState == PlayerState.falling || currentState == PlayerState.sliding
                            || currentState == PlayerState.verticalDashing) {
                        velY = 0;
                        y = t.getY() - getHeight();
                        currentState = PlayerState.standing;
                        Handler.addObject(new LandingEffect(x+15, y+30, 64, 64, Id.landingEffect));
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
                if (frame >= 4) {
                    frame = 0;
                }
                frameDelay = 0;
            }
        }
        else if(currentState == PlayerState.dashing) {
            frameDelay++;
            if (frameDelay >= 10) {
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
    public Rectangle getBounds() { return new Rectangle(x+25, y, width-50, height);}
    public Rectangle getBoundsTop() {
        return new Rectangle(getX()+40, getY(), width-80,1 );
    }
    public Rectangle getBoundsBottom() {
        return new Rectangle(getX()+40, getY()+height, width-80,1 );
    }
    public Rectangle getBoundsLeft() {
        return new Rectangle(getX()+25, getY()+20, 1,height-40 );
    }
    public Rectangle getBoundsRight() {
        return new Rectangle(getX()+width-30, getY()+20, 1,height-40 );
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
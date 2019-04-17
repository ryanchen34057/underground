package character;

import UI.Game;
import effects.DashEffect;
import effects.DeathEffect;
import effects.Effect;
import graphics.FrameManager;
import input.Input;
import level.Tile;
import prize.Prize;
import states.PlayerState;
import states.StateMachine;
import util.Handler;
import util.PrizeCollisionCondition;
import util.TileCollisionCondition;


import java.awt.*;

public class Player extends Entity {

    private int frame;
    private int frameDelay;
    public static final int STEP = 5;
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
    public static final float RUNNINGJUMPING_GRAVITY = 8;
    public static final float RUNNINGJUMPING_GRAVITY_OFFSET = 0.2f;
    public static final int FALLING_VELX = 3;
    public static final float DASHJUMPING_GRAVITY_OFFSET = 0.3f;
    public static final float DASHJUMPING_GRAVITY = 8;
    public static final float VERTICAL_DASHING_VELX = 10;


    // State
    public  StateMachine currentState;
    public StateMachine prevState;
    public static boolean isOnTheGround;
    public int fatigue;
    public static boolean isTired;

    public Player(int x, int y, int width, int height, Id id) {
        super(x, y, width, height, id);
        frameDelay = 0;
        frame = 0;
        isOnTheGround = false;
        prevState = null;
        currentState = PlayerState.standing;
        fatigue = 0;
        isTired = false;
    }

    @Override
    public void paint(Graphics g) {
        if (super.getFacing() == -1) {
            g.drawImage(FrameManager.getPlayerMoveFrame(currentState)[frame + 4].getBufferedImage(), super.getX(), super.getY(),
                    super.getWidth(), super.getHeight(), null);
        } else {
            g.drawImage(FrameManager.getPlayerMoveFrame(currentState)[frame].getBufferedImage(), super.getX(), super.getY(),
                    super.getWidth(), super.getHeight(), null);
        }
        if(Game.debugMode) {
            g.setColor(Color.BLUE);
            g.drawRect(super.getX(), super.getY(), getWidth(), getHeight());
            //TOP
            g.drawRect(super.getX()+40, super.getY(), getWidth()-80, 1);
            //BOTTOM
            g.drawRect(super.getX()+40, super.getY()+getHeight(), getWidth()-80, 1);
            //LEFT
            g.drawRect(super.getX()+25, super.getY()+20, 1, getWidth()-40);
            //RIGHT
            g.drawRect(super.getX()+getWidth()-30, super.getY()+20,1, getWidth()-40);
        }

    }

    @Override
    public void update() {
        if(prevState != currentState) {
            prevState = currentState;
            System.out.println(prevState);
        }

        // Predict collision
        setX(getX() + (int)getVelX());
        setY(getY() + (int)getVelY());

        handleKeyInput();
        currentState.update(this);
        isOnTheGround = false;
        Tile t;
        for (int i = 0; i < Game.handler.getTiles().size(); i++) {
            t = Game.handler.getTiles().get(i);
            //Wall collision test
            if (t.getId() == Id.wall || t.getId() == Id.spike) {
                if (checkCollisionTop(t, Tile::getBounds)) {
//                    System.out.println("TOP");
                    setGravity(FALLING_GRAVITY_VEL);
                    setVelY(0);
                    setY(t.getY() + t.getHeight());
                    currentState = PlayerState.falling;

                }
                if (checkCollisionBottom(t, Tile::getBounds)) {
                    //System.out.println("BOTTOM");
                    if(currentState == PlayerState.falling || currentState == PlayerState.sliding
                            || currentState == PlayerState.verticalDashing) {
                        setVelY(0);
                        setY(t.getY() - getHeight());
                        currentState = PlayerState.standing;
                    }
                    isOnTheGround = true;
                    fatigue = 0;
                }
                if (checkCollisionLeft(t, Tile::getBounds)) {
                    System.out.println("LEFT");
                    setVelX(0);
                    setX(t.getX() + t.getWidth() - 25);
                    if(currentState == PlayerState.falling &&
                            Input.keys.get(2).down && fatigue < STAMINA) {
                        currentState = PlayerState.sliding;
                    }
                }
                if (checkCollisionRight(t, Tile::getBounds)) {
                    System.out.println("RIGHT");
                    setVelX(0);
                    setX(t.getX() - getWidth() + 28);
                    if(currentState == PlayerState.falling&& Input.keys.get(3).down && fatigue < STAMINA) {
                        currentState = PlayerState.sliding;
                    }
                }
            }
                // Spike collision test
                checkHitTrap(t);

                //Prize
                if (t.getId() == Id.coin) {
                    if (checkCollisionTop(t, Tile::getBoundsTop)) {
                        Game.handler.getPrizes().remove(t);
                    }
                    if (checkCollisionBottom(t, Tile::getBoundsTop)) {
                        Game.handler.getPrizes().remove(t);
                    }

                    if (checkCollisionLeft(t, Tile::getBoundsTop)) {
                        Game.handler.getPrizes().remove(t);
                    }
                    if (checkCollisionRight(t, Tile::getBoundsTop)) {
                        Game.handler.getPrizes().remove(t);
                    }
                }
        }
        Prize p;
        for (int i = 0; i < Game.handler.getPrizes().size(); i++) {
            p = Game.handler.getPrizes().get(i);
            checkGetPrize(p);
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
    private boolean checkCollisionTop(Prize p, PrizeCollisionCondition prizeCollisionCondition) {
        return getBoundsTop().intersects(prizeCollisionCondition.checkCollision(p));
    }
    private boolean checkCollisionBottom(Prize p, PrizeCollisionCondition prizeCollisionCondition) {
        return getBoundsBottom().intersects(prizeCollisionCondition.checkCollision(p));
    }
    private boolean checkCollisionLeft(Prize p, PrizeCollisionCondition prizeCollisionCondition) {
        return getBoundsLeft().intersects(prizeCollisionCondition.checkCollision(p));
    }
    private boolean checkCollisionRight(Prize p, PrizeCollisionCondition prizeCollisionCondition) {
        return getBoundsRight().intersects(prizeCollisionCondition.checkCollision(p));
    }
    private void checkHitTrap(Tile t) {
        // Spike collision test
        if(t.getId() == Id.upwardSpike) {
            if((checkCollisionTop(t, Tile::getBoundsTop)
            || (checkCollisionBottom(t, Tile::getBoundsTop)
            || (checkCollisionLeft(t, Tile::getBoundsTop)
            || (checkCollisionRight(t, Tile::getBoundsTop))))))
                Game.handler.getEntities().remove(this);
        }
        else if(t.getId() == Id.downwardSpike) {
            if((checkCollisionTop(t, Tile::getBoundsBottom)
                    || (checkCollisionBottom(t, Tile::getBoundsBottom)
                    || (checkCollisionLeft(t, Tile::getBoundsBottom)
                    || (checkCollisionRight(t, Tile::getBoundsBottom)))))){
                Game.handler.getEntities().remove(this);
                Handler.addObject(new DeathEffect(getX(), getY(), DeathEffect.EFFECT_SIZE,DeathEffect.EFFECT_SIZE, Id.deathEffect));
            }
        }
    }
    private void checkGetPrize(Prize p) {
        //Prize
        if(p.getId() == Id.coin) {
            if((checkCollisionTop(p, Prize::getBounds)
                    || (checkCollisionBottom(p, Prize::getBounds)
                    || (checkCollisionLeft(p, Prize::getBounds)
                    || (checkCollisionRight(p, Prize::getBounds)))))) {
                Game.handler.getPrizes().remove(p);
            }
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

}
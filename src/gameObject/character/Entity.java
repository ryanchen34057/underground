package gameObject.character;

import effects.Effect;
import enums.Id;
import gameObject.ICollidable;
import states.State;

import java.awt.*;

public abstract class Entity implements ICollidable {
    //Coordinate
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected float velX;
    protected float velY;
    protected int facing;  //-1 -> left, 1 -> right

    //Physics
    protected float gravity;

    //info
    protected Id id;
    protected boolean isDead;
    protected State currentState;
    protected State prevState;
    protected Effect currentEffect;

    // State
    protected boolean isOnTheGround;
    protected int fatigue;
    protected boolean isTired;

    public Entity(int x, int y, int width, int height, Id id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velX = 0;
        this.velY = 0;
        gravity = 0;
        this.id = id;
        facing = 0;
        isDead = false;
    }

    //getters and setters
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public double getVelX() {
        return velX;
    }
    public void setVelX(float velX) {
        this.velX = velX;
    }
    public double getVelY() {
        return velY;
    }
    public void setVelY(float velY) {
        this.velY = velY;
    }
    public int getFacing() {
        return facing;
    }
    public Id getId() {
        return id;
    }
    public float getGravity() {
        return gravity;
    }
    public void setGravity(float gravity) {
        this.gravity = gravity;
    }
    public void setFacing(int facing) {
        this.facing = facing;
    }
    public boolean isDead() {
        return isDead;
    }
    public State getCurrentState() {
        return currentState;
    }
    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }
    public Effect getCurrentEffect() {
        return currentEffect;
    }
    public void setCurrentEffect(Effect currentEffect) {
        this.currentEffect = currentEffect;
    }

    // Drawing method
    public abstract void paint(Graphics g);

    // Update method
    public abstract void update();

    // Collision test
    public abstract Rectangle getBounds();
    public abstract Rectangle getBoundsTop();
    public abstract Rectangle getBoundsBottom();
    public abstract Rectangle getBoundsLeft();
    public abstract Rectangle getBoundsRight();

    // Die
    public abstract void die();

}
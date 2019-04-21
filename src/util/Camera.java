package util;

import UI.Game;
import gameObject.character.Entity;

public class Camera {
    private int x;
    private int y;
    private int backGroundY;
    private boolean shaking;
    private int shakingLength;
    private char shakingAxis;
    private int counter;
    private int intensity;
    public int level = 0;

    public void update(Entity player) {
        x = -player.getX() + Game.WIDTH * 2;
        y = -player.getY() + Game.HEIGHT * 2;
        if(shaking) {
            counter++;
            x += Math.random() * intensity - intensity/ 2;
            y += Math.random() * intensity - intensity/ 2;
            if(counter == shakingLength) {
                shaking = false;
                counter = 0;
            }
        }
    }

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

    public void setShaking(boolean shaking, int intensity, int shakingLength) {
        this.shaking = shaking;
        this.intensity = intensity;
        this.shakingLength = shakingLength;
    }

    public void setBackGroundY(int y) {
        backGroundY = y;
    }

}

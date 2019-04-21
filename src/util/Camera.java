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
        x = -player.getX() + Game.WIDTH / 2;
        y = -player.getY() + Game.HEIGHT * 2;
        if(shaking) {
            counter++;
            if(shakingAxis == 'x') {
                x += Math.random() * intensity - intensity/ 2;
            }
            else if(shakingAxis == 'y') {
                y += Math.random() * intensity - intensity/ 2;
            }
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

    public void setShaking(boolean shaking, int intensity, int shakingLength, char axis) {
        this.shaking = shaking;
        this.intensity = intensity;
        this.shakingLength = shakingLength;
        shakingAxis = axis;
    }

    public void setBackGroundY(int y) {
        backGroundY = y;
    }

}

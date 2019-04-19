package util;

import UI.Game;
import character.Entity;

public class Camera {
    public static int x;
    public static int y;
    public static int backGroundY;
    public int level = 0;

    public void update(Entity player) {
        x = -player.getX() + Game.WIDTH/2;
        if(level == 0) {
            backGroundY = -player.getY() + Game.HEIGHT*2+150;
            level++;
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

}

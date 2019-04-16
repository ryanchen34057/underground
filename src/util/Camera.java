package util;

import UI.Game;
import character.Entity;

public class Camera {
    private int x;
    private int y;

    public void update(Entity player) {
        setX(-player.getX() + Game.WIDTH);
        setY(-player.getY() + Game.HEIGHT*2);
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

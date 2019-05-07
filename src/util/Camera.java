package util;

import UI.Game;
import UI.Window;
import gameObject.character.Entity;
import gameObject.tiles.wall.Wall;

public class Camera {
    private int x;
    private int y;
    private boolean shaking;
    private int shakingLength;
    private int counter;
    private int intensity;

    public void update(Entity player, int mapWidth, int mapHeight) {
//        x = -player.getX() + (Game.WIDTH * Game.SCALE) / 2;
//        y = -player.getY() + (Game.HEIGHT * Game.SCALE) / 2;
        float xTarg = -player.getX() + (Window.scaledGameWidth) / 2.0f;
        float yTarg = -player.getY() + (Window.scaledGameHeight) / 2.0f;
        x += (xTarg - x) * (0.08 * Game.UpdatesRatio * Game.widthRatio);
        y += (yTarg - y) * (0.07 * Game.UpdatesRatio * Game.heightRatio);
        if(x > 0) {
            x = 0;
        }
        if(y > 0) {
            y = 0;
        }
        if(y <= (player.getHeight() + Wall.TILE_SIZE)-mapHeight*(int) (64 * Game.widthRatio) + (int)(Window.scaledGameHeight/1.5)) {
            y = (player.getHeight() + Wall.TILE_SIZE)-mapHeight*(int) (64 * Game.widthRatio) + (int)(Window.scaledGameHeight/1.5);
        }
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
}

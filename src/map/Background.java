package map;

import UI.Game;
import UI.Window;
import graphics.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Background {
    private BufferedImage bcakgroundImage;
    private float x;
    private float y;
    
    private int width;
    private int height;

    private float scale;

    public Background(String s, float scale) {
        bcakgroundImage = ResourceManager.getInstance().getImage(s);
        width = Window.scaledGameWidth;
        height = Window.scaledGameHeight;
    }
    public Background(String s, int width, int height){
        bcakgroundImage = ResourceManager.getInstance().getImage(s);
        this.width = width;
        this.height = height;
    }

    public void setPos(float x, float y) {
        this.x = (x * scale) % Game.WIDTH;
        this.y = (y * scale) % Game.HEIGHT;
    }

    //Getters
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public void setVel(float velX, float velY) {
        x += velX;
        y += velY;
    }

    public void paint(Graphics g) {
        g.drawImage(bcakgroundImage, (int)x, (int)y, width, height, null);

        if(x < 0) {
            g.drawImage(bcakgroundImage, (int)x + Game.WIDTH, (int)y, null);
        }
        if(x > 0) {
            g.drawImage(bcakgroundImage, (int)x - Game.WIDTH, (int)y, null);
        }
        if(y < 0) {
            g.drawImage(bcakgroundImage, (int)x, (int)y + Game.HEIGHT, null);
        }
        if(y > 0) {
            g.drawImage(bcakgroundImage, (int)x, (int)y - Game.HEIGHT, null);
        }

    }



}

package trap;

import UI.Game;
import character.Id;
import graphics.SpriteManager;

import java.awt.*;

public class RightwardSpike extends Spike {

    public RightwardSpike(int x, int y, int width, int height, boolean breakable, Id id) {
        super(x, y, width, height, breakable, id);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(SpriteManager.rightwardSpike.getBufferedImage(), super.getX(), super.getY(),
                super.getWidth(), super.getHeight(), null);
        if(Game.debugMode) {
            g.drawRect(getX()+20, getY(), super.getWidth()-40,1);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public Rectangle getBoundsTop() {
        return null;
    }

    @Override
    public Rectangle getBoundsBottom() {
        return null;
    }

    @Override
    public Rectangle getBoundsLeft() {
        return null;
    }

    @Override
    public Rectangle getBoundsRight() {
        return null;
    }
}

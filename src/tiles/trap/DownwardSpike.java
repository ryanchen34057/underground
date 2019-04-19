package tiles.trap;

import UI.Game;
import enums.Id;
import graphics.SpriteManager;

import java.awt.*;

public class DownwardSpike extends Spike {
    public DownwardSpike(int x, int y, int width, int height, boolean breakable, Id id) {
        super(x, y, width, height, breakable, id);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(SpriteManager.downwardSpike.getBufferedImage(), super.getX(), super.getY(),
                super.getWidth(), super.getHeight(), null);
        if(Game.debugMode) {
            g.drawRect(x, y, width, height/2-5);
            g.drawRect(x+6, y+30, width-12,1);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height/2-5);
    }

    @Override
    public  Rectangle getBoundsTop() {
        return null;
    }
    @Override
    public  Rectangle getBoundsBottom() {
        return new Rectangle(x+6, y+30, width-12,1 );
    }
    @Override
    public  Rectangle getBoundsLeft() {
        return null;
    }
    @Override
    public  Rectangle getBoundsRight() { return null; }
}

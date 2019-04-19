package tiles.trap;

import UI.Game;
import enums.Id;
import graphics.SpriteManager;

import java.awt.*;

public class LeftwardSpike extends Spike {
    public LeftwardSpike(int x, int y, int width, int height, boolean breakable, Id id) {
        super(x, y, width, height, breakable, id);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(SpriteManager.leftwardSpike.getBufferedImage(), super.getX(), super.getY(),
                super.getWidth(), super.getHeight(), null);
        if(Game.debugMode) {
            g.drawRect(x+width/2+5, y, width/2, height);
            g.drawRect(x+35, y+6, 1,height-12);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x+width/2+5, y, width/2, height);
    }

    @Override
    public  Rectangle getBoundsTop() { return null; }
    @Override
    public  Rectangle getBoundsBottom() { return null; }
    @Override
    public  Rectangle getBoundsLeft() {
        return new Rectangle(x+35, y+6, 1,height-12 );
    }
    @Override
    public  Rectangle getBoundsRight() { return null; }
}

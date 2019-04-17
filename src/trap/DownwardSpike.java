package trap;

import UI.Game;
import character.Id;
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
            g.drawRect(getX()+20, getY()+40, super.getWidth()-40,1);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public  Rectangle getBoundsTop() {
        return new Rectangle(getX()+10, getY(), width-20,1 );
    }
    @Override
    public  Rectangle getBoundsBottom() {
        return new Rectangle(getX()+10, getY()+40, width-40,1 );
    }
    @Override
    public  Rectangle getBoundsLeft() {
        return new Rectangle(getX(), getY()+20, 1,height-40 );
    }
    @Override
    public  Rectangle getBoundsRight() { return new Rectangle(getX()+width, getY()+20, 1,height-40 ); }
}

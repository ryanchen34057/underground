package level;

import UI.Game;
import character.Id;

import java.awt.*;

public class Spike extends Tile{
    public Spike(int x, int y, int width, int height,boolean breakable,  Id id) {
        super(x, y, width, height,breakable, id);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(Game.spike.getBufferedImage(), super.getX(), super.getY(),
                super.getWidth(), super.getHeight(), null);
        if(Game.debugMode) {
            g.drawRect(getX()+20, getY(), super.getWidth()-40,1);
        }
    }

    @Override
    public void update() {

    }
}

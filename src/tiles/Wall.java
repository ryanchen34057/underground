package tiles;

import UI.Game;
import character.Id;
import graphics.SpriteManager;

import java.awt.*;

public class Wall extends Tile {
    public Wall(int x, int y, int width, int height,boolean breakable,  Id id) {
        super(x, y, width, height,breakable, id);
    }

    @Override
    public void paint(Graphics g) {
        if(!breakable) {
            g.drawImage(SpriteManager.wall1.getBufferedImage(), super.getX(), super.getY(),
                    super.getWidth(), super.getHeight(), null);
        }
        else {
            g.drawImage(SpriteManager.wall2Breakable.getBufferedImage(), super.getX(), super.getY(),
                    super.getWidth(), super.getHeight(), null);
        }

        if(Game.debugMode) {
            g.setColor(Color.GREEN);
            g.drawRect(getX(), getY(), super.getWidth(),getHeight() );
        }
    }

    @Override
    public void update() {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public  Rectangle getBoundsTop() {
        return new Rectangle(x+10, y, width-20,1 );
    }
    @Override
    public  Rectangle getBoundsBottom() {
        return new Rectangle(x+10, y+height, width-20,1 );
    }
    @Override
    public  Rectangle getBoundsLeft() {
        return new Rectangle(x, y+20, 1,height-40 );
    }
    @Override
    public  Rectangle getBoundsRight() { return new Rectangle(x+width, y+20, 1,height-40 ); }
}

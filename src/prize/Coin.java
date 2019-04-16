package prize;

import UI.Game;
import character.Id;
import graphics.MoveFrameManager;
import util.Handler;

import java.awt.*;

public class Coin extends Prize {
    private int frameDelay, frame;
    public Coin(int x, int y, int width, int height, int point, Id id) {
        super(x, y, width, height, point, id);
        frameDelay = 0;
        frame = 0;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(MoveFrameManager.getPrizeFrame()[frame].getBufferedImage(), super.getX(), super.getY(),
                super.getWidth(), super.getHeight(), null);
//        if(Game.debugMode) {
//            g.setColor(Color.YELLOW);
//            g.drawRect(getX(), getY(), super.getWidth(),getHeight() );
//        }
    }

    @Override
    public void update() {
        frameDelay++;
        if (frameDelay >= 5) {
            frame++;
            if (frame >= MoveFrameManager.getPrizeFrame().length / 2) {
                frame = 0;
            }
            frameDelay = 0;
        }
    }

    public void die() {

    }
}

package effects;

import character.Id;
import graphics.FrameManager;
import util.Handler;

import java.awt.*;

public class DeathEffect extends Effect {
    public static int EFFECT_SIZE = 64;
    public DeathEffect(int x, int y, int width, int height, Id id) {
        super(x, y, width, height, id);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(FrameManager.getDeathFrame()[frame].getBufferedImage(), super.getX(), super.getY(),
                super.getWidth(), super.getHeight(), null);
    }

    @Override
    public void update() {
        frameDelay++;
        if (frameDelay >= 5) {
            frame++;
            if (frame >= FrameManager.deathFrame.length) {
                frame = 0;
                Handler.effectRemove();
            }
            frameDelay = 0;
        }
    }
}

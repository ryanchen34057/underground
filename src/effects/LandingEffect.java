package effects;

import character.Id;
import graphics.FrameManager;
import util.Handler;

import java.awt.*;

public class LandingEffect extends Effect {
    public static int EFFECT_SIZE = 100;
    public LandingEffect(int x, int y, int width, int height, Id id) {
        super(x, y, width, height, id);
        frame = 0;
        frameDelay = 0;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(FrameManager.getEffectFrame(id)[frame].getBufferedImage(), super.getX(), super.getY(),
                super.getWidth(), super.getHeight(), null);
    }

    @Override
    public void update() {
        frameDelay++;
        if (frameDelay >= 1) {
            frame++;
            if (frame >= 17) {
                frame = 0;
                Handler.effectRemove();
            }
            frameDelay = 0;
        }
    }
}

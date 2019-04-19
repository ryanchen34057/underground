package effects;

import enums.Id;
import character.Player;
import graphics.FrameManager;
import util.Handler;

import java.awt.*;

public class LandingEffect extends Effect {
    public static int EFFECT_SIZE = 100;
    private static LandingEffect landingEffect;
    private LandingEffect(int x, int y, int width, int height, Id id) {
        super(x, y, width, height, id);
        frame = 0;
        frameDelay = 0;
    }

    public static LandingEffect getInstance(Player player) {
        return new LandingEffect(player.getX()+15, player.getY()+30, 64, 64, Id.landingEffect);
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

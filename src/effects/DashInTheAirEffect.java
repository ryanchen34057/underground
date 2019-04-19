package effects;

import enums.Id;
import character.Player;
import graphics.FrameManager;
import util.Handler;

import java.awt.*;

public class DashInTheAirEffect extends Effect {
    private int direction;
    public final static int EFFECT_SIZE = 150;
    public DashInTheAirEffect(int x, int y, int width, int height, int direction, Id id) {
        super(x, y, width, height, id);
        this.direction = direction;
        frame = 0;
        frameDelay = 0;
    }



    public static DashInTheAirEffect getInstance(Player player) {
        return  new DashInTheAirEffect(player.getX()
                , player.getY()-20, EFFECT_SIZE, EFFECT_SIZE, player.getFacing(), Id.dashInTheAirEffect);
    }

    @Override
    public void paint(Graphics g) {
        if(direction == 1) {
            g.drawImage(FrameManager.getEffectFrame(id)[frame].getBufferedImage(), super.getX(), super.getY(),
                    super.getWidth(), super.getHeight(), null);
        }
        else {
            g.drawImage(FrameManager.getEffectFrame(id)[frame+12].getBufferedImage(), super.getX(), super.getY(),
                    super.getWidth(), super.getHeight(), null);
        }
    }

    @Override
    public void update() {
        frameDelay++;
        if (frameDelay >= 3) {
            frame++;
            if (frame >= 12) {
                frame = 0;
                Handler.effectRemove();
            }
            frameDelay = 0;
        }
    }
}

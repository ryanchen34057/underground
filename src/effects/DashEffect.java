package effects;

import UI.Game;
import enums.Id;
import gameObject.character.Player;
import graphics.FrameManager;

import java.awt.*;

public class DashEffect extends Effect{
    public static final int EFFECT_SIZE = (int)(100 * Game.widthRatio);
    private int direction;

    private DashEffect(int x, int y, int width, int height, int direction, Id id) {
        super(x, y, width, height, id);
        frame = 0;
        frameDelay = 0;
        this.direction = direction;
    }

    public static DashEffect getInstance(Player player) {
        return  new DashEffect(player.getX() + -player.getFacing() * player.getWidth() + 15
                , player.getY(), EFFECT_SIZE, EFFECT_SIZE, player.getFacing(), Id.dashEffect);
    }

    @Override
    public void paint(Graphics g) {
        if(direction == 1) {
            g.drawImage(FrameManager.getEffectFrame(id)[frame].getBufferedImage(), x, y,
                    width, height, null);
        }
        else {
            g.drawImage(FrameManager.getEffectFrame(id)[frame+15].getBufferedImage(), x, y,
                        width, height, null);
        }
    }

    @Override
    public void update() {
        frameDelay++;
        if (frameDelay >= 2 / Game.UpdatesRatio) {
            frame++;
            if (frame >= 15) {
                frame = 0;
                die();
            }
            frameDelay = 0;
        }
    }
}

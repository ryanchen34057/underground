package effects;

import character.Id;
import character.Player;
import graphics.FrameManager;
import util.Handler;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class DashInTheAirEffect extends Effect {
    private int direction;
    private double velX;
    private double velY;
    public final static int EFFECT_SIZE = 150;
    public DashInTheAirEffect(int x, int y, int width, int height, int direction, double velX, double velY, Id id) {
        super(x, y, width, height, id);
        this.direction = direction;
        frame = 0;
        frameDelay = 0;
        this.velX = velX;
        this.velY = velY;
    }



    public static DashInTheAirEffect getInstance(Player player) {
        double velX = player.getVelX();
        double velY = player.getVelY();
        return  new DashInTheAirEffect(player.getX()
                , player.getY()-20, EFFECT_SIZE, EFFECT_SIZE, player.getFacing(), velX, velY, Id.dashInTheAirEffect);
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
//        x += velX;
//        y += velY;
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

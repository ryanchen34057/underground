package effects;

import java.awt.*;

public abstract class ParticleSystem {
    protected int x;
    protected int y;
    protected int destX;
    protected int destY;
    protected int size;
    protected int life;

    public ParticleSystem(int x, int y, int destX, int destY, int size, int life) {
        this.x = x;
        this.y = y;
        this.destX = destX;
        this.destY = destY;
        this.size = size;
        this.life = life;
    }

    public abstract void update();

    public abstract void paint(Graphics g);
}

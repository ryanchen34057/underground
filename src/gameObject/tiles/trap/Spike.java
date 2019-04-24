package gameObject.tiles.trap;

import enums.Id;
import gameObject.tiles.Tile;

import java.awt.image.BufferedImage;


public abstract class Spike extends Tile {
    public static final int TILE_SIZE = 64;
    public Spike(int x, int y, int width, int height, Id id, BufferedImage bufferedImage) {
        super(x, y, width, height, id);
        this.bufferedImage = bufferedImage;
    }
}

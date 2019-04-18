package tiles.trap;

import character.Id;
import tiles.Tile;



public abstract class Spike extends Tile {
    public static final int TILE_SIZE = 64;
    public Spike(int x, int y, int width, int height,boolean breakable,  Id id) {
        super(x, y, width, height,breakable, id);
    }
}

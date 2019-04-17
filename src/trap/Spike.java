package trap;

import character.Id;
import level.Tile;


public abstract class Spike extends Tile {
    public Spike(int x, int y, int width, int height,boolean breakable,  Id id) {
        super(x, y, width, height,breakable, id);
    }
}

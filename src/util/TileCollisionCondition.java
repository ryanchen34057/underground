package util;

import tiles.Tile;

import java.awt.*;

public interface TileCollisionCondition {
    Rectangle checkCollision(Tile t);
}

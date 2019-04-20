package util;

import gameObject.tiles.Tile;

import java.awt.*;

public interface CollisionCondition {
    Rectangle checkCollision(Tile t);
}

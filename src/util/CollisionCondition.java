package util;

import gameObject.ICollidable;
import gameObject.tiles.Tile;

import java.awt.*;

public interface CollisionCondition {
    Rectangle checkCollision(Tile t);
}

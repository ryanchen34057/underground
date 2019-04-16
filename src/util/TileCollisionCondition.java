package util;

import level.Tile;
import prize.Prize;

import java.awt.*;

public interface TileCollisionCondition {
    Rectangle checkCollision(Tile t);
}

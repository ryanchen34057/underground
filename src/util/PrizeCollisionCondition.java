package util;

import level.Tile;
import prize.Prize;

import java.awt.*;

public interface PrizeCollisionCondition {
    Rectangle checkCollision(Prize prize);
}

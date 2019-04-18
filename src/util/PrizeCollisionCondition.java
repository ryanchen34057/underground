package util;

import prize.Prize;

import java.awt.*;

public interface PrizeCollisionCondition {
    Rectangle checkCollision(Prize prize);
}

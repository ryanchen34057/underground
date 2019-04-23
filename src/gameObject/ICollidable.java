package gameObject;

import enums.Direction;
import util.CollisionCondition;

public interface ICollidable {
    boolean collidesWith(ICollidable other, CollisionCondition collisionCondition);
    void handleCollision(ICollidable other, Direction direction);
    void reactToCollision(ICollidable other, Direction direction);
}

package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;
import src.gameobjects.*;

/**
 * interface of the strategies
 */
public interface CollisionStrategy {

    /**
     * function that called when there is collision to perform action.
     *
     * @param collidedObj
     * @param colliderObj
     */
    public void onCollision(GameObject collidedObj, GameObject colliderObj);


}

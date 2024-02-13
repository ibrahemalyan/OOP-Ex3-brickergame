package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

public class NormalCollisionStrategy implements CollisionStrategy {
    GameObjectCollection gameObjectCollection;
    Counter bricksCounter;

    /**
     *
     * @param gameObjectCollection game objects
     * @param bricksCounter number of bricks
     */
    public NormalCollisionStrategy(GameObjectCollection gameObjectCollection, Counter bricksCounter) {

        this.gameObjectCollection=gameObjectCollection;
        this.bricksCounter=bricksCounter;
    }

    /**
     *
     * @param collidedObj
     * @param colliderObj
     */
    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj) {
        if (gameObjectCollection.removeGameObject(collidedObj)){
            this.bricksCounter.decrement();
        }
    }
}

package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.AnotherPaddle;

public class AnotherPaddleStrategy implements CollisionStrategy {
    GameObjectCollection gameObjectCollection;
    Renderable renderable;
    Vector2 size;
    Counter currentPaddleCounter;
    UserInputListener userInputListener;
    Vector2 windowDimensions;
    int minDistFromEdge;
    CollisionStrategy strategy;

    /**
     * @param gameObjectCollection game objects
     * @param renderable           render of paddle
     * @param size                 size of the paddle
     * @param userInputListener    user inputlistener
     * @param windowDimensions     window dimensioning
     * @param minDistFromEdge      minimum destination from edge
     * @param currentPaddleCounter current paddle counter starting from 0
     * @param strategy             normal collision
     */
    public AnotherPaddleStrategy(GameObjectCollection gameObjectCollection, Renderable renderable,
                                 Vector2 size, UserInputListener userInputListener,
                                 Vector2 windowDimensions, int minDistFromEdge, Counter currentPaddleCounter,
                                 CollisionStrategy strategy) {
        this.gameObjectCollection = gameObjectCollection;
        this.renderable = renderable;
        this.size = size;
        this.userInputListener = userInputListener;
        this.windowDimensions = windowDimensions;
        this.minDistFromEdge = minDistFromEdge;
        this.currentPaddleCounter = currentPaddleCounter;
        this.strategy = strategy;
    }


    /**
     * @param collidedObj
     * @param colliderObj
     */

    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj) {
        if (this.currentPaddleCounter.value() < 1) {
            AnotherPaddle anotherPaddle = new AnotherPaddle(Vector2.ZERO, size, renderable
                    , windowDimensions, minDistFromEdge,
                    gameObjectCollection, currentPaddleCounter, userInputListener);
            anotherPaddle.setCenter(windowDimensions.mult(0.5f));
            gameObjectCollection.addGameObject(anotherPaddle);
            currentPaddleCounter.increaseBy(1);
        }
        this.strategy.onCollision(collidedObj, colliderObj);

    }
}

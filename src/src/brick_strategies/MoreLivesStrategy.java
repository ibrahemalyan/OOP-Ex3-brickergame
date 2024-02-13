package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.MoreHeart;

public class MoreLivesStrategy implements CollisionStrategy {
    public static final int HEART_VELOCITY = 100;
    GameObjectCollection gameObjectCollection;
    Renderable renderable;
    Vector2 heartSize;
    CollisionStrategy NormalCollisionStrategy;
    Sound collisionSound;
    Counter livesCounter;
    int numOfLives;
    WindowController windowController;

    /**
     *
     * @param gameObjectCollection game objects
     * @param renderable render
     * @param NormalCollisionStrategy normal collision
     * @param collisionSound sound of ball collision
     * @param heartSize heart size
     * @param livesCounter number of lives
     * @param numOfLives nuumber of max lives
     * @param windowController  windows controller
     */
    public MoreLivesStrategy(GameObjectCollection gameObjectCollection, Renderable renderable,
                             CollisionStrategy NormalCollisionStrategy,
                             Sound collisionSound, Vector2 heartSize, Counter livesCounter,
                             int numOfLives, WindowController windowController) {
        this.gameObjectCollection = gameObjectCollection;
        this.renderable = renderable;
        this.NormalCollisionStrategy = NormalCollisionStrategy;
        this.collisionSound = collisionSound;
        this.heartSize = heartSize;
        this.livesCounter = livesCounter;
        this.numOfLives = numOfLives;
        this.windowController = windowController;
    }

    /**
     *
     * @param collidedObj
     * @param colliderObj
     */
    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj) {
        MoreHeart moreHeart = new MoreHeart(Vector2.ZERO, heartSize, livesCounter,
                renderable, gameObjectCollection, numOfLives, windowController);
        moreHeart.setVelocity(Vector2.DOWN.mult(HEART_VELOCITY));
        moreHeart.setCenter(collidedObj.getCenter());
        gameObjectCollection.addGameObject(moreHeart);
        NormalCollisionStrategy.onCollision(collidedObj, colliderObj);

    }
}

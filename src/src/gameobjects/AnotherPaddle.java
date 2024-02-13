package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class AnotherPaddle extends Paddle {
    int collisionCount;
    GameObjectCollection gameObjectCollection;
    Counter isThereAnotherPaddle;

    /**
     * constructor of another paddle
     * @param topLeftCorner
     * @param dimensions
     * @param renderable
     * @param windowDimensions
     * @param minDistFromEdge
     * @param gameObjectCollection
     * @param anotherPaddleCounter
     * @param inputListener
     */
    public AnotherPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                          Vector2 windowDimensions,
                         int minDistFromEdge, GameObjectCollection gameObjectCollection,
                         Counter anotherPaddleCounter,UserInputListener inputListener) {
        super(topLeftCorner, dimensions, renderable, inputListener,
                windowDimensions, minDistFromEdge);
        this.isThereAnotherPaddle = anotherPaddleCounter;
        this.gameObjectCollection = gameObjectCollection;
    }

    /**
     *
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other instanceof Ball)
            collisionCount += 1;
    }

    /**
     *
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (collisionCount >= 3) {
            gameObjectCollection.removeGameObject(this);
            isThereAnotherPaddle.decrement();
        }
    }
}

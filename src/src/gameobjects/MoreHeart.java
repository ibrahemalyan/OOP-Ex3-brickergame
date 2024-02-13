package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class MoreHeart extends GameObject {
    GameObjectCollection gameObjectCollection;
    int numOfLives;
    Counter livesCounter;
    WindowController windowController;

    /**
     * Construct a new heart instance.
     *
     * @param widgetTopLeftCorner Position of the object, in window coordinates (pixels).
     *                            Note that (0,0) is the top-left corner of the window.
     * @param widgetDimensions    Width and height in window coordinates.
     * @param widgetRenderable    The renderable representing the object. Can be null, in which case
     *                            the GameObject will not be rendered.
     */
    public MoreHeart(danogl.util.Vector2 widgetTopLeftCorner,
                     danogl.util.Vector2 widgetDimensions,
                     danogl.util.Counter livesCounter,
                     danogl.gui.rendering.Renderable widgetRenderable,
                     danogl.collisions.GameObjectCollection gameObjectsCollection,
                     int numOfLives, WindowController windowController) {

        super(widgetTopLeftCorner, widgetDimensions, widgetRenderable);
        this.livesCounter = livesCounter;
        this.numOfLives = numOfLives;
        this.gameObjectCollection = gameObjectsCollection;
        this.windowController = windowController;

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
        if (!(other instanceof AnotherPaddle) && other instanceof Paddle) {
            if (livesCounter.value() < 4) {
                if (gameObjectCollection.removeGameObject(this)) {
                    livesCounter.increment();
                }
            }
        }
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
        if (getCenter().y() > windowController.getWindowDimensions().y()) {
            gameObjectCollection.removeGameObject(this);
        }
    }

    /**
     *
     * @param other The other GameObject.
     * @return
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return !(other instanceof AnotherPaddle) && other instanceof Paddle;
    }
}

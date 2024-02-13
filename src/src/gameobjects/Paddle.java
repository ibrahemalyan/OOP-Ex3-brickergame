package src.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {
    private static final float MOVEMENT_SPEED = 500;
    UserInputListener userInputListener;
    Vector2 windowDimensions;
    int minDistFromEdge;


    /**
     * Constructor of the Paddle, it constructs new paddle with given Dimensions
     * @param topLeftCorner
     * @param dimensions
     * @param renderable
     * @param inputListener
     * @param windowDimensions
     * @param minDistFromEdge
     */
    public Paddle(danogl.util.Vector2 topLeftCorner, danogl.util.Vector2 dimensions,
                  danogl.gui.rendering.Renderable renderable,
                  UserInputListener inputListener,
                  Vector2 windowDimensions, int minDistFromEdge) {
        super(topLeftCorner, dimensions, renderable);
        this.minDistFromEdge = minDistFromEdge;
        this.userInputListener = inputListener;
        this.windowDimensions = windowDimensions;

    }

    /**
     * overrided function that updates the paddle place on the screen
     * after sensing keyboards arrows clicks.
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
        Vector2 movementDir = Vector2.ZERO;
        if (userInputListener.isKeyPressed(KeyEvent.VK_LEFT) &&
                this.getTopLeftCorner().x() >= minDistFromEdge) {
            movementDir = movementDir.add(Vector2.LEFT);
        }
        if (userInputListener.isKeyPressed(KeyEvent.VK_RIGHT)
                && windowDimensions.x() - (this.getDimensions().x() + this.getTopLeftCorner().x())
                >= minDistFromEdge) {
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
    }
}

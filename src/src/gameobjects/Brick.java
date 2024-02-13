package src.gameobjects;

import src.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class Brick extends GameObject {
    CollisionStrategy strategy;
    Counter counter;


    /**
     * constructor that constructs brick with given dimensions.
     *
     * @param topLeftCorner
     * @param dimensions
     * @param renderable
     * @param strategy
     * @param counter
     */
    public Brick(danogl.util.Vector2 topLeftCorner,
                 danogl.util.Vector2 dimensions,
                 danogl.gui.rendering.Renderable renderable,
                 CollisionStrategy strategy,
                 danogl.util.Counter counter) {
        super(topLeftCorner, dimensions, renderable);
        this.strategy = strategy;
        this.counter = counter;

    }

    /**
     * function that runs collision strategy on brick.
     *
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        strategy.onCollision(this, other);
    }
}

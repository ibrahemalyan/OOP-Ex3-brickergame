package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.util.Counter;
import danogl.util.Vector2;

public class Ball extends GameObject {
    private Counter collisionCount;
    Sound sound;

    /**
     * Construct a new Ball .
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */

    public Ball(danogl.util.Vector2 topLeftCorner,
                danogl.util.Vector2 dimensions,
                danogl.gui.rendering.Renderable renderable,
                danogl.gui.Sound sound) {
        super(topLeftCorner, dimensions, renderable);
        this.sound = sound;
        this.collisionCount = new Counter();

    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        Vector2 newBallVelocity = getVelocity().flipped(collision.getNormal());
        setVelocity(newBallVelocity);
        sound.play();
        this.collisionCount.increaseBy(1);


    }

    /**
     *
     * @return number of collisions
     */
    public int getCollisionCount() {
        return this.collisionCount.value();
    }
}

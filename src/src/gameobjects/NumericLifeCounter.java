package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;

import java.awt.*;

public class NumericLifeCounter extends GameObject {

    Counter livesCounter;
    int startingLives;
    GameObjectCollection gameObjectCollection;
    TextRenderable textRenderable;


    /**
     * @param livesCounter
     * @param topLeftCorner
     * @param dimensions
     * @param gameObjectCollection
     */
    public NumericLifeCounter(danogl.util.Counter livesCounter,
                              danogl.util.Vector2 topLeftCorner,
                              danogl.util.Vector2 dimensions,
                              danogl.collisions.GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, null);
        startingLives = livesCounter.value();

        this.livesCounter = livesCounter;
        this.gameObjectCollection = gameObjectCollection;
        textRenderable = new TextRenderable(String.valueOf(livesCounter.value()));
        textRenderable.setColor(Color.GREEN);
        this.renderer().setRenderable(textRenderable);

    }

    /**
     * over rided function that changes the color of
     * the counter according to the remaining hearts
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
        if (livesCounter.value() > 2) {
            textRenderable.setString(Integer.toString(livesCounter.value()));
            textRenderable.setColor(Color.GREEN);

        } else if (livesCounter.value() == 2) {
            textRenderable.setString("2");
            textRenderable.setColor(Color.YELLOW);

        } else if (livesCounter.value() == 1) {
            textRenderable.setString("1");
            textRenderable.setColor(Color.RED);

        }
        this.renderer().setRenderable(textRenderable);


    }
}


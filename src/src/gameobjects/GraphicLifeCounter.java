package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class GraphicLifeCounter extends GameObject {
    Counter livesCounter;
    GameObjectCollection gameObjectCollection;
    int numOfLives;
    GameObject[] lives;

    Renderable widgetRenderable;
    Vector2 widgetDimensions;

    /**
     * constructor
     *
     * @param widgetTopLeftCorner
     * @param widgetDimensions
     * @param livesCounter
     * @param widgetRenderable
     * @param gameObjectsCollection
     * @param numOfLives
     */
    public GraphicLifeCounter(danogl.util.Vector2 widgetTopLeftCorner,
                              danogl.util.Vector2 widgetDimensions,
                              danogl.util.Counter livesCounter,
                              danogl.gui.rendering.Renderable widgetRenderable,
                              danogl.collisions.GameObjectCollection gameObjectsCollection,
                              int numOfLives) {
        super(widgetTopLeftCorner, widgetDimensions, widgetRenderable);
        this.widgetDimensions = widgetDimensions;
        this.livesCounter = livesCounter;
        this.numOfLives = numOfLives;
        this.gameObjectCollection = gameObjectsCollection;
        this.lives = new GameObject[numOfLives];
        this.widgetRenderable = widgetRenderable;

        for (int i = 0; i < numOfLives; i++) {
            this.lives[i] = new GameObject(new Vector2((+35 * (i)), 670), widgetDimensions, widgetRenderable);
            gameObjectCollection.addGameObject(this.lives[i], Layer.UI);


        }
    }

    /**
     * function that adds a heart to the screen according to their recent number.
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
        if (livesCounter.value() < numOfLives) {
            numOfLives--;

            gameObjectCollection.removeGameObject(this.lives[numOfLives], Layer.UI);


        }

        if (livesCounter.value() > numOfLives) {
            this.lives[numOfLives] = new GameObject(new Vector2((+35 * (numOfLives)), 670),
                    widgetDimensions, widgetRenderable);

            gameObjectCollection.addGameObject(lives[numOfLives], Layer.UI);
            numOfLives++;
        }

    }
}

package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import src.gameobjects.Ball;

import java.util.Random;

public class MockStrategy implements CollisionStrategy {
    private float velocity;
    private GameObjectCollection gameObjectCollection;
    private Renderable renderable;


    private CollisionStrategy onCollisionStrategy;
    private Sound collisionSound;

    /**
     *
     * @param gameObjectCollection game objects
     * @param renderable render
     * @param collisionStrategy normal collision strategy
     * @param collisionSound sound of ball collision
     * @param velocity velocity of the mock ball
     */
    public MockStrategy(GameObjectCollection gameObjectCollection,
                        Renderable renderable, CollisionStrategy collisionStrategy,
                        Sound collisionSound,float velocity) {
        this.gameObjectCollection = gameObjectCollection;
        this.renderable = renderable;
        this.onCollisionStrategy = collisionStrategy;
        this.collisionSound = collisionSound;
        this.velocity=velocity;
    }

    /**
     * function that takes ball and given velocity
     * and let the ball move in diagonal way with the velocity
     * @param ball
     * @param velocity
     */
    private void mockBallMoveDiagonal(GameObject ball, float velocity) {
        float ballVelX = velocity;
        float ballVelY = velocity;
        Random rand = new Random();
        if (rand.nextBoolean())
            ballVelX *= -1;
        if (rand.nextBoolean())
            ballVelY *= -1;
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
    }

    /**
     *
     * @param collidedObj
     * @param colliderObj
     */
    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj) {
        float mockBallSize = collidedObj.getDimensions().x() / 3;
        for (int i = 0; i < 3; i++) {
            Ball newMockBall = new Ball(collidedObj.getCenter(),
                    new Vector2(mockBallSize, mockBallSize),
                    renderable, collisionSound);
            mockBallMoveDiagonal(newMockBall, 200);
            gameObjectCollection.addGameObject(newMockBall);


        }

        onCollisionStrategy.onCollision(collidedObj, colliderObj);

    }
}

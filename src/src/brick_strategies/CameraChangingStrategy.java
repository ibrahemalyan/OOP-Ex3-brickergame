package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.WindowController;
import src.controllers.CameraController;
import src.gameobjects.Ball;

public class CameraChangingStrategy implements CollisionStrategy {
    Ball ball;
    WindowController windowController;
    CollisionStrategy NormalCollisionStrategy;
    CameraController cameraController;

    /**
     *
     * @param ball main ball of the game
     * @param windowController windows controller
     * @param cameraController camera controller
     * @param NormalCollisionStrategy normal collision
     */
    public CameraChangingStrategy(Ball ball, WindowController windowController,
                                  CameraController cameraController,
                                  CollisionStrategy NormalCollisionStrategy) {
        this.ball = ball;
        this.windowController = windowController;
        this.NormalCollisionStrategy = NormalCollisionStrategy;
        this.cameraController = cameraController;
    }

    /**
     * if the ball collide with the brick then the camera follow the ball.
     * @param collidedObj
     * @param colliderObj
     */
    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj) {
        if (colliderObj == ball) {
            cameraController.followObject();
        }
        NormalCollisionStrategy.onCollision(collidedObj, colliderObj);
    }
}

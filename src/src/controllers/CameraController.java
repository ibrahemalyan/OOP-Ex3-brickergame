package src.controllers;

import danogl.GameManager;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import src.gameobjects.Ball;

public class CameraController {
    Ball ball;
    WindowController windowController;
    int numOfCollisions;
    GameManager gameManager;

    /**
     *
     * @param ball main gams ball
     * @param windowController windows controller
     * @param gameManager game manager
     */
    public CameraController(Ball ball, WindowController windowController, GameManager gameManager) {
        this.ball = ball;
        this.windowController = windowController;
        this.gameManager = gameManager;
    }

    /**
     * function that updates the current  Collisions Num
     */
    private void updateCollisionsNum() {
        this.numOfCollisions = ball.getCollisionCount();
    }

    /**
     * function to follow object with camera
     */
    public void followObject() {
        gameManager.setCamera(new Camera(
                ball, //object to follow
                Vector2.ZERO, //follow the center of the object
                windowController.getWindowDimensions().mult(1.2f), //widen the frame a bit
                windowController.getWindowDimensions()));
        updateCollisionsNum();

    }

    /**
     * check if it's time to stop following
     * @return
     */
    private boolean checkCollisionNumToStop() {
        return ball.getCollisionCount() - numOfCollisions >= 4;
    }

    /**
     * stopping the camera if the condition true
     */
    public void checkToFollow() {
        if (gameManager.getCamera() != null)
            if (checkCollisionNumToStop()) {
                gameManager.setCamera(null);
            }
    }
}

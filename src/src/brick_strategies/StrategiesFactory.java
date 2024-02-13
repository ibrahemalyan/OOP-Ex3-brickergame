package src.brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.controllers.CameraController;
import src.gameobjects.Ball;

public class StrategiesFactory {
    private int minDistFromEdge;
    private int maxNumOfStrategies;
    private GameObjectCollection gameObjectCollection;
    private UserInputListener userInputListener;
    private WindowController windowController;
    private Renderable mockRender;
    private Renderable anotherPaddleRender;
    private Renderable anotherHeartRender;
    private Vector2 anotherPaddleSize;

    private CollisionStrategy NormalCollision;
    private Sound collisionSound;
    private Counter bricksCounter;
    private Counter livesCounter;
    private Vector2 heartSize;
    private int numOfLives;
    private Ball ball;
    private CameraController cameraController;
    private Counter anotherPaddlesCount;
    private float ballVelocity;
    String[] strategyNames;
    String[] strategyNamesWithoutNormal;

    /**
     * constructor of the factory
     * @param gameObjectCollection game objects
     * @param anotherPaddlesCount another paddle cound
     * @param maxNumOfStrategies max number of strategies
     * @param userInputListener user inputlistner
     * @param windowController windows control
     * @param mockRender mock image
     * @param anotherPaddleRender paddle render
     * @param anotherHeartRender heart render
     * @param anotherPaddleSize paddle size
     * @param NormalCollision normal collision
     * @param collisionSound collision sound
     * @param bricksCounter bricks count
     * @param livesCounter lives count
     * @param heartSize heart size
     * @param numOfLives max lives
     * @param ball game ball
     * @param cameraController camera controller
     * @param minDistFromEdge min destination from edge
     * @param ballVelocity ball velocity
     * @param strategyNames list of strategy names
     * @param strategyNamesWithoutNormal  list of strategy names
     */
    public StrategiesFactory(GameObjectCollection gameObjectCollection, Counter anotherPaddlesCount,
                             int maxNumOfStrategies, UserInputListener userInputListener,
                             WindowController windowController, Renderable mockRender,
                             Renderable anotherPaddleRender, Renderable anotherHeartRender,
                             Vector2 anotherPaddleSize, CollisionStrategy NormalCollision,
                             Sound collisionSound, Counter bricksCounter, Counter livesCounter,
                             Vector2 heartSize, int numOfLives, Ball ball,
                             CameraController cameraController, int minDistFromEdge,
                             float ballVelocity, String[] strategyNames,
                             String[] strategyNamesWithoutNormal) {
        this.gameObjectCollection = gameObjectCollection;
        this.userInputListener = userInputListener;
        this.windowController = windowController;
        this.maxNumOfStrategies = maxNumOfStrategies;
        this.mockRender = mockRender;
        this.anotherPaddleRender = anotherPaddleRender;
        this.anotherHeartRender = anotherHeartRender;
        this.anotherPaddleSize = anotherPaddleSize;
        this.NormalCollision = NormalCollision;
        this.collisionSound = collisionSound;
        this.bricksCounter = bricksCounter;
        this.livesCounter = livesCounter;
        this.heartSize = heartSize;
        this.numOfLives = numOfLives;
        this.ball = ball;
        this.cameraController = cameraController;
        this.anotherPaddlesCount = anotherPaddlesCount;
        this.minDistFromEdge = minDistFromEdge;
        this.ballVelocity = ballVelocity;
        this.strategyNames = strategyNames;
        this.strategyNamesWithoutNormal = strategyNamesWithoutNormal;

    }

    /**
     * generate new strategy according to given name
     * @param name name of requested strategy
     * @return object of generated strategy
     */
    public CollisionStrategy generateStrategy(String name) {
        switch (name) {
            case "Mock":
                return new MockStrategy(gameObjectCollection, mockRender, NormalCollision, collisionSound, ballVelocity);
            case "More Lives":
                return new MoreLivesStrategy(gameObjectCollection, anotherHeartRender,
                        NormalCollision, collisionSound,
                        heartSize, livesCounter, numOfLives, windowController);
            case "double":
                return new DoubleStrategy(new Counter(), maxNumOfStrategies, NormalCollision, this, strategyNames,strategyNamesWithoutNormal);
            case "More paddle":
                return new AnotherPaddleStrategy(gameObjectCollection, anotherPaddleRender,
                        anotherPaddleSize, userInputListener, windowController.getWindowDimensions(),
                        minDistFromEdge,
                        anotherPaddlesCount, NormalCollision
                );
            case "Camera":
                return new CameraChangingStrategy(ball, windowController, cameraController, NormalCollision);
            default:
                return new NormalCollisionStrategy(gameObjectCollection, bricksCounter);

        }
    }
}

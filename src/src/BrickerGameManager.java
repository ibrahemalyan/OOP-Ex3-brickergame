package src;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.controllers.CameraController;
import src.gameobjects.*;
import src.brick_strategies.*;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class BrickerGameManager extends GameManager {
    private static final Renderable BORDER_RENDERABLE =
            new RectangleRenderable(new Color(80, 140, 250));
    private Random rand;

    private static final String WIN_MSG = "You Win!";
    private static final float PADDLE_WIDTH = 200;
    private static final float PADDLE_HEIGHT = 20;
    private static final int DEFAULT_LIVES_NUM = 3;
    private static final int MIN_DIST_FROM_EDGE = 10;
    private static final float BOARDER_WIDTH = 20;
    private static final float BALL_SPEED = 300;
    private static final float BRICK_WIDTH = 75;
    private static final int BRICK_HEIGHT = 30;
    private static final float BALL_RADIUS = 30;
    private static final String LOSE_MSG = "You Lost!";
    private static final String PLAY_AGAIN = " Play again?";
    private static final float HEART_SIZE = 20;
    private static final int DEFAULT_BRICKS_NUM = 56;

    private Ball ball;
    private Vector2 windowDimensions;
    private String windowTitle;
    private WindowController windowController;
    private GraphicLifeCounter graphicLifeCounter;
    private Counter bricksCounter;
    private Counter anotherPaddlesCount;
    private Counter heartCounter;
    private NumericLifeCounter numericLifeCounter;
    private int numOfLives;
    private UserInputListener inputListener;
    Renderable heartImage;
    Sound collisionSound;
    Renderable anotherPaddleImage;
    private Renderable mockBall;
    private CameraController cameraController;
    String[] possibleStrategies = {"Mock", "More paddle", "More Lives", "Camera",
            "normal", "double"};
    String[] possibleStrategiesWithoutNormal = {"Mock", "More paddle", "More Lives", "Camera",
            "double"};
    private int maxNumofStrategies = 3;

    /**
     * constructor of the main class
     *
     * @param windowTitle      title of the game
     * @param windowDimensions dimensions of the screen
     */
    public BrickerGameManager(java.lang.String windowTitle,
                              danogl.util.Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
        this.windowTitle = windowTitle;
        this.windowDimensions = windowDimensions;

    }

    /**
     * @param imageReader      Contains a single method: readImage, which reads an image from disk.
     *                         See its documentation for help.
     * @param soundReader      Contains a single method: readSound, which reads a wav file from
     *                         disk. See its documentation for help.
     * @param inputListener    Contains a single method: isKeyPressed, which returns whether
     *                         a given key is currently pressed by the user or not. See its
     *                         documentation.
     * @param windowController Contains an array of helpful, self explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowController = windowController;
        addBackground(imageReader);
        this.inputListener = inputListener;
        createHearts(imageReader, inputListener);
        createNumericLifeCounter();
        createBall(imageReader, soundReader, windowController);
        createPaddle(imageReader, inputListener, windowDimensions);
        createBorders(windowDimensions);
        createBricks(imageReader);


    }

    /**
     * Helper function to create NumericlifeCounter
     */
    private void createNumericLifeCounter() {
        numericLifeCounter = new NumericLifeCounter(heartCounter,
                new Vector2(100, windowDimensions.y() - MIN_DIST_FROM_EDGE * 3),
                new Vector2(HEART_SIZE, HEART_SIZE), gameObjects());
        gameObjects().addGameObject(numericLifeCounter, Layer.UI);
    }

    /**
     * Helper function to create Hearts and lives
     */
    private void createHearts(ImageReader imageReader, UserInputListener inputListener) {
        heartImage = imageReader.readImage("assets/heart.png", true);
        numOfLives = DEFAULT_LIVES_NUM + 1;

        heartCounter = new Counter(DEFAULT_LIVES_NUM);
        graphicLifeCounter = new GraphicLifeCounter(Vector2.ZERO,
                new Vector2(HEART_SIZE, HEART_SIZE), heartCounter, heartImage,
                gameObjects(), numOfLives);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd(this.windowController);
        graphicLifeCounter.update(1);
        numericLifeCounter.update(1);
        cameraController.checkToFollow();
    }

    /**
     * Helper function to check if there is a possible win or lose
     */
    private void checkForGameEnd(WindowController windowController) {
        double ballHeight = ball.getCenter().y();

        String prompt = "";
        if (bricksCounter.value() <= 0 || inputListener.isKeyPressed(KeyEvent.VK_W)) {
            prompt = WIN_MSG;
        } else if (ballHeight > windowDimensions.y()) {
            heartCounter.decrement();
            ball.setCenter(new Vector2(windowDimensions.x(), windowDimensions.y() + 200).mult(0.5f));

        }
        if (heartCounter.value() == 0) {
            prompt = LOSE_MSG;

        }

        if (!prompt.isEmpty()) {
            prompt += PLAY_AGAIN;
            if (windowController.openYesNoDialog(prompt))
                windowController.resetGame();
            else
                windowController.closeWindow();
        }
    }

    /**
     * Helper function to add background to the game
     */
    private void addBackground(ImageReader imageReader) {
        Renderable background =
                imageReader.readImage("assets/DARK_BG2_small.jpeg", false);
        GameObject backgroundObject = new GameObject(Vector2.ZERO, windowDimensions, background);
        gameObjects().addGameObject(backgroundObject, Layer.BACKGROUND);
    }

    /**
     * function that adds 56 bricks to the game.
     *
     * @param imageReader
     */
    private void createBricks(ImageReader imageReader) {
        Renderable brickImage =
                imageReader.readImage("assets/brick.png", false);
        GameObject brickObject;
        this.anotherPaddleImage =
                imageReader.readImage("assets/botGood.png", true);
        this.mockBall =
                imageReader.readImage("assets/mockBall.png", true);
        anotherPaddlesCount = new Counter();
        bricksCounter = new Counter(DEFAULT_BRICKS_NUM);
        StrategiesFactory strategiesFactory = new StrategiesFactory(gameObjects(),
                anotherPaddlesCount, this.maxNumofStrategies,
                inputListener, windowController, mockBall, anotherPaddleImage,
                heartImage, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                new NormalCollisionStrategy(gameObjects(), bricksCounter), collisionSound,
                bricksCounter, heartCounter, new Vector2(HEART_SIZE, HEART_SIZE),
                numOfLives, ball, cameraController,
                MIN_DIST_FROM_EDGE, BALL_SPEED, possibleStrategies, possibleStrategiesWithoutNormal);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {

                brickObject = new Brick(new Vector2(75 * (j + 1), BRICK_HEIGHT * (i + 2)),
                        new Vector2(BRICK_WIDTH, BRICK_HEIGHT), brickImage,
                        getRandomStrategy(strategiesFactory), bricksCounter);
                gameObjects().addGameObject(brickObject);
            }
        }
    }

    /**
     * function that create paddle and add it to the game object
     *
     * @param imageReader
     * @param inputListener
     * @param windowDimensions
     */
    private void createPaddle(ImageReader imageReader,
                              UserInputListener inputListener,
                              Vector2 windowDimensions) {
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", false);

        GameObject paddle = new Paddle(
                Vector2.ZERO,
                new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage,
                inputListener
                , this.windowDimensions, MIN_DIST_FROM_EDGE
        );

        paddle.setCenter(
                new Vector2(windowDimensions.x() / 2, (int) windowDimensions.y() - 3 * MIN_DIST_FROM_EDGE));
        gameObjects().addGameObject(paddle);
    }

    private CollisionStrategy getRandomStrategy(StrategiesFactory factory) {

        rand = new Random();
        int num = rand.nextInt(this.possibleStrategies.length);

        return factory.generateStrategy(this.possibleStrategies[num]);
    }

    /**
     * function that creates border and put them in the sides of the game.
     *
     * @param windowDimensions
     */

    private void createBorders(Vector2 windowDimensions) {
        gameObjects().addGameObject(
                new GameObject(
                        Vector2.ZERO,
                        new Vector2(BOARDER_WIDTH, windowDimensions.y()),
                        null)
        );
        gameObjects().addGameObject(
                new GameObject(
                        Vector2.ZERO,
                        new Vector2(windowDimensions.x(), BOARDER_WIDTH),
                        null)
        );
        gameObjects().addGameObject(
                new GameObject(
                        new Vector2(windowDimensions.x() - 2 * MIN_DIST_FROM_EDGE, 0),
                        new Vector2(BOARDER_WIDTH, windowDimensions.y()),
                        null)
        );

    }

    /**
     * function that creates ball with given radius.
     *
     * @param imageReader
     * @param soundReader
     * @param windowController
     */
    private void createBall(ImageReader imageReader, SoundReader soundReader, WindowController windowController) {
        Renderable ballImage =
                imageReader.readImage("assets/ball.png", true);
        collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        ball = new Ball(
                Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage, collisionSound);

        Vector2 windowDimensions = windowController.getWindowDimensions();
        ball.setCenter(new Vector2(windowDimensions.x(), windowDimensions.y() + 200).mult(0.5f));
        gameObjects().addGameObject(ball);

        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean())
            ballVelX *= -1;
        if (rand.nextBoolean())
            ballVelY *= -1;
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
        gameObjects().addGameObject(ball);
        this.cameraController = new CameraController(ball, windowController, this);


    }

    public static void main(String[] args) {
        new BrickerGameManager("My game", new Vector2(700, 700)).run();

    }
}

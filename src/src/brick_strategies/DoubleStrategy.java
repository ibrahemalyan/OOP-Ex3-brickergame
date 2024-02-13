package src.brick_strategies;

import danogl.GameObject;
import danogl.util.Counter;

import java.util.Random;

public class DoubleStrategy implements CollisionStrategy {
    Counter numOfStrategies;
    int maxNumOfStrategies;
    CollisionStrategy normalCollisionStrategy;
    StrategiesFactory strategiesFactory;
    private CollisionStrategy firstStrategy;
    private CollisionStrategy secondStrategy;
    Random random = new Random();
    String[] strategyNames;
    String[] strategyNamesWithoutNormal;

    /**
     *
     * @param numOfStrategies num of current strategies
     * @param maxNumOfStrategies maximum number of strategies that object can hold
     * @param normalCollisionStrategy normal collision
     * @param strategiesFactory factory object
     * @param strategyNames list of possible strategy names
     * @param strategyNamesWithoutNormal list of possible strategy names
     */
    public DoubleStrategy(Counter numOfStrategies, int maxNumOfStrategies,
                          CollisionStrategy normalCollisionStrategy,
                          StrategiesFactory strategiesFactory, String[] strategyNames,
                          String[] strategyNamesWithoutNormal) {
        this.numOfStrategies = numOfStrategies;
        this.maxNumOfStrategies = maxNumOfStrategies;
        this.normalCollisionStrategy = normalCollisionStrategy;
        this.strategiesFactory = strategiesFactory;
        this.strategyNamesWithoutNormal = strategyNamesWithoutNormal;
        this.strategyNames = strategyNames;
        firstStrategy = strategiesFactory.generateStrategy(generateRandomStrategy(strategyNamesWithoutNormal));

        secondStrategy = strategiesFactory.generateStrategy(generateRandomStrategy(strategyNames));


    }

    /**
     * function that gets a list of possible names and returns two random generated strategies.
     * @param names list of possible names
     * @return
     */
    private String generateRandomStrategy(String[] names) {
        String generatedName;
        generatedName = names[random.nextInt(names.length)];
        while (true) {
            if (!generatedName.equals("double")) {
                numOfStrategies.increment();
                return generatedName;
            } else {
                if (numOfStrategies.value() + 2 <= maxNumOfStrategies) {
                    break;
                }
                numOfStrategies.increaseBy(2);

            }
        }
        return generatedName;
    }

    /**
     * applying the generated collision strategies
     * @param collidedObj
     * @param colliderObj
     */
    @Override
    public void onCollision(GameObject collidedObj, GameObject colliderObj) {
        normalCollisionStrategy.onCollision(collidedObj, colliderObj);
        firstStrategy.onCollision(collidedObj, colliderObj);
        secondStrategy.onCollision(collidedObj, colliderObj);

    }
}

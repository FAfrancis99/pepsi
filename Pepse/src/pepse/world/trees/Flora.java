package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.util.Vector2;
import pepse.world.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Represents a flora in a game world.
 * The flora consists of trees that can be created within a certain range.
 * The creation of trees is based on a probability to simulate randomness in nature.
 * @author Fahim.francis
 * @see GameObject
 */
public class Flora {
    private static final Random rand = new Random();
    private static final int DISTANCE_BETWEEN_TREES = 60;
    private static final double TREE_PROBABILITY = 0.1;
    private static final int ZERO = 0;
    private final Function<Integer, Float> groundHeightAt;
    private Consumer<Float> energyAddition;
    private GameObjectCollection gameObjects;
    private   ImageReader imageReader;
    private GameObject gameObject;
    private static final int INITIAL_HEIGHT = 100;
    private static final int BOUND_TRUNK = 150;
    /**
     * Constructor.
     * @param groundHeightAt A function that returns the ground height at a certain x location.
     * @param gameObjects The collection of game objects to which the trees will be added.
     * @param energyAddition A callback function for handling energy addition.
     */
    public Flora(Function<Integer, Float> groundHeightAt, GameObjectCollection gameObjects,
                 Consumer<Float> energyAddition) {
        this.groundHeightAt = groundHeightAt;
        this.gameObjects = gameObjects;
        this.gameObject = gameObject;
        this.energyAddition = energyAddition;
    }
    /**
     * Creates trees within a certain range.
     * The trees are created at certain intervals, and whether a
     * tree is created at a certain location is based on a probability.
     * @param minX The minimum x location where a tree can be created.
     * @param maxX The maximum x location where a tree can be created.
     * @return A list of trees that were created.
     */
    public List<Tree> createInRange(int minX, int maxX) {
        List<Tree> trees = new ArrayList<>();
        for (int x = minX; x <= maxX; x+= DISTANCE_BETWEEN_TREES) {
            // Avoid planting a tree at the first left side of the road
            if (x == minX) {
                continue;
            }
            // Flip a biased coin to decide whether to plant a tree
            if (rand.nextDouble() < TREE_PROBABILITY) {
                // Create a tree at this x location with the ground height as the y location
                float y = groundHeightAt.apply(x);
                Tree tree = new Tree(new Vector2(x, y), new Vector2(Block.SIZE,
                        INITIAL_HEIGHT + rand.nextInt(BOUND_TRUNK)),gameObjects,energyAddition);
                trees.add(tree);
//                gameObjects.addGameObject(tree, Layer.STATIC_OBJECTS);
            }
        }
        return trees;
    }

}

package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.Transition;
import danogl.gui.ImageReader;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.AvatarJumpObserver;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import static pepse.world.trees.Trunk.TRUNK_COL;
/**
 * The Tree class extends GameObject and implements `AvatarJumpObserver`.
 * It represents a tree , composed of trunk blocks, leaves, and fruits.
 * The tree is created at a specified top left corner with specified dimensions.
 * The tree can react to an avatar's jump by changing its color and rotating its leaves.
 * @author fahim.francis
 * @see GameObject
 * @see AvatarJumpObserver
 */
public class Tree extends GameObject implements AvatarJumpObserver {
    private static final float INITIAL_VALUE = 0f;
    private static final float FINAL_VALUE = 90f;
    private static final int INT_TWO = 2;
    private static final int INT_THREE = 3;
    private static final int BOUND_THREE = INT_THREE;
    private static final int BOUND_FOUR = 4;
    private static final int BOUND_TRUNK = 150;
    private static final int INITIAL_HEIGHT = 100;
    private static final int ZERO = 0;
    private static final double LEAF_PROB = 0.4;
    private static final double FRUIT_PROB = 0.3;
    private static final double LEAF_SIZE = 30.0;
    private static final double DOUBLE_TWO = 2.0;
    private List<Trunk> trunkBlocks;
    private List<Leaf> leaves;
    private List<Fruit> fruits;
    private static final Random rand = new Random();
    private Vector2 topLeftCorner;
    private GameObjectCollection gameObjects;
    private ImageReader imageReader;
    private Renderable trunkRendreable;
    private Consumer<Float> energyAddition;

    /**
     * Constructor.
     * @param topLeftCorner The top left corner of the tree.
     * @param dimensions The dimensions of the tree.
     * @param gameObjects The collection of game objects.
     * @param energyAddition  A callback function for handling energy addition.
     */
    public Tree(Vector2 topLeftCorner, Vector2 dimensions, GameObjectCollection gameObjects ,
                Consumer<Float> energyAddition) {
        super(topLeftCorner, dimensions, null);
        this.trunkBlocks = new ArrayList<>();
        this.leaves = new ArrayList<>();
        this.fruits = new ArrayList<>();
        this.topLeftCorner = topLeftCorner;
        this.gameObjects = gameObjects;
        this.energyAddition = energyAddition;

        // creat trunk
        int trunkWidth = (int) dimensions.x();
        int trunkHeight =(int) dimensions.y();
        for (int i = ZERO; i < trunkHeight; i++) {
            Trunk trunkBlock = new Trunk(new Vector2(topLeftCorner.x(),
                    topLeftCorner.y() - i), null);
            trunkBlocks.add(trunkBlock);
            gameObjects.addGameObject(trunkBlock, Layer.STATIC_OBJECTS);
        }
        createLeavesAndFruits(topLeftCorner, gameObjects, trunkWidth, trunkHeight);
    }

    /**
     * This funtion creats leaves in random way by sorounding the top of the trunk
     * and also it creates fruits between the leaves and the trunk or on the leaves also
     * in a random way
     * @param topLeftCorner
     * @param gameObjects
     * @param trunkWidth
     * @param trunkHeight
     */
    private void createLeavesAndFruits(Vector2 topLeftCorner, GameObjectCollection gameObjects,
                                       int trunkWidth, int trunkHeight) {
        int numLeavesRow = BOUND_THREE + rand.nextInt(BOUND_THREE);  // Number of leaves in each row
        int numLeavesColumn = BOUND_FOUR + rand.nextInt(BOUND_FOUR);  // Number of leaves in each column
        double leafWidth = LEAF_SIZE;
        double leafHeight = LEAF_SIZE;
        for (int row = ZERO; row < numLeavesRow; row++) {
            for (int col = ZERO; col < numLeavesColumn; col++) {
                // Adjust the gap between leaves and center them
                double xOffset = (col - numLeavesColumn / DOUBLE_TWO) * leafWidth;
                double yOffset = (row - numLeavesRow / DOUBLE_TWO) * leafHeight;
                double x = topLeftCorner.x() + (double) trunkWidth / INT_TWO + xOffset;
                double y = topLeftCorner.y() - trunkHeight + yOffset ;
                if (rand.nextDouble() > LEAF_PROB){
                    creatLeaf(gameObjects, (float) x, (float) y, (float) leafWidth, (float) leafHeight);

                }  else if (rand.nextDouble() <= FRUIT_PROB) {
                    createFruits(gameObjects, x, y);
                }


            }
        }
    }

    /**
     *  this function takes the postion of the fruit and add it to the gameobject collection
     *  ans also add it to the list of fruits
     * @param gameObjects
     * @param x
     * @param y
     */
    private void createFruits(GameObjectCollection gameObjects, double x, double y) {
        double x_fruit = x;
        double y_fruit = y;
        Vector2 fruitPosition = new Vector2((float) x_fruit, (float) y_fruit);
        Fruit fruit = new Fruit(fruitPosition, gameObjects,energyAddition);
        fruits.add(fruit);
        gameObjects.addGameObject(fruit);
    }

    /**
     * this function add leaf to the gameobjectCollection and also add it to the list of leaves
     * @param gameObjects
     * @param x
     * @param y
     * @param leafWidth
     * @param leafHeight
     */
    private void creatLeaf(GameObjectCollection gameObjects, float x,
                           float y, float leafWidth, float leafHeight) {
        Vector2 leafPosition = new Vector2(x, y);
        Leaf leaf = new Leaf(leafPosition, null,
                new Vector2(leafWidth, leafHeight));
        leaves.add(leaf);
        gameObjects.addGameObject(leaf, Layer.STATIC_OBJECTS);
    }


    /**
     * this fucntion return the trunkBlocksList
     * @return trunkBlocks
     */
    private List<Trunk> getTrunkBlocks() {
        return trunkBlocks;
    }
    /**
     * this fucntion return the leavs list
     * @return leaves
     */
    private List<Leaf> getLeaves() {
        return leaves;
    }
    /**
     * this fucntion return the trunkBlocksList
     * @return fruits
     */
    private List<Fruit> getFruits(){
        return fruits;
    }

    /**
     * this function updates the postion of the tree
     * @param newTopLeftCorner the new postion of the tree
     */
    public void updatePfosition(Vector2 newTopLeftCorner) {
        this.topLeftCorner = newTopLeftCorner;
    }
    /**
     * Rotates a leaf.
     * @param leaf The leaf to be rotated.
     */
    public void rotateLeaf(Leaf leaf) {
        new Transition<Float>(leaf,// the game object being changed
                (Float angle)->leaf.renderer().setRenderableAngle(angle),
                INITIAL_VALUE,// initial transition value
                FINAL_VALUE,// final transition value
                Transition.LINEAR_INTERPOLATOR_FLOAT, // use a linear interpolator
                INT_THREE,// transition time
                Transition.TransitionType.TRANSITION_ONCE,// Choose appropriate ENUM value
                null);// nothing further to execute upon reaching final value
    }
    /**
     * Reacts to an avatar's jump by changing the color of the trunk blocks and rotating the leaves
     * and changing the color of the fruit.
     */
    @Override
    public void onAvatarJump() {
        // Generate a new random brown color
        Color newColor = ColorSupplier.approximateColor(TRUNK_COL);
        for (Trunk trunk : trunkBlocks) {
            // Update the trunk's color
            trunk.renderer().setRenderable(new RectangleRenderable(newColor));
        }
        for (Leaf leaf : leaves){
            rotateLeaf(leaf);
        }
        for (Fruit fruit: fruits) {
            if (fruit.IsRed){
                fruit.renderer().setRenderable(new OvalRenderable(Color.yellow));
                fruit.IsRed = false;
            }
            else {
                fruit.renderer().setRenderable(new OvalRenderable(Color.red));
                fruit.IsRed = true;
            }
        }
    }
}

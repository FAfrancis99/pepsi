package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;

import java.awt.*;
import java.util.Random;
/**
 * Represents a leaf in a game world.
 * The leaf is part of a tree and has animations for dimension and angle transitions.
 * @author fahim.francis
 * @see Block
 */
public class Leaf extends Block {
    private static final int R = 50;
    private static final int G = 200;
    private static final int B = 30;
    private static final Color LEAF_COL = new Color(R, G, B);
    private static final Random rand = new Random();
    private static final float TRANSITION_TIME = 3f;
    private static final float FINAL_TRANS_VALUE = 15f;
    private static final float INITIAL_TRANS_VALUE = -FINAL_TRANS_VALUE;
    private static final int RANDOM_FACTOR = 4;
    private static final int INT_ONE = 1;
    private Vector2 topLeftCorner;
    private Vector2 intialDimensions;
    private Vector2 final_transition_value;
    /**
     * Constructor.
     * @param topLeftCorner The top left corner of the leaf.
     * @param renderable The renderable component of the leaf.
     * @param dimension The dimensions of the leaf.
     */
    public Leaf(Vector2 topLeftCorner, Renderable renderable, Vector2 dimension) {
        super(topLeftCorner, new RectangleRenderable(ColorSupplier.approximateColor(LEAF_COL)));
        this.topLeftCorner = topLeftCorner;
        this.intialDimensions = dimension;
        Vector2 addDimensionVector = Vector2.ONES;
        this.final_transition_value = new Vector2(Leaf.SIZE + INT_ONE,Leaf.SIZE + INT_ONE);
        float randomWaitTime = rand.nextFloat() * RANDOM_FACTOR;
        new ScheduledTask(this,randomWaitTime,false,this::leafAnimation);

    }
    /**
     * Determines whether the leaf should collide with another game object.
     * @param other The other game object.
     * @return false because the leaf does not collide with other game objects.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return false;
    }

    /**
     * Starts the animations for the leaf.
     */
    private void leafAnimation() {
        leafDimTransition();
        leafAngleMovement();
    }
    /**
     * Starts the dimension transition animation for the leaf.
     */
    
    private void leafDimTransition() {
        new Transition<Vector2>(this,// the game object being changed
                dimensionsAsVector2->this.setDimensions(dimensionsAsVector2),// the method to call
                intialDimensions, // initial transition value
                final_transition_value,// final transition value
                Transition.LINEAR_INTERPOLATOR_VECTOR, // use a linear interpolator
                TRANSITION_TIME, // transition time
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,// Choose appropriate ENUM value
                null);// nothing further to execute upon reaching final value
    }
    /**
     * Starts the angle movement animation for the leaf.
     */
    private void leafAngleMovement() {
        new Transition<>(this,// the game object being changed
                (Float angle)->this.renderer().setRenderableAngle(angle),
                INITIAL_TRANS_VALUE,// initial transition value
                FINAL_TRANS_VALUE,// final transition value
                Transition.LINEAR_INTERPOLATOR_FLOAT,// use a linear interpolator
                TRANSITION_TIME,// transition time
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,// Choose appropriate ENUM value
                null);// nothing further to execute upon reaching final value
    }


}

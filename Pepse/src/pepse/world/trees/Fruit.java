package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;

import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;
import java.util.function.Consumer;

/**
 * Represents a fruit in a game world.
 * The fruit can be eaten by an avatar to gain energy.
 * Once eaten, the fruit will disappear and reappear after a certain time.
 * @author Fahim.francis
 * @see GameObject
 */
public class Fruit extends GameObject  {

    // PUBLIC
    /**
        this indactes whether the fruit is red or not;
     */
    public  boolean IsRed  ;
    //////// PRIVATE
    private static final float AMOUNT = 10;
    private static final int INVISIBLE = 0;
    private static final int WAIT_TIME = 30;
    private static final int VISIBLE = 1;
    private static final int WAIT_TIME_ZERO = 0;
    private static final String AVATAR = "avatar";
    private static final float FRUIT_SIZE = Leaf.SIZE; // Same size as leaves
    private final GameObjectCollection gameObjects;
    private final Vector2 pos;
    private Transition<Float> fruitTransition;
    private ScheduledTask respawnTask;
    private boolean flag = false;
    private OvalRenderable ovalRenderable;
    private ScheduledTask colorChangeTask;
    private final Vector2 initialPos;
    private  Consumer<Float> energyAddition;
    /**
     * Constructor.
     * @param pos The initial position of the fruit.
     * @param gameObjects The collection of game objects to which the fruit will be added.
     * @param energyAddition  A callback function for handling energy addition
     *fruit
     */
    public Fruit(Vector2 pos, GameObjectCollection gameObjects , Consumer<Float> energyAddition) {
        super(pos, new Vector2(FRUIT_SIZE, FRUIT_SIZE), new OvalRenderable(Color.RED));
        this.gameObjects = gameObjects;
        this.initialPos = pos;
        this.pos = pos;
        IsRed = true;
        this.energyAddition = energyAddition;

    }

    /**
     *  if the Opaquenes of the fruit is invisble it means that the fruit shouldnotcollide with
     *  any object so it returns false else if its visible the fruit can be collided with the avatar
     * @param other The other GameObject.
     * @return true
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        if (this.renderer().getOpaqueness() == INVISIBLE){
            return false;
        }
        return true;
    }

    /**
     * Handles the collision of the fruit with another game object.
     * If the other game object is an avatar, the avatar gains energy and the fruit disappears.
     * @param other The other game object that the fruit collided with.
     * @param collision The collision that occurred.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals(AVATAR)){
//            Avatar avatar = (Avatar)other;
//            avatar.addEnergy(AMOUNT);
            energyAddition.accept(AMOUNT);
            fruitEatCase();
        }
    }
    /**
     * Handles the case when the fruit is eaten.
     * The fruit disappears and a task is scheduled to respawn the fruit after a certain time.
     */
    private void fruitEatCase() {
        // Reappear the fruit at the initial position after 30 seconds
        this.renderer().setOpaqueness(INVISIBLE);
        new ScheduledTask(this, WAIT_TIME, false, this::reappearFruit);
    }

    /**
     * Handels the case when the fruit reappears
     */
    private void reappearFruit() {
        // Reappear the fruit at the initial position after 30 seconds
        this.renderer().setOpaqueness(VISIBLE);
    }




}

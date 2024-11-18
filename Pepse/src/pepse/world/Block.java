package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a block in the game world.
 * The block is a static object that other game objects can interact with.
 * It has a fixed size and is immovable.
 * @author Fahim.francis
 * @see GameObject
 */
public class Block extends GameObject {
    /**
     * The size of the block in game units. It is a constant (static final).
     */
    public static final int SIZE = 30;
    private static final String BLOCK = "block";

    /**
     * Constructor.
     * @param topLeftCorner The top left corner of the block in the game world.
     * @param renderable The visual representation of the block.
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, Vector2.ONES.mult(SIZE), renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
        setTag(BLOCK);
    }
}

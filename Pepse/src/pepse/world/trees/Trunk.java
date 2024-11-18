package pepse.world.trees;

import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;

import java.awt.*;
/**
 * Represents a trunk in a game world.
 * The trunk is part of a tree and has a specific color.
 * @author fahim.francis
 * @see Block
 */
public class Trunk extends Block {
    /**
     * The color of the trunk in RGB values. It is a constant (static final).
     */
    public final static Color TRUNK_COL =  new Color(100, 50, 20);
    private Vector2 topLeftCorner;
    /**
     * Constructor.
     * @param topLeftCorner The top left corner of the trunk.
     * @param renderable The renderable component of the trunk.
     */
    public Trunk(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner,  new RectangleRenderable(TRUNK_COL));
        this.topLeftCorner = topLeftCorner;
    }

}

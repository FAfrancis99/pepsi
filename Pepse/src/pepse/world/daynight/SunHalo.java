package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * Represents a sun halo in a game world.
 * The sun halo is created around the sun object passed to it.
 * @author Fahim.francis
 * @see GameObject
 */
public class SunHalo {

    /**
     * The tag for the sun halo. It is a constant (static final).
     */
    private static final String SUN_HALO = "sunHalo";
    private static final float NEW_SIZE = 1.75f;
    private static final int R = 255;
    private static final int A = 20;
    private static final int B = 0;

    /**
     * Creates a sun halo around the provided sun object.
     * @param sun The sun object around which the halo is created.
     * @return A GameObject representing the sun halo.
     */
    public static GameObject create(GameObject sun){
        float newWidth = sun.getDimensions().x() * NEW_SIZE;
        float newHeight = sun.getDimensions().y() * NEW_SIZE;
        GameObject sunHalo = new GameObject(sun.getTopLeftCorner(),
                new Vector2(newWidth, newHeight),
                new OvalRenderable( new Color(R, R, B, A)));
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sunHalo.setTag(SUN_HALO);
        return sunHalo;
    }
}

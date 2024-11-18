package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * Represents a sun in a game world.
 * The sun's position transitions in a circular path to simulate a day-night cycle.
 * @author Fahim.francis
 * @see GameObject
 */
public class Sun {

    /**
     * The size of the sun in pixels. It is a constant (static final).
     */
    private static final int sunSize = 80;
    private static final int TWO = 2;
    private static final String SUN = "sun";
    private static final float INIT_TRANS_VAL = 0f;
    private static final float FINAL_TRANS_VAL = 360f;
    private static final int OFFSET = 10;

    /**
     * Creates a sun with a day-night cycle.
     * @param windowDimensions The dimensions of the window.
     * @param cycleLength The length of the day-night cycle.
     * @return A GameObject representing the sun.
     */
    public static GameObject create(Vector2 windowDimensions,
                                    float cycleLength){
        Vector2 sunPosition = new Vector2(windowDimensions.x()/ TWO, windowDimensions.y() / TWO);
        Vector2 sunDimention =  new Vector2(sunSize, sunSize);
        GameObject sun = new GameObject(
                sunPosition,sunDimention,
                new OvalRenderable(Color.YELLOW));
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag(SUN);
        Vector2 initialSunCenter = sun.getCenter();
        Vector2 cycleCenter = new Vector2(windowDimensions.x()/ TWO, windowDimensions.y() + OFFSET);
        new Transition<Float>(
                sun, // the game object being changed
                (Float angle) -> sun.setCenter
                        (initialSunCenter.subtract(cycleCenter)
                                .rotated(angle)
                                .add(cycleCenter)), // the method to call
                INIT_TRANS_VAL, // initial transition value
                FINAL_TRANS_VAL, // final transition value
                Transition.LINEAR_INTERPOLATOR_FLOAT,// use a linear interpolator
                cycleLength ,
                Transition.TransitionType.TRANSITION_LOOP,
                null);
        return sun;
    }
}

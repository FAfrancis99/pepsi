package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * Represents a night scene in a game world.
 * The scene's opacity transitions back and forth to simulate day-night cycle.
 * @author Fahim.francis
 * @see GameObject
 */
public class Night {
    /**
     * The opacity at midnight. It is a constant (static final).
     */
    private static final Float MIDNIGHT_OPACITY = 0.5f;
    private static final String NIGHT = "night";
    private static final int TWO = 2;
    private static final float INIT_TRANS_VAL = 0f;

    /**
     * Creates a night scene with a day-night cycle.
     * @param windowDimensions The dimensions of the window.
     * @param cycleLength The length of the day-night cycle.
     * @return A GameObject representing the night scene.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength){
        RectangleRenderable nightRender = new RectangleRenderable(Color.BLACK);
        GameObject night = new GameObject(Vector2.ZERO, windowDimensions, nightRender);
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag(NIGHT);
        new Transition<Float>(
                night, // the game object being changed
                night.renderer()::setOpaqueness, // the method to call
                INIT_TRANS_VAL, // initial transition value
                MIDNIGHT_OPACITY, // final transition value
                Transition.CUBIC_INTERPOLATOR_FLOAT,// use a cubic interpolator
                cycleLength / TWO, // transition fully over half a day
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH
                , // Choose appropriate ENUM value
                null
        );// nothing further to execute upon reaching final value
        return night;
    }
}

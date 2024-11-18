package pepse.world;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;
import java.util.function.Supplier;

/**
 * Represents an energy indicator in the game world.
 * The energy indicator displays the current energy level of a game object.
 * The energy level is updated in each game update cycle.
 * @author Fahim.francis
 * @see GameObject
 */
public class EnergyIndicator extends GameObject {
    private static final String STRING_PERCENT = "%";
    private static final int IND_WIDTH = 10;
    private static final int HEIGHT = 20;
    /**
     * The supplier of the energy level. It is a Supplier<Float>.
     */
    private Supplier<Float> energySupplier;
    private TextRenderable text;

    /**
     * Constructor.
     * @param topLeftCorner The top left corner of the energy indicator in the game world.
     * @param energySupplier The supplier of the energy level.
     * @param gameObjects The collection of game objects in the game world.
     */
    public EnergyIndicator(Vector2 topLeftCorner, Supplier<Float> energySupplier,
                           GameObjectCollection gameObjects) {
        super(topLeftCorner, new Vector2(IND_WIDTH, HEIGHT),null);
        this.energySupplier = energySupplier;
        text = new TextRenderable(energySupplier.toString());
        text.setColor(Color.BLACK);
        gameObjects.addGameObject(new GameObject(topLeftCorner,
                new Vector2(IND_WIDTH, HEIGHT),text),
                Layer.BACKGROUND);
    }

    /**
     * Updates the energy indicator. If the energy level has changed, the display is updated.
     * @param deltaTime The time since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float currentEnergy = energySupplier.get();
        text.setString(String.valueOf(currentEnergy)+ STRING_PERCENT);
    }
}

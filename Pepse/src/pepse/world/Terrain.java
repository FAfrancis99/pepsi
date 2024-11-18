package pepse.world;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
/**
 * This class generates a random landscape using procedural noise generation.
 * The terrain is made of Block objects.
 *
 * @author fahim.francis
 * @see Block, NoiseGenerator
 */
public class Terrain {
    private static final int TERRAIN_DEPTH = 20;
    private static final float FLOAT = (2 / 3f);
    private static final int SEVEN = 7;
    private static final String GROUND = "ground";
    private float groundHeightAtX0;
    private NoiseGenerator noiseGenerator;
    private final Vector2 windowDimensions;
    private static final int R = 212;
    private static final int G = 123;
    private static final int B = 74;
    private static final Color BASE_GROUND_COLOR =
            new Color(R, G, B);

    /**
     * constructor for the Terrain class.
     * @param windowDimensions The dimensions of the game window.
     * @param seed Seed value used for noise generation (affects terrain shape).
     */
    public Terrain(Vector2 windowDimensions, int seed){
        this.windowDimensions = windowDimensions;
        this.groundHeightAtX0 = this.windowDimensions.y() * FLOAT;
        this.noiseGenerator = new NoiseGenerator((double) seed, (int) this.groundHeightAtX0);
    }
    /**
     * Calculates the height of the terrain at a specific x-coordinate.
     *
     * @param x The x-coordinate to check.
     * @return The height of the terrain at the given x-coordinate.
     */
    public float groundHeightAt(float x) {
        float noise = (float) noiseGenerator.noise(x, Block.SIZE * SEVEN);
        return groundHeightAtX0   + noise ;
    }
    /**
     * Creates a list of Block objects representing the terrain within a
     * specified range.
     *
     * @param minX The minimum x-coordinate of the range.
     * @param maxX The maximum x-coordinate of the range.
     * @return A list of Block objects representing the terrain in the range.
     */
    public List<Block> createInRange(int minX, int maxX) {
        List<Block> blockList = new ArrayList<>();
        for (int x = minX; x <= maxX; x += Block.SIZE) {
            float groundHeight = groundHeightAt(x);
            int topBlockY = (int) (Math.floor(groundHeight / Block.SIZE) * Block.SIZE);
            for (int y = topBlockY; y < windowDimensions.y() + TERRAIN_DEPTH ; y += Block.SIZE) {
                Block block = new Block(new Vector2(x, y),
                        new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR)));
                block.setTag(GROUND);
                blockList.add(block);
            }
        }
        return blockList;
    }
}

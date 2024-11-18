package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Vector2;

import pepse.world.*;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Flora;
import pepse.world.trees.Tree;

import java.util.List;
import java.util.Random;

/**
 *  This class represents the main game manager for the Pepse game.
 *  It initializes the game world, including the terrain, sky, day/night cycle,
 *  sun, sun halo, folara, and the avatar.
 *
 *  @fahim.francis
 *  @see Terrain, Sky, Night, Sun, SunHalo, Flora, Avatar
 */
public class PepseGameManager extends GameManager {
    private static final int CYCLE_LENGTH = 30;
    private static final String ASSETS_CLOUDS_PNG = "assets/Clouds_.png";
    private static final int ZERO = 0;
    private static final int INT_TWO = 2;
    private static final int X = 30;
    private static final int Y = 5;
    private static final String AVATAR = "avatar";
    private int seed ;
    private Terrain terrain;
    private ImageReader imageReader;
    private Flora flora;
    private Avatar avatar;

    /**
     *  The main entry point for the Pepse game.
     *  This method creates a new instance of the PepseGameManager class
     *  and calls its `run` method to start the game.
     *
     *  @param args Command line arguments .
     */
    public static void main(String[] args) {
        new PepseGameManager().run();
    }

    /**
     * Initializes the Pepse game world.
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     *                 See its documentation for help.
     * @param soundReader Contains a single method: readSound, which reads a wav file from
     *                    disk. See its documentation for help.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not. See its
     *                      documentation.
     * @param windowController Contains an array of helpful, self explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener
            inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        seed = (int) new Random().nextGaussian();
//        windowController.setTargetFramerate(40);
        terrain = new Terrain(windowController.getWindowDimensions(),seed);
        this.imageReader = imageReader;
        // create Sky
        createSky(windowController);

        // create terrain :
        createTerrain(windowController);

        // create night :
        creatingNight(windowController);

        // create sun & sunHalo
        createingSunAndHalo(windowController);

        // create avatar :
        creatingAvatar(imageReader, inputListener, windowController);

        // create Trees:
        // creating the flora
        flora = new Flora(x -> terrain.groundHeightAt(x),gameObjects(),avatar::addEnergy);
        createTrees(windowController);


    }

    /**
     * create the tree
     * @param windowController
     */
    private void createTrees(WindowController windowController) {
        int windowWidth = (int) windowController.getWindowDimensions().x();
        List<Tree> trees = flora.createInRange(0, windowWidth + Block.SIZE);
        for (Tree tree : trees) {
            avatar.registerObserver(tree);
            gameObjects().addGameObject(tree);
        }
    }


    /**
     * Creates the player character (avatar) and related elements.
     *
     * @param imageReader Used to load the avatar image from disk.
     * @param inputListener Listens for user input (key presses) to control the avatar.
     * @param windowController Provides access to the game window for camera creation.
     */
    private void creatingAvatar(ImageReader imageReader, UserInputListener inputListener,
                                WindowController windowController) {
        float groundHeight = terrain.groundHeightAt(ZERO); // get ground height at x=0
        // center avatar on the ground
        Vector2 avatarPosition = new Vector2(ZERO, groundHeight - Avatar.AVATAR_DIMENTION / INT_TWO);
        avatar = new Avatar(avatarPosition, inputListener, imageReader);
        EnergyIndicator energyIndicator = new EnergyIndicator(new Vector2(X, Y),
                avatar::getEnergy,gameObjects());

        gameObjects().addGameObject(energyIndicator,Layer.BACKGROUND);
        gameObjects().addGameObject(avatar,Layer.DEFAULT);
        avatar.setTag(AVATAR);
    }

    /**
     * This method is used to create a sun and its halo in the game window.
     *
     * @param windowController The controller for the game window.
     * It is used to get the dimensions of the window.
     */
    private void createingSunAndHalo(WindowController windowController) {
        // creat sun :
        GameObject sun = Sun.create(windowController.getWindowDimensions(),CYCLE_LENGTH);
        gameObjects().addGameObject(sun,Layer.BACKGROUND);

        // create sunHalo:
        GameObject sunHalo = SunHalo.create(sun);
        sunHalo.addComponent((deltaTime ->sunHalo.setCenter(sun.getCenter())));
        gameObjects().addGameObject(sunHalo,Layer.BACKGROUND);
    }

    /**
     * This method is used to create a night scene in the game window.
     *
     * @param windowController The controller for the game window.
     *                        It is used to get the dimensions of the window.
     */
    private void creatingNight(WindowController windowController) {
        GameObject night = Night.create(windowController.getWindowDimensions(), CYCLE_LENGTH);
        gameObjects().addGameObject(night,Layer.FOREGROUND);
    }

    /**
     * This method is used to create a terrain in the game window.
     *
     * @param windowController The controller for the game window.
     *                         It is used to get the dimensions of the window.
     */
    private void createTerrain(WindowController windowController) {
        int windowWidth = (int) windowController.getWindowDimensions().x();
        List<Block> blocks = terrain.createInRange(-Block.SIZE, windowWidth + Block.SIZE);
        for (Block block : blocks) {
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }
    }

    /**
     * This method is used to create a sky in the game window.
     * @param windowController
     */
    private void createSky(WindowController windowController) {
        GameObject sky = Sky.create(windowController.getWindowDimensions());
        gameObjects().addGameObject(sky, Layer.BACKGROUND);
        ImageRenderable cloudRender = imageReader.readImage(ASSETS_CLOUDS_PNG,
                true);
        // Adding cloud background to make the design looks better
        GameObject cloud = new GameObject(Vector2.ZERO,windowController.getWindowDimensions(), cloudRender);
        gameObjects().addGameObject(cloud,Layer.BACKGROUND);
    }
}

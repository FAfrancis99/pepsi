package pepse.world;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * The Avatar class represents a character in a game world.
 * It extends the GameObject class and includes properties and methods for controlling the
 * avatar's movement and animations.
 * The avatar can move left or right, jump, and idle. It also has an energy level that decreases when
 * it moves and increases when it idles.
 * The avatar's animations change based on its actions. For example, it has different animations for idling,
 * jumping, and running.
 * The Avatar class also includes a list of observers that are notified when the avatar jumps.
 */
public class Avatar extends GameObject {
    // public
    /**
     * the size of the avatar
     */
    public static final float AVATAR_DIMENTION = 50f;
    // private
    private static final String ASSETS_IDLE_0_PNG = "assets/idle_0.png";
    private static final String ASSETS_IDLE_1_PNG = "assets/idle_1.png";
    private static final String ASSETS_IDLE_2_PNG = "assets/idle_2.png";
    private static final String ASSETS_IDLE_3_PNG = "assets/idle_3.png";
    private static final float TIME_BETWEEN_CLIPS = 0.25f;
    private static final String ASSETS_JUMP_0_PNG = "assets/jump_0.png";
    private static final String ASSETS_JUMP_1_PNG = "assets/jump_1.png";
    private static final String ASSETS_JUMP_2_PNG = "assets/jump_2.png";
    private static final String ASSETS_JUMP_3_PNG = "assets/jump_3.png";
    private static final String ASSETS_RUN_0_PNG = "assets/run_0.png";
    private static final String ASSETS_RUN_1_PNG = "assets/run_1.png";
    private static final String ASSETS_RUN_2_PNG = "assets/run_2.png";
    private static final String ASSETS_RUN_3_PNG = "assets/run_3.png";
    private static final String ASSETS_RUN_4_PNG = "assets/run_4.png";
    private static final String ASSETS_RUN_5_PNG = "assets/run_5.png";
    private static final int ZERO = 0;
    private static final int ENERGY_PLUS_1 = 1;
    private static final int ENERGY_TEN = 10;
    private static final double ENERGY_HALF = 0.5;
    private static final int MAX_ENERGY = 100;
    private UserInputListener inputListener;
    private static final float VELOCITY_X = 200;
    private static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 600;
    private float energy = MAX_ENERGY; // Initialize energy to 100
    private AnimationRenderable idleAnimation;
    private AnimationRenderable jumpAnimation;
    private AnimationRenderable runAnimation;
    private List<AvatarJumpObserver> observers = new ArrayList<>();

    /**
     * Constructor.
     * @param pos The initial position of the avatar.
     * @param inputListener The listener for user input.
     * @param imageReader The reader to read image files.
     */
    public Avatar(Vector2 pos,
                  UserInputListener inputListener,
                  ImageReader imageReader){
        super(pos, new Vector2(AVATAR_DIMENTION, AVATAR_DIMENTION),
                imageReader.readImage(ASSETS_IDLE_0_PNG,
                true));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;
        initlizeAnimation(imageReader);
    }

    /**
     * Initializes the animations for the avatar.
     * This method sets up the idle, jumping, and running animations for
     * the avatar using the provided image reader.
     * Each animation consists of a series of images that are displayed in sequence.
     * The initial animation is set to the idle animation.
     * @param imageReader The ImageReader object used to read the image files for the animations.
     */
    private void initlizeAnimation(ImageReader imageReader) {
        // Initialize animations
        String[] idleImages = {ASSETS_IDLE_0_PNG, ASSETS_IDLE_1_PNG, ASSETS_IDLE_2_PNG, ASSETS_IDLE_3_PNG};
        String[] jumpingImages = {ASSETS_JUMP_0_PNG, ASSETS_JUMP_1_PNG, ASSETS_JUMP_2_PNG,
                ASSETS_JUMP_3_PNG};
        String[] runnigImages = {ASSETS_RUN_0_PNG, ASSETS_RUN_1_PNG, ASSETS_RUN_2_PNG,
                ASSETS_RUN_3_PNG, ASSETS_RUN_4_PNG, ASSETS_RUN_5_PNG};
        // Create the AnimationRenderable objects for each animation
        idleAnimation = new AnimationRenderable(idleImages, imageReader,true,
                TIME_BETWEEN_CLIPS);
        jumpAnimation = new AnimationRenderable( jumpingImages, imageReader,true,
                TIME_BETWEEN_CLIPS);
        runAnimation = new AnimationRenderable(runnigImages, imageReader,true,
                TIME_BETWEEN_CLIPS);
        // Set the initial animation to idle
        renderer().setRenderable(idleAnimation);
    }
    /**
     * Adds an observer for avatar jump events.
     * @param observer The observer to add.
     */
    public void registerObserver(AvatarJumpObserver observer) {
        observers.add(observer);
    }
    /**
     * removes an observer for avatar jump events.
     * @param observer The observer to remove.
     */
    public void unregisterObserver(AvatarJumpObserver observer) {
        observers.remove(observer);
    }


    /**
     * Updates the avatar's state based on the current input and the elapsed time since the last update.
     * This method is called periodically to update the avatar's state.
     * It checks for key presses and adjusts the avatar's velocity and animation accordingly.
     * If the avatar is not moving, it gains energy.
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = ZERO;
        xVel = LeftKeyCase(xVel);
        xVel = RightKeyCase(xVel);
        transform().setVelocityX(xVel);
        SpaceKeyCase();
        if(xVel == ZERO && getVelocity().y() == ZERO){
            energy += ENERGY_PLUS_1; // Add energy for idling
            renderer().setRenderable(idleAnimation);
        }
        energy = Math.min(MAX_ENERGY, energy); // Ensure energy does not exceed 100
    }

    /**
     * Notifies all observers that the avatar has jumped.
     * This method is called when the avatar jumps. It notifies all registered observers of the jump event.
     */
    private void notifyObservers() {
        for (AvatarJumpObserver observer : observers) {
            observer.onAvatarJump();
        }
    }

    /**
     * Handles the case when the space key is pressed.
     * If the avatar is on the ground and has enough energy, it jumps and loses some energy.
     */
    private void SpaceKeyCase() {
        if(inputListener.isKeyPressed(KeyEvent.VK_SPACE)){
            if(getVelocity().y() == ZERO && energy >= ENERGY_TEN){
                transform().setVelocityY(VELOCITY_Y);
                energy -= ENERGY_TEN; // Deduct energy for jumping
                renderer().setRenderable(jumpAnimation);
                notifyObservers();
            }
        }
    }

    /**
     * Handles the case when the right arrow key is pressed.
     * If the avatar has energy, it moves to the right and loses some energy.
     * @param xVel The current horizontal velocity of the avatar.
     * @return The new horizontal velocity of the avatar.
     */
    private float RightKeyCase(float xVel) {
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT) && energy != ZERO){
            xVel += VELOCITY_X;
            energy -= (float) ENERGY_HALF; // Deduct energy for moving right
            renderer().setRenderable(runAnimation);
            renderer().setIsFlippedHorizontally(false);
        }
        return xVel;
    }

    /**
     * Handles the case when the left arrow key is pressed.
     * If the avatar has energy, it moves to the left and loses some energy.
     * @param xVel The current horizontal velocity of the avatar.
     * @return The new horizontal velocity of the avatar.
     */
    private float LeftKeyCase(float xVel) {
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT) && energy != ZERO){
            xVel -= VELOCITY_X;
            energy -= (float) ENERGY_HALF; // Deduct energy for moving left
            renderer().setRenderable(runAnimation);
            renderer().setIsFlippedHorizontally(true);
        }
        return xVel;
    }


    /**
     * Gets the current energy level of the avatar.
     * @return The current energy level.
     */
    public float getEnergy() {
        return energy;
    }
    /**
     * Adds a certain amount of energy to the avatar.
     * @param amount The amount of energy to add.
     */
    public void addEnergy(float amount) {
        this.energy += amount;
        this.energy = Math.min(MAX_ENERGY, this.energy); // Ensure energy does not exceed 100
    }
}


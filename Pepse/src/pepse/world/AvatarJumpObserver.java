package pepse.world;

/**
 * Represents an observer for avatar jumps in the game world.
 * This interface defines a method that should be called when an avatar jump event occurs.
 * @author Fahim.francis
 */
public interface AvatarJumpObserver {
    /**
     * Called when an avatar jump event occurs in the game world.
     * Implementations should define what action should be taken on an avatar jump.
     */
    void onAvatarJump();
}

package item;

import character.Controllable;
import character.Player;
import feature.Touchable;

public interface Droppable extends Touchable {
	
	/**
	 * Method to be called upon a player touching this object, picking it up.
	 * @param player
	 */
	default void onTouch(Controllable character) { // Default implementation. Perhaps something to reconsider.
		// TODO: Make the player pick up the droppable.
	}
	
}

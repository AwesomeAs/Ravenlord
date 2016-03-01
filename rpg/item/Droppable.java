package item;

import character.Player;
import feature.Touchable;

public abstract class Droppable extends Touchable {
	
	/**
	 * Method to be called upon a player touching this object, picking it up.
	 * @param player
	 */
	public void onTouch(Player player) {
		// TODO: Make the player pick up the droppable.
		
	}
	
}

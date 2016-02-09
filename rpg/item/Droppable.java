package item;

import character.Player;
import graphic.Drawable;

public abstract class Droppable extends Drawable {
	
	/**
	 * Method to be called upon a player touching this object.
	 * @param player
	 */
	public abstract void onTouch(Player player);
	
}

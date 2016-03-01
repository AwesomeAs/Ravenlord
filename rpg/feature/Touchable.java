package feature;

import character.Player;
import graphic.Drawable;

public abstract class Touchable extends Drawable {
	
	/**
	 * Method to be called upon a player touching this object.
	 * @param player
	 */
	public abstract void onTouch(Player player);

}

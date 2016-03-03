package feature;

import character.Controllable;
import character.Player;
import graphic.Drawable;

public interface Touchable {
	
	/**
	 * Method to be called upon a player touching this object.
	 * @param player
	 */
	void onTouch(Controllable character);
	void pick(Controllable character);
	boolean isPickable();
}

package graphic;

import java.awt.Graphics2D;

public abstract class Drawable {
	
	private double x;
	private double y;
	private double offset;
	
	/**
	 * Gets the X coordinate in world space.
	 * @return x
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Gets the Y coordinate in world space.
	 * @return y
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Sets the world coordinate for this drawable.
	 * @param x
	 * @param y
	 */
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Gets the offset from the ground, as -y on the screen.
	 * @return y
	 */
	public double getGroundOffset() {
		return offset;
	}
	
	/**
	 * Sets the offset from the ground, as -y on the screen.
	 * @param y
	 */
	public void setGroundOffset(double y) {
		this.offset = y;
	}
	
	/**
	 * Method to be called upon drawing of this object.
	 * @param g
	 */
	public abstract void onDraw(Graphics2D g);
	
}

package graphic;

import java.awt.Graphics2D;

public abstract class Drawable {
	
	private double x;
	private double y;
	private double offset;
	private double height;
	private int ZIndex = 10;
	private double imgheight = 0;
	
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
	 * Gets the visual height of this image.
	 * @return y height
	 */
	public double getImgHeight() {
		return imgheight;
	}
	
	/**
	 * Sets the visual height of this image, used for z-indexing.
	 * @param value
	 */
	public void setImgHeight(double value) {
		imgheight = value;
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
	 * Returns the result of the elevation from the top surface of this drawable minus the elevation of its bottom surface.
	 * @return height
	 */
	public double getHeight() {
		return height;
	}
	
	/**
	 * Sets the result of the elevation from the top surface of this drawable minus the elevation of its bottom surface.<br>
	 * Useable for setting collision detection for i.e. pillars, walls etc.
	 * @param height
	 */
	public void setHeight(double height) {
		this.height = height;
	}
	
	/**
	 * Gets the "layered position" for this object to be drawn. Higher value = closer to screen.
	 * @return z index
	 */
	public int getZIndex() {
		return ZIndex;
	}
	
	/**
	 * Sets the "layered position" for this object to be drawn. Higher value = closer to screen.
	 * @param zindex
	 */
	public void setZIndex(int zindex) {
		this.ZIndex = zindex;
	}
	
	/**
	 * Method to be called upon drawing of this object.
	 * @param g
	 * @param delta
	 */
	public abstract void onDraw(Graphics2D g, float delta);
	
	/**
	 * Method to be called before drawing this object. Is not called in sorted order.
	 * @param delta
	 */
	public void beforeDraw(float delta) {}
	
}

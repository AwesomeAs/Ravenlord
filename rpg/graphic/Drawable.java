package graphic;

import java.awt.Graphics2D;
import java.util.Iterator;

import feature.CollisionArea;

public abstract class Drawable {
	
	private double x;
	private double y;
	private double offset;
	private int iwidth = 64;
	private int iheight = 64;
	private double height;
	private int ZIndex = 10;
	private double imgheight = 0;
	private boolean cancollide = false;
	private CollisionArea collision = new CollisionArea();
	
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
		if (!cancollide) {
			this.x = x;
			this.y = y;
		} else {
			Iterator<Drawable> l = Viewport.getInstance().getObjects();
			while (l.hasNext()) {
				Drawable o = l.next();
				if (!o.equals(this) && o.cancollide && this.isColliding(o,
						o.x - o.iwidth / 2 - x + iwidth / 2, o.y - o.iheight / 2 - y + iheight / 2)) {
					return;
				}
			}
			this.x = x;
			this.y = y;
		}
	}
	
	/**
	 * Gets the allowed width for this element to be drawn at. Defaults to 64.
	 * @return width of image
	 */
	public int sizeWidth() {
		return iwidth;
	}
	
	/**
	 * Gets the allowed height for this element to be drawn at. Defaults to 64.
	 * @return height of image
	 */
	public int sizeHeight() {
		return iheight;
	}
	
	/**
	 * Sets the allowed width and height for this element to be drawn at.
	 * @param width
	 * @param height
	 */
	public Drawable setSize(int width, int height) {
		iwidth = width;
		iheight = height;
		return null;
	}
	
	/**
	 * Allows the shadowing system to draw shadows depending on the unique current animation clip.
	 * @return animation index
	 */
	public int getAnimationID() {
		return 0;
	}
	
	/**
	 * Gets this drawable's ability to collide.
	 * @return boolean indicating if this object can collide with other collidable objects.
	 */
	public boolean canCollide() {
		return cancollide;
	}
	
	/**
	 * Sets this drawable's ability to collide.
	 * @param cancollide
	 */
	public void setCollide(boolean cancollide) {
		this.cancollide = cancollide;
	}
	
	/**
	 * Gives an object which can have shapes added to it and check if a point is within intersection.
	 * @return collision shape
	 */
	public CollisionArea getCollision() {
		return collision;
	}
	
	/**
	 * Checks if the current drawable's collision area is colliding with the other drawable's collision area, given a delta offset.
	 * For calculating pre-move and preventing move if the drawables will collide.
	 * @param other
	 * @param dx
	 * @param dy
	 * @return boolean indicating if the collision areas overlaps.
	 */
	public boolean isColliding(Drawable other, double dx, double dy) {
		boolean colliding = false;
		other.collision.translate(dx, dy);
		if (other.collision.intersects(this.collision)) {
			colliding = true;
		}
		other.collision.translate(-dx, -dy);
		return colliding;
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

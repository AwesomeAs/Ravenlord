package graphic.ui;

import graphic.Drawable;

public abstract class UIElement extends Drawable {
	
	public static enum AnchorPoint {
		TOP_LEFT,
		TOP_CENTER,
		TOP_RIGHT,
		CENTER_LEFT,
		CENTER,
		CENTER_RIGHT,
		BOTTOM_LEFT,
		BOTTOM_CENTER,
		BOTTOM_RIGHT
	}
	
	protected AnchorPoint anchor = AnchorPoint.TOP_LEFT;
	
	public abstract UIElement setSize(int width, int height);
	
	public UIElement setAnchor(AnchorPoint anchor) {
		this.anchor = anchor;
		return this;
	}
	
	public AnchorPoint getAnchor() {
		return anchor;
	}
	
}

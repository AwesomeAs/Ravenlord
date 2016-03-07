package feature;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

public class CollisionArea {
	
	private Area shape = new Area();
	
	
	public void add(Shape primitive) {
		shape.add(new Area(primitive));
	}
	
	public boolean contains(double x, double y) {
		return shape.contains(x, y);
	}
	
	public void translate(double dx, double dy) {
		AffineTransform at = new AffineTransform();
		at.translate(dx, dy);
		shape.transform(at);
	}
	
	public boolean intersects(CollisionArea other) {
		Area temp = new Area(shape);
		temp.intersect(other.shape);
		return !temp.isEmpty();
	}
	
	public BufferedImage getVisualisation() {
		Rectangle2D size = shape.getBounds2D();
		if (size.getWidth() > 0 && size.getHeight() > 0) {
			BufferedImage img = new BufferedImage(
					(int)(size.getWidth() + size.getX()),
					(int)(size.getHeight() + size.getY()),
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D)img.getGraphics();
			g.setColor(new Color(0, 1, 0, 0.875f));
			g.fill(shape);
			g.dispose();
			return img;
		} else {
			return null;
		}
	}
	
}

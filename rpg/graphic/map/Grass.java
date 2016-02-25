package graphic.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import feature.Animation;
import graphic.Drawable;

public class Grass extends Drawable {
	
	private Animation anim;
	
	public Grass(int x, int y) {
		super.setPosition(x, y);
		super.setZIndex(9);
		anim = new Animation("map/Grass");
	}

	@Override
	public void onDraw(Graphics2D g) {
		BufferedImage img = anim.getImage();
		g.drawImage(img, 0, 0, null);
	}

}

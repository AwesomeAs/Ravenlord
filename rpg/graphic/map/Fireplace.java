package graphic.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import feature.Animation;
import graphic.Drawable;

public class Fireplace extends Drawable {
	
	private Animation anim;
	
	public Fireplace(int x, int y) {
		super.setPosition(x, y);
		anim = new Animation("map/Fireplace0", 100);
	}

	@Override
	public void onDraw(Graphics2D g) {
		BufferedImage img = anim.getImage();
		g.drawImage(img, 0, 0, null);
	}

}

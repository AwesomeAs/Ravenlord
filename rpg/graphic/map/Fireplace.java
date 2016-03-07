package graphic.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import feature.Animation;
import graphic.Drawable;

public class Fireplace extends Drawable {
	
	private Animation anim;
	
	public Fireplace(int x, int y) {
		super.setPosition(x, y);
		super.setImgHeight(90);
		super.setSize(128, 128);
		anim = new Animation("map/Fireplace0", 64, 0.3f);
	}

	@Override
	public void onDraw(Graphics2D g, float delta) {
		BufferedImage img = anim.getImage(delta);
		g.drawImage(img, 0, 0, null);
	}

}

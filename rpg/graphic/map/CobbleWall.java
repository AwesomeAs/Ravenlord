package graphic.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import feature.Animation;
import graphic.Drawable;

public class CobbleWall extends Drawable {
	
	private Animation anim;
	
	public CobbleWall(int x, int y, String wallType) {
		super.setPosition(x, y);
		anim = new Animation("map/Wall" + wallType);
	}

	@Override
	public void onDraw(Graphics2D g, float delta) {
		BufferedImage img = anim.getImage();
		g.drawImage(img, 0, 0, null);
	}

}

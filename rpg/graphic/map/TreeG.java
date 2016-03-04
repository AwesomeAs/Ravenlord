package graphic.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import feature.Animation;
import graphic.Drawable;

public class TreeG extends Drawable {
	
	private Animation anim;
	
	public TreeG(int x, int y, int treeType) {
		super.setPosition(x, y);
		super.setImgHeight(284);
		anim = new Animation("map/TreeG" + treeType);
	}

	@Override
	public void onDraw(Graphics2D g, float delta) {
		BufferedImage img = anim.getImage();
		g.drawImage(img, 0, 0, null);
	}

}

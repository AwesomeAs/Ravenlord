package graphic.map;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import feature.Animation;
import graphic.Drawable;

public class TreeG extends Drawable {
	
	private Animation anim;
	
	public TreeG(int x, int y, int treeType) {
		super.setPosition(x, y);
		super.setImgHeight(214);
		super.setSize(384, 384);
		switch (treeType) {
		case 1:
			super.getCollision().add(new Ellipse2D.Double(142, 334, 64, 32));
			break;
		case 2:
			super.getCollision().add(new Ellipse2D.Double(186, 335, 64, 32));
			break;
		case 3:
			super.getCollision().add(new Ellipse2D.Double(132, 336, 64, 32));
			break;
		}
		super.setCollide(true);
		anim = new Animation("map/TreeG" + treeType);
	}

	@Override
	public void onDraw(Graphics2D g, float delta) {
		BufferedImage img = anim.getImage();
		g.drawImage(img, 0, 0, null);
	}

}

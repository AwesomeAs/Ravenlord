package util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.HashMap;

import javax.swing.ImageIcon;

import graphic.Drawable;

public class ImageManager {
	
	private static ImageManager self;
	private HashMap<String, ImageIcon> imgs = new HashMap<String, ImageIcon>();
	private HashMap<String, BufferedImage> cimgs = new HashMap<String, BufferedImage>();
	
	public ImageIcon get(String path) {
		if (!imgs.containsKey(path)) {
			imgs.put(path, new ImageIcon(path));
		}
		return imgs.get(path);
	}
	
	public BufferedImage colorImage(Drawable key, int mcol_r, int mcol_g, int mcol_b) {
		if (!cimgs.containsKey(key.hashCode() + "|" + key.getAnimationID())) {
			int w = key.sizeWidth();
			int h = key.sizeHeight();
			BufferedImage timg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			Graphics2D tdg = timg.createGraphics();
			tdg.setClip(0, 0, w, h);
			key.onDraw(tdg, 0);
			tdg.setClip(null);
			tdg.dispose();
			WritableRaster raster = timg.getRaster();

			for (int xx = 0; xx < w; xx++) {
				for (int yy = 0; yy < h; yy++) {
					int[] pixels = raster.getPixel(xx, yy, (int[]) null);
					pixels[0] = mcol_r;
					pixels[1] = mcol_g;
					pixels[2] = mcol_b;
					raster.setPixel(xx, yy, pixels);
				}
			}

			cimgs.put(key.hashCode() + "|" + key.getAnimationID(), timg);
			timg.flush();
		}
		return cimgs.get(key.hashCode() + "|" + key.getAnimationID());
	}
	
	public static ImageManager getInstance() {
		if (self == null) {
			self = new ImageManager();
		}
		return self;
	}
	
}

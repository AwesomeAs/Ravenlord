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
	
	public BufferedImage colorImage(Drawable key, int w, int h, int x_off, int y_off,
			int mcol_r, int mcol_g, int mcol_b) {
		if (!cimgs.containsKey(key.hashCode() + "," + x_off + "," + y_off)) {
			BufferedImage timg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			Graphics2D tdg = timg.createGraphics();
			tdg.translate(key.getX() + x_off, key.getY() + y_off);
			key.onDraw(tdg, 0);
			tdg.translate(-key.getX() - x_off, -key.getY() - y_off);
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

			cimgs.put(key.hashCode() + "," + x_off + "," + y_off, timg);
			timg.flush();
		}
		return cimgs.get(key.hashCode() + "," + x_off + "," + y_off);
	}
	
	public static ImageManager getInstance() {
		if (self == null) {
			self = new ImageManager();
		}
		return self;
	}
	
}

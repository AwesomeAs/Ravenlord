package feature;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;

public class Animation {
	
	private BufferedImage[] clip;
	private double imgduration;
	private double index = 0.0;
	
	/**
	 * Initialize a sequence of animated images using the path to the local resource (without last number and extension) and
	 * the frame duration for each image to be drawn at.
	 * @param fileprefix
	 * @param imgduration
	 */
	public Animation(String fileprefix, double imgduration) {
		this.imgduration = imgduration;
		int id = 0;
		clip = new BufferedImage[0];
		while (true) {
			URL file = getClass().getClassLoader().getResource(fileprefix + id + ".png");
			if (file != null) {
				BufferedImage[] temp = new BufferedImage[clip.length + 1];
				for (int b = 0; b < clip.length; b++) {
					temp[b] = clip[b];
				}
				ImageIcon img = new ImageIcon(file);
				temp[clip.length] = new BufferedImage(
						img.getIconWidth() * 2, img.getIconHeight() * 2, BufferedImage.TYPE_INT_ARGB);
				temp[clip.length].createGraphics().drawImage(
						img.getImage(), 0, 0, img.getIconWidth() * 2, img.getIconHeight() * 2, img.getImageObserver());
				clip = temp;
				id++;
			} else {
				break;
			}
		}
	}
	
	/**
	 * Initialize a sequence of animated images using the path to the local resource (all except extension), the width
	 * of each image to split the main image into, and the frame duration for each image to be drawn at.
	 * @param filepath
	 * @param imgwidth
	 * @param imgduration
	 */
	public Animation(String filepath, int imgwidth, double imgduration) {
		this.imgduration = imgduration;
		if (new File(filepath + ".png").exists()) {
			ImageIcon img = new ImageIcon(filepath + ".png");
			int length = (int)((float)img.getIconWidth() / imgwidth);
			clip = new BufferedImage[length];
			for (int i = 0; i < length; i++) {
				clip[i] = new BufferedImage(
						imgwidth * 2, img.getIconHeight() * 2, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g = clip[i].createGraphics();
				g.setClip(i * imgwidth, 0, imgwidth, img.getIconHeight());
				g.drawImage(img.getImage(), -i * imgwidth * 2, 0, imgwidth * 2, img.getIconHeight() * 2, img.getImageObserver());
				g.setClip(null);
			}
		} else {
			clip = new BufferedImage[0];
		}
	}
	
	/**
	 * Same as new Animation(fileprefix, 1.0)
	 * @param fileprefix
	 * {@link #Animation(String, double)}
	 */
	public Animation(String fileprefix) {
		this(fileprefix, 1.0);
	}
	
	/**
	 * Same as new Animation(filename, imgwidth, 1.0)
	 * @param filename
	 * @param imgwidth
	 * {@link #Animation(String, int, double)}
	 */
	public Animation(String filename, int imgwidth) {
		this(filename, imgwidth, 1.0);
	}
	
	public BufferedImage getImage() {
		if (clip.length > 0) {
			index += (1 / imgduration);
			index = index % clip.length;
			return clip[(int)index];
		} else {
			return null;
		}
	}
	
	@Override
	public String toString() {
		String o = "[Animation]{";
		for (int i = 0; i < clip.length; i++) {
			o += "\r\n\t[Image](" + clip[i].getWidth() + ", " + clip[i].getHeight() + ") " + clip[i].hashCode() +
					(i >= clip.length - 1 ? "\r\n" : ",");
		}
		return o += "}";
	}
}

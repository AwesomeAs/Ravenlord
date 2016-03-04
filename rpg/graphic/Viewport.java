package graphic;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.function.BiConsumer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import graphic.map.*;
import graphic.ui.*;
import util.ImageManager;

/**
 * Viewport for sorting drawable objects and calling onDraw on them.
 * 
 * @author Andreas
 *
 */
public class Viewport {
	
	private static Viewport instance;
	private JFrame frame;
	private CustomPanel panel;
	private Drawable viewAnchor;
	private List<KeyListener> keylisteners = new ArrayList<KeyListener>();
	
	private ArrayList<Drawable> render;
	private HashMap<Drawable, Long> bdrawTime = new HashMap<Drawable, Long>(); //System.currentTimeMillis();
	private HashMap<Drawable, Long> odrawTime = new HashMap<Drawable, Long>();
	private long drawTime = System.currentTimeMillis();
	
	/**
	 * Gets the singleton instance.
	 * @return Viewport
	 */
	public static Viewport getInstance() {
		if (instance == null) {
			instance = new Viewport();
		}
		return instance;
	}
	
	private Viewport() {
		render = new ArrayList<Drawable>();
		frame = new JFrame("Ravenlord");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new CustomPanel();
		frame.getContentPane().add(panel);
		frame.pack();
		URL iconUrl = getClass().getClassLoader().getResource("icon.png");
		frame.setIconImage(new ImageIcon(iconUrl).getImage());
		frame.setVisible(true);
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyEvtDispatcher());
	}
	
	/**
	 * Add the drawable object to the scene.
	 * @param obj
	 */
	public void add(Drawable obj) {
		if (!render.contains(obj)) {
			render.add(obj);
			if (obj instanceof Group) {
				Drawable[] d = ((Group)obj).getObjects();
				for (int i = 0; i < d.length; i++) {
					if (d[i] instanceof Clickable) {
						panel.add(((Clickable)d[i]).getButton());
					} else if (d[i] instanceof LTextfield) {
						panel.add(((LTextfield)d[i]).getTextfield());
					}
				}
			}
		}
	}
	
	/**
	 * Checks if this drawable object is currently being rendered.
	 * @param obj
	 * @return boolean indicating if the drawable is being rendered.
	 */
	public boolean has(Drawable obj) {
		return render.contains(obj);
	}
	
	/**
	 * Adds a key event listener for the game's window.
	 * @param event listener
	 */
	public void addKeyListener(KeyListener e) {
		keylisteners.add(e);
	}
	
	/**
	 * Gets the width of the game's window.
	 * @return width in pixels
	 */
	public int getWidth() {
		return frame.getExtendedState() == JFrame.MAXIMIZED_BOTH ? frame.getWidth() : 832;
	}
	
	/**
	 * Gets the height of the game's window.
	 * @return height in pixels
	 */
	public int getHeight() {
		return frame.getExtendedState() == JFrame.MAXIMIZED_BOTH ? frame.getHeight() : 640;
	}
	
	/**
	 * The game will render with this element in the center of the window.
	 * @param anchor
	 */
	public void setViewAnchor(Drawable anchor) {
		viewAnchor = anchor;
	}
	
	/**
	 * Adds a button to the game. Used for adding UI button interaction.
	 * @param button
	 */
	public void addToPanel(JButton button) {
		panel.add(button);
	}
	
	/**
	 * Removes the object from the scene
	 * @param obj
	 * @return boolean indicating success for removal.
	 */
	public boolean remove(Drawable obj) {
		if (render.contains(obj)) {
			render.remove(obj);
			if (obj instanceof Clickable) {
				panel.remove(((Clickable)obj).getButton());
			} else if (obj instanceof Group) {
				Drawable[] d = ((Group)obj).getObjects();
				for (int i = 0; i < d.length; i++) {
					if (d[i] instanceof Clickable) {
						panel.remove(((Clickable)d[i]).getButton());
						if (d[i] instanceof LButton) {
							((LButton)d[i]).setHovered(false);
						} else if (d[i] instanceof LToggle) {
							((LToggle)d[i]).setHovered(false);
						}
					} else if (d[i] instanceof LTextfield) {
						panel.remove(((LTextfield)d[i]).getTextfield());
					}
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Sets whether the game is fullscreen or not.
	 * @param fs fullscreen
	 */
	public void setFullscreen(boolean fs) {
		if (fs) {
			frame.dispose();
			frame.setUndecorated(true);
			frame.pack();
			frame.setVisible(true);
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			panel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
		} else {
			frame.dispose();
			frame.setUndecorated(false);
			panel.setPreferredSize(new Dimension(832, 640));
			frame.pack();
			frame.setVisible(true);
			frame.setExtendedState(JFrame.NORMAL);
			panel.setPreferredSize(new Dimension(832, 640));
		}
	}
	
	private class KeyEvtDispatcher implements KeyEventDispatcher {

		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			if (keylisteners.size() > 0) {
				if (e.getID() == KeyEvent.KEY_PRESSED) {
					for(KeyListener kl : keylisteners) {
						kl.keyPressed(e);
					}
				} else if (e.getID() == KeyEvent.KEY_RELEASED) {
					for(KeyListener kl : keylisteners) {
						kl.keyReleased(e);
					}
				} else if (e.getID() == KeyEvent.KEY_TYPED) {
					for(KeyListener kl : keylisteners) {
						kl.keyTyped(e);
					}
				}
			}
			return false;
		}

	}
	
	private class CustomPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		private CustomPanel() {
			
			setPreferredSize(new Dimension(832, 640));
			
			setLayout(null);
			
			MyThread mt = new MyThread();
	        new Thread(mt).start();
			
		}
		
		/**
		 * Draws the game.
		 */
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D)g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(Color.black);
			g2d.fillRect(0, 0, getWidth(), getHeight());
			Hashtable<Integer, ArrayList<Drawable>> crender = new Hashtable<Integer, ArrayList<Drawable>>();
			ArrayList<Integer> zindexes = new ArrayList<Integer>();
			
			
			
			/**
			 * Add current Z-indexes and drawables.
			 */
			Object[] iter = render.toArray();
			for (int i = 0; i < iter.length; i++) {
				if (iter[i] instanceof Group) {
					Drawable[] d = ((Group)iter[i]).getObjects();
					for (int j = 0; j < d.length; j++) {
						int z = d[j].getZIndex();
						if (!zindexes.contains(z)) {
							zindexes.add(z);
						}
						if (crender.get(z) == null) {
							crender.put(z, new ArrayList<Drawable>());
						}
						crender.get(z).add(d[j]);
						d[j].beforeDraw((System.currentTimeMillis() - bdrawTime.getOrDefault(d[j], System.currentTimeMillis())) / 1000f);
						bdrawTime.put(d[j], System.currentTimeMillis());
					}
				} else {
					Drawable d = (Drawable)iter[i];
					int z = d.getZIndex();
					if (!zindexes.contains(z)) {
						zindexes.add(z);
					}
					if (crender.get(z) == null) {
						crender.put(z, new ArrayList<Drawable>());
					}
					crender.get(z).add(d);
					d.beforeDraw((System.currentTimeMillis() - bdrawTime.getOrDefault(d, System.currentTimeMillis())) / 1000f);
					bdrawTime.put(d, System.currentTimeMillis());
				}
			}
			
			/**
			 * Sort Z-indexes.
			 */
			zindexes.sort(new Comparator<Integer>() {

				@Override
				public int compare(Integer arg0, Integer arg1) {
					return arg0.compareTo(arg1);
				}
				
			});
			
			/**
			 * Sort objects by Y position.
			 */
			crender.forEach(new BiConsumer<Integer, ArrayList<Drawable>>() {

				@Override
				public void accept(Integer arg0, ArrayList<Drawable> arg1) {
					arg1.sort(new Comparator<Drawable>() {

						@Override
						public int compare(Drawable o1, Drawable o2) {
							return Double.compare(o1.getY() + o1.getImgHeight(), o2.getY() + o2.getImgHeight());
						}
						
					});
				}
				
			});
			
			/**
			 * Draw the objects.
			 */
			for (int i = 0; i < zindexes.size(); i++) {
				for (int j = 0; j < crender.get(zindexes.get(i)).size(); j++) {
					Drawable o = crender.get(zindexes.get(i)).get(j);
					double x = o.getX();
					double y = o.getY() - o.getGroundOffset();
					if (o instanceof UIElement) {
						int x_off = 0;
						int y_off = 0;
						switch (((UIElement)o).getAnchor()) {
						case BOTTOM_CENTER:
							x_off = getWidth() / 2;
							y_off = getHeight();
							break;
						case BOTTOM_LEFT:
							y_off = getHeight();
							break;
						case BOTTOM_RIGHT:
							x_off = getWidth();
							y_off = getHeight();
							break;
						case CENTER:
							x_off = getWidth() / 2;
							y_off = getHeight() / 2;
							break;
						case CENTER_LEFT:
							y_off = getHeight() / 2;
							break;
						case CENTER_RIGHT:
							x_off = getWidth();
							y_off = getHeight() / 2;
							break;
						case TOP_CENTER:
							x_off = getWidth() / 2;
							break;
						case TOP_RIGHT:
							x_off = getWidth();
							break;
						default:
							break;
						}
						if (o instanceof Clickable) {
							((Clickable)o).getButton().setLocation((int)(x_off + x), (int)(y_off + y));
						} else if (o instanceof LTextfield) {
							((LTextfield)o).getTextfield().setLocation((int)(x_off + x) + 8, (int)(y_off + y));
						}
						g2d.translate(x_off + x, y_off + y);
						o.onDraw(g2d, (System.currentTimeMillis() - odrawTime.getOrDefault(o, System.currentTimeMillis())) / 1000f);
						odrawTime.put(o, System.currentTimeMillis());
						g2d.translate(-x_off - x, -y_off - y);
					} else if (!(o instanceof LightSource)) {
						int x_off = getWidth() / 2;
						int y_off = getHeight() / 2;
						if (viewAnchor != null) {
							x_off -= viewAnchor.getX();
							y_off -= viewAnchor.getY();
						}
						g2d.translate(x + x_off, y + y_off);
						o.onDraw(g2d, (System.currentTimeMillis() - odrawTime.getOrDefault(o, System.currentTimeMillis())) / 1000f);
						odrawTime.put(o, System.currentTimeMillis());
						g2d.translate(-x - x_off, -y - y_off);
					}
				}
			}
			
			/**
			 * Draw map shadowing & lights.
			 */
			for (int i = 0; i < iter.length; i++) {
				if (iter[i] instanceof Map) {
					Map map = (Map)iter[i];
					
					int x_off = getWidth() / 2;
					int y_off = getHeight() / 2;
					if (viewAnchor != null) {
						x_off -= viewAnchor.getX();
						y_off -= viewAnchor.getY();
					}
					BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
					Graphics2D dg = img.createGraphics();
					dg.setColor(map.getColor());
					dg.fillRect(0, 0, getWidth(), getHeight());
					
					int mcol_r = map.getColor().getRed();
					int mcol_g = map.getColor().getGreen();
					int mcol_b = map.getColor().getBlue();
					boolean hasLight = false;
					
					for (int j = 0; j < zindexes.size(); j++) {
						for (int k = 0; k < crender.get(zindexes.get(j)).size(); k++) {
							Drawable o = crender.get(zindexes.get(j)).get(k);
							if (o instanceof LightSource) {
								dg.setComposite(AlphaComposite.getInstance(AlphaComposite.XOR, map.getLightFactor()));
								LightSource l = (LightSource)o;
								RadialGradientPaint p = new RadialGradientPaint(
										(float)l.getX() + x_off + l.getRange(), (float)l.getY() + y_off + l.getRange(),
										l.getRange(), l.getFractions(), l.getColor());
								dg.setPaint(p);
								dg.fillRect(0, 0, getWidth(), getHeight());
								hasLight = true;
							} else if (!(o instanceof UIElement) && hasLight) {
								int w = getWidth();
								int h = getHeight();
								dg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
								BufferedImage timg = ImageManager.getInstance()
										.colorImage(o, w, h, x_off, y_off, mcol_r, mcol_g, mcol_b);
						        dg.drawImage(timg, 0, 0, null);
							}
						}
					}
					
					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, map.getDarkness()));
					
					dg.dispose();
					g2d.drawImage(img, 0, 0, null);
					
					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				}
			}
			
			g2d.dispose();
		}
		
	}
	
	/**
	 * Keep drawing, every 10ms.
	 * @author Andreas
	 *
	 */
	private class MyThread implements Runnable {
		@Override
		public void run() {
			while (true) {
				panel.repaint();
				try {
					Thread.sleep(5 - Math.min(5, System.currentTimeMillis() - drawTime));
				} catch (InterruptedException e) {}
				drawTime = System.currentTimeMillis();
			}
		}
	}
	
}

package graphic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.function.BiConsumer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import graphic.ui.*;

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
	
	private ArrayList<Drawable> render;
	
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
						if (d[i] instanceof LTextfield) {
							System.out.println("Hello");
							panel.add(((LTextfield)d[i]).getTextfield());
							System.out.println(((LTextfield)d[i]).getTextfield().getParent());
						}
					}
				}
			}
		}
	}
	
	public int getWidth() {
		return 832;
	}
	
	public int getHeight() {
		return 640;
	}
	
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
						} else if (d[i] instanceof LTextfield) {
							panel.remove(((LTextfield)d[i]).getTextfield());
						}
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
			frame.setVisible(false);
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
			frame.setVisible(true);
			panel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
		} else {
			frame.setVisible(false);
			frame.setExtendedState(JFrame.NORMAL); 
			frame.setVisible(true);
			panel.setPreferredSize(new Dimension(832, 640));
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
			g2d.clearRect(0, 0, getWidth(), getHeight());
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
					// TODO: Set screen position for the object.
					double x = o.getX();
					double y = o.getY() - o.getGroundOffset();
					g2d.translate(x, y);
					o.onDraw(g2d);
					g2d.translate(-x, -y);
				}
			}
			
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
					Thread.sleep(10);
				} catch (InterruptedException e) {}
			}
		}
	}
	
}

package graphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
	private Drawable viewAnchor;
	private KeyListener keylistener;
	
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
		keylistener = e;
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
			if (keylistener != null) {
				if (e.getID() == KeyEvent.KEY_PRESSED) {
					keylistener.keyPressed(e);
				} else if (e.getID() == KeyEvent.KEY_RELEASED) {
					keylistener.keyReleased(e);
				} else if (e.getID() == KeyEvent.KEY_TYPED) {
					keylistener.keyTyped(e);
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
						o.onDraw(g2d);
						g2d.translate(-x_off - x, -y_off - y);
					} else {
						int x_off = getWidth() / 2;
						int y_off = getHeight() / 2;
						if (viewAnchor != null) {
							x_off += viewAnchor.getX();
							y_off += viewAnchor.getY();
						}
						g2d.translate(x + x_off, y + y_off);
						o.onDraw(g2d);
						g2d.translate(-x - x_off, -y - y_off);
					}
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

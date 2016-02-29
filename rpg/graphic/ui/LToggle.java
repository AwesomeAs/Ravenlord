package graphic.ui;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;

public class LToggle extends Clickable {
	
	private boolean hovered = false;
	private boolean active = false;
	private ImageIcon img;
	
	public LToggle(AnchorPoint anchor, int x, int y, boolean active) {
		img = new ImageIcon("resources/ui/MainT.png");
		this.active = active;
		LToggle diz = this;
		super.setAnchor(anchor);
		super.setPosition(x, y);
		super.setZIndex(20);
		super.button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		super.button.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				diz.active = !diz.active;
				if (diz.bclb != null) {
					diz.bclb.onClick();
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				hovered = true;
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				hovered = false;
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			
		});
		super.button.setSize(34, 34);
		super.button.setLocation((int)getX(), (int)getY());
	}
	
	public LToggle setSize(int width, int height) {
		super.button.setSize(width, height);
		return this;
	}
	
	public void setHovered(boolean hovered) {
		this.hovered = hovered;
	}
	
	public boolean getValue() {
		return active;
	}

	@Override
	public void onDraw(Graphics2D g) {
		g.setClip(0, 0, button.getWidth(), button.getHeight());
		g.drawImage(img.getImage(), active ? 0 : -34, hovered ? -34 : 0, button.getWidth() * 2, button.getHeight() * 2, img.getImageObserver());
		g.setClip(null);
	}
	
}

package graphic.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LSlider extends Clickable {
	
	private boolean hovered = false;
	private boolean active = false;
	private float value = 1f;
	
	public LSlider(AnchorPoint anchor, int x, int y, float value) {
		this.value = value;
		LSlider diz = this;
		super.setAnchor(anchor);
		super.setPosition(x, y);
		super.setZIndex(20);
		super.button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		super.button.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
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
				active = true;
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				active = false;
			}
			
		});
		super.button.setSize(372, 16);
		super.button.setLocation((int)getX(), (int)getY());
	}
	
	public LSlider setSize(int width, int height) {
		super.button.setSize(width, height);
		return this;
	}
	
	public void setHovered(boolean hovered) {
		this.hovered = hovered;
	}

	@Override
	public void onDraw(Graphics2D g) {
		if (active && button.getParent() != null) {
			int x = MouseInfo.getPointerInfo().getLocation().x - button.getLocationOnScreen().x;
			value = Math.max(0f, Math.min(1f, (x - 3f) / (button.getWidth() - 6f)));
		}
		g.setClip(0, 0, button.getWidth(), button.getHeight());
		g.setColor(Color.white);
		g.fillRoundRect(0, 0, button.getWidth(), button.getHeight(), 6, 6);
		g.setColor(hovered ? (active ? new Color(30, 30, 30) : new Color(47, 47, 47)) : new Color(117, 117, 117));
		g.fillRoundRect(2, 2, button.getWidth() - 4, button.getHeight() - 4, 5, 5);
		g.setColor(active ? new Color(255, 255, 255, 100) : new Color(0, 0, 0, 100));
		g.fillRoundRect(3, 3, (int)((button.getWidth() - 6) * value), button.getHeight() - 6, 4, 4);
		g.setColor(new Color(0, 255, 128, 150));
		g.fillRoundRect(3, button.getHeight() - 4, (int)((button.getWidth() - 6) * value), 2, 4, 4);
		g.setClip(null);
	}
	
}

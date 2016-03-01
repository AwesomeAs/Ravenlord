package graphic.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;

import util.FontManager;
import util.ImageManager;

public class LButton extends Clickable {
	
	private boolean hovered = false;
	private ImageIcon img;
	private boolean imgactive = true;
	private String text;
	private Font font;
	
	public LButton(String text, AnchorPoint anchor, int x, int y, boolean small, int fontSize) {
		img = ImageManager.getInstance().get(small ? "resources/ui/MainSB.png" : "resources/ui/MainB.png");
		font = FontManager.getInstance().get("Amatic-Bold.ttf", fontSize);
		LButton diz = this;
		this.text = text;
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
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			
		});
		super.button.setSize(small ? 332 : 572, 64);
		super.button.setLocation((int)getX(), (int)getY());
	}
	
	public LButton(String text, AnchorPoint anchor, int x, int y, boolean small) {
		this(text, anchor, x, y, small, 32);
	}
	
	public LButton(String text, AnchorPoint anchor, int x, int y) {
		this(text, anchor, x, y, false);
	}
	
	public LButton(String text, int x, int y) {
		this(text, AnchorPoint.TOP_LEFT, x, y);
	}
	
	public LButton setBGActive(boolean active) {
		imgactive = active;
		return this;
	}
	
	public LButton setSize(int width, int height) {
		super.button.setSize(width, height);
		return this;
	}
	
	public void setHovered(boolean hovered) {
		this.hovered = hovered;
	}
	
	@Override
	public void onDraw(Graphics2D g, float delta) {
		g.setClip(0, 0, button.getWidth(), button.getHeight());
		if (imgactive) {
			g.drawImage(img.getImage(), 0, hovered ? -128 : 0, button.getWidth(), button.getHeight() * 4,
					img.getImageObserver());
		}
		g.setColor(new Color(1f, 1f, 1f));
		g.setFont(font);
		g.drawString(text, button.getWidth() / 2 - g.getFontMetrics().stringWidth(text) / 2, button.getHeight() / 2 + 10);
		g.setClip(null);
	}

}

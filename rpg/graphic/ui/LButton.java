package graphic.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;

import util.FontManager;

public class LButton extends Clickable {
	
	private boolean hovered = false;
	private ImageIcon img;
	private String text;
	private Font font;
	
	public LButton(String text, int x, int y) {
		img = new ImageIcon("resources/ui/MainB.png");
		font = new FontManager("Amatic-Bold.ttf", 32).get();
		LButton diz = this;
		this.text = text;
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
		super.button.setSize(572, 64);
		super.button.setLocation((int)getX(), (int)getY());
	}
	
	public LButton setSize(int width, int height) {
		super.button.setSize(width, height);
		return this;
	}
	
	public void setHovered(boolean hovered) {
		this.hovered = hovered;
	}
	
	@Override
	public void onDraw(Graphics2D g) {
		//g.setColor(hovered ? new Color(255, 255, 255, 100) : new Color(0, 0, 0, 100));
		//g.fillRoundRect(0, 0, super.button.getWidth(), super.button.getHeight(), 5, 5);
		g.setClip(0, 0, button.getWidth(), button.getHeight());
		g.drawImage(img.getImage(), 0, hovered ? -128 : 0, button.getWidth(), button.getHeight() * 4,
				img.getImageObserver());
		g.setColor(new Color(1f, 1f, 1f));
		g.setFont(font);
		g.drawString(text, button.getWidth() / 2 - g.getFontMetrics().stringWidth(text) / 2, button.getHeight() / 2 + 10);
		g.setClip(null);
		//g.drawImage(img.getImage(), 0, 0, 200, 50, 700, 256, 200, 50, img.getImageObserver());
	}

}

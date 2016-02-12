package graphic.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import graphic.Viewport;

public class LButton extends Clickable {
	
	public LButton(String text, int x, int y) {
		super.setPosition(x, y);
		super.setZIndex(20);
		super.button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Event: " + e);
			}
			
		});
		super.button.setSize(200, 50);
		super.button.setLocation((int)getX(), (int)getY());
		Viewport.getInstance().addToPanel(super.button);
	}
	
	public LButton setSize(int width, int height) {
		super.button.setSize(width, height);
		return this;
	}
	
	@Override
	public void onDraw(Graphics2D g) {
		g.setColor(new Color(255, 0, 0, 1));
		g.fillRect(0, 0, 64, 64);
		g.setColor(new Color(0, 0, 0, 100));
		g.fillRoundRect(0, 0, super.button.getWidth(), super.button.getHeight(), 5, 5);
	}

}

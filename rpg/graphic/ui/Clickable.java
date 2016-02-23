package graphic.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import graphic.Drawable;

public abstract class Clickable extends Drawable {
	
	protected CustomButton button = new CustomButton();
	protected ButtonCallback bclb;
	
	public CustomButton getButton() {
		return button;
	}
	
	public Clickable setCallback(ButtonCallback callback) {
		this.bclb = callback;
		return this;
	}
	
	protected class CustomButton extends JButton implements MouseListener {
		private static final long serialVersionUID = 1L;
		
		private CustomButton() {
			setBorder(null);
		}
		
		public void paintComponent(Graphics g) { 
			// invisible
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
		
	}
	
}

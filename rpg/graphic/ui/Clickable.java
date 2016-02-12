package graphic.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import graphic.Drawable;

public abstract class Clickable extends Drawable {
	
	protected CustomButton button = new CustomButton();
	
	public CustomButton getButton() {
		return button;
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
			// TODO Auto-generated method stub
			System.out.println("CLICK: " + e);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("ENTER: " + e);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("EXIT: " + e);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}

package graphic.ui;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;
import javax.swing.text.Caret;

import javafx.geometry.Insets;
import util.FontManager;

public class LTextfield extends UIElement {
	
	private CustomTextfield textfield = new CustomTextfield();
	private String text = "";
	private Font font;
	private int width = 400;
	private int height = 30;
	private Insets padding = new Insets(10);
	private boolean isPassword = false;
	private boolean hovered = false;
	
	public LTextfield(int x, int y, int fontSize, boolean isPassword) {
		font = new FontManager("Amatic-Bold.ttf", fontSize).get();
		this.isPassword = isPassword;
		super.setPosition(x, y);
		super.setZIndex(20);
		textfield.setSize(400, 30);
		textfield.setLocation(x, y);
		Toolkit.getDefaultToolkit().addAWTEventListener(
		          new AWTEventListener() {

					@Override
					public void eventDispatched(AWTEvent arg) {
						if (arg.getID() == 501 && !hovered) {
							textfield.transferFocus();
						}
					}
		        	  
		          }, AWTEvent.MOUSE_EVENT_MASK | AWTEvent.FOCUS_EVENT_MASK);
	}
	
	public LTextfield(int x, int y) {
		this(x, y, 24, false);
	}
	
	public LTextfield setBorder(int top, int left, int bottom, int right) {
		padding = new Insets(top, right, bottom, left);
		return this;
	}
	
	public String getText() {
		return text;
	}
	
	public LTextfield setSize(int width, int height) {
		this.width = width;
		this.height = height;
		textfield.setSize(width, height);
		return this;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public JTextField getTextfield() {
		return textfield;
	}
	
	@Override
	public void onDraw(Graphics2D g) {
		g.setColor(new Color(0f, 0f, 0f, 0.5f));
		g.fillRoundRect(0, 0, width, height, 6, 6);
		g.setFont(font);
		int x = (int)padding.getLeft();
		int y = (int)padding.getTop() + 14;
		
		text = textfield.getText();
		
		String line;
		if (isPassword) {
			line = "";
			for (int i = 0; i < text.length(); i++) {
				line += "•";
			}
		} else {
			line = text;
		}
		Caret caret = textfield.getCaret();
		line = line.substring(0, textfield.getCaretPosition()) + (caret.isVisible() ? "|" : "") + line.substring(textfield.getCaretPosition());
		
		g.setColor(new Color(0f, 0f, 0f, 0.7f));
		g.drawString(line, x + 1, y + 1);
		g.drawString(line, x + 2, y + 3);
		g.setColor(new Color(1f, 1f, 1f));
		g.drawString(line, x, y);
	}
	
	protected class CustomTextfield extends JTextField {
		private static final long serialVersionUID = 1L;
		
		private CustomTextfield() {
			setBorder(null);
			addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					hovered = true;
				}

				@Override
				public void mouseExited(MouseEvent e) {
					hovered = false;
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}
				
			});
		}
		
		public void paintComponent(Graphics g) { 
			// invisible
		}
		
		
		
	}

}

package graphic.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JTextField;

import graphic.Drawable;
import javafx.geometry.Insets;
import util.FontManager;

public class LTextfield extends Drawable {
	
	private CustomTextfield textfield = new CustomTextfield();
	private String text = "";
	private Font font;
	private int width = 400;
	private int height = 30;
	private Insets padding = new Insets(10);
	private boolean isPassword = false;
	
	public LTextfield(int x, int y, int fontSize, boolean isPassword) {
		font = new FontManager("Amatic-Bold.ttf", fontSize).get();
		this.isPassword = isPassword;
		super.setPosition(x, y);
		super.setZIndex(20);
		textfield.setSize(400, 30);
		textfield.setLocation(x, y);
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
		int y = (int)padding.getTop() + 30;
		
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
		}
		
		public void paintComponent(Graphics g) { 
			// invisible
		}
		
	}

}

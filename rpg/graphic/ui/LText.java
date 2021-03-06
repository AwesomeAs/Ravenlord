package graphic.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javafx.geometry.Insets;
import util.FontManager;

public class LText extends UIElement {
	
	private String text;
	private Font font;
	private int width = 200;
	private int height = 50;
	private float bgAlpha = 0.7f;
	private Insets padding = new Insets(10);
	
	public LText(String text, AnchorPoint anchor, int x, int y, int fontSize, float bgAlpha) {
		font = FontManager.getInstance().get("Amatic-Bold.ttf", fontSize);
		this.text = text;
		this.bgAlpha = bgAlpha;
		super.setAnchor(anchor);
		super.setPosition(x, y);
		super.setZIndex(20);
	}
	
	public LText(String text, int x, int y, int fontSize, float bgAlpha) {
		this(text, AnchorPoint.TOP_LEFT, x, y, fontSize, bgAlpha);
	}
	
	public LText(String text, AnchorPoint anchor, int x, int y) {
		this(text, anchor, x, y, 32, 0.5f);
	}
	
	public LText(String text, int x, int y) {
		this(text, AnchorPoint.TOP_LEFT, x, y);
	}
	
	public LText setBorder(int top, int left, int bottom, int right) {
		padding = new Insets(top, right, bottom, left);
		return this;
	}
	
	public LText setSize(int width, int height) {
		this.width = width;
		this.height = height;
		return this;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public void onDraw(Graphics2D g, float delta) {
		g.setColor(new Color(0f, 0f, 0f, bgAlpha));
		g.fillRoundRect(0, 0, width, height, 5, 5);
		g.setFont(font);
		int x = (int)padding.getLeft();
		int y = (int)padding.getTop() + 30;
		
		for (String line : text.split("\n")) {
			g.setColor(new Color(0f, 0f, 0f, 0.7f));
			g.drawString(line, x + 1, y + 1);
			g.drawString(line, x + 2, y + 3);
			g.setColor(new Color(1f, 1f, 1f));
			g.drawString(line, x, y);
			y += g.getFontMetrics().getHeight();
		}
		
	}

}

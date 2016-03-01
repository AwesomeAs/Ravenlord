package graphic.map;

import java.awt.Color;
import java.awt.Graphics2D;

import graphic.Drawable;

public class LightSource extends Drawable {
	
	private float range = 64;
	private Color[] dim = {
			new Color(1f, 0f, 0f, 0.75f),
			new Color(1f, 0.5f, 0f, 0.5f),
			new Color(1f, 1f, 0f, 0f)
	};
	private float[] dimf = {0f, 0.625f, 1f};
	
	public LightSource(int x, int y) {
		super.setPosition(x, y);
		super.setImgHeight(128);
	}
	
	public LightSource setRange(float value) {
		range = value;
		super.setImgHeight(range * 2);
		return this;
	}
	
	public LightSource setColor(Color[] colors, float[] fractions) {
		if (colors.length >= 2 && fractions.length >= 2) {
			dim = colors;
			dimf = fractions;
		}
		return this;
	}
	
	public float getRange() {
		return range;
	}
	
	public Color[] getColor() {
		return dim;
	}
	
	public float[] getFractions() {
		return dimf;
	}

	@Override
	public void onDraw(Graphics2D g, float delta) {
		
	}

}

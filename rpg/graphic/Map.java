package graphic;

import java.awt.Color;

public class Map extends Group {
	
	private float darkness = 0f;
	private Color darknessColor = new Color(0, 0, 0);
	
	public void setDarkness(float value) {
		darkness = value;
	}
	
	public void setColor(Color color) {
		darknessColor = color;
	}
	
	public float getDarkness() {
		return darkness;
	}
	
	public Color getColor() {
		return darknessColor;
	}
	
}

package util;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class FontManager {
	
	private static FontManager self;
	private HashMap<String, Font> fonts = new HashMap<String, Font>();
	
	private Font loadFont(String path, int size) {
		Font font = null;
		InputStream fontStream = getClass().getClassLoader().getResourceAsStream("fonts/" + path);
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, fontStream);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		GraphicsEnvironment graph = GraphicsEnvironment.getLocalGraphicsEnvironment();
		graph.registerFont(font);
		Map<TextAttribute, Object> attr = new HashMap<TextAttribute, Object>();
		attr.put(TextAttribute.SIZE, size);
		font = font.deriveFont(attr);
		return font;
	}
	
	public Font get(String path, int size) {
		if (!fonts.containsKey(path + "|" + size)) {
			fonts.put(path + "|" + size, loadFont(path, size));
		}
		return fonts.get(path + "|" + size);
	}
	
	public static FontManager getInstance() {
		if (self == null) {
			self = new FontManager();
		}
		return self;
	}
}

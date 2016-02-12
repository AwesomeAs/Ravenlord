package game;

import graphic.Viewport;
import graphic.map.*;
import graphic.ui.*;

public class Main {

	public static void main(String[] args) {
		Viewport view = Viewport.getInstance();
		for (int x = 0; x < 800; x += 64) {
			for (int y = 0; y < 600; y += 64) {
				view.add(new Grass(x, y));
			}
		}
		for (int x = 64; x < 800 - 64; x += 64) {
			view.add(new CobbleWall(x, 0, (x > 64 ? (x < 800 - 128 ? "CTM" : "CTR") : "CTL")));
		}
		for (int x = 64; x < 800 - 64; x += 64) {
			for (int y = 64; y < 512; y += 64) {
				view.add(new CobbleWall(x, y, (x > 64 ? (x < 800 - 128 ? "CMUM" : "CMUR") : "CMUL")));
			}
		}
		for (int x = 64; x < 800 - 64; x += 64) {
			view.add(new CobbleWall(x, 512, (x > 64 ? (x < 800 - 128 ? "CBM" : "CBR") : "CBL")));
		}
		
		view.add(new LButton("Test", view.getWidth() / 2 - 200, view.getHeight() / 2 - 30).setSize(400, 60));
	}

}

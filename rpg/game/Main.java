package game;

import graphic.Group;
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
		
		Group main_g = new Group();
		Group cred_g = new Group();
		main_g.add(new LButton("Play game", view.getWidth() / 2 - 286, view.getHeight() / 2 - 82).setSize(572, 64).setCallback(new ButtonCallback() {

			@Override
			public void onClick() {
				System.out.println("Play game");
			}
			
		}));
		main_g.add(new LButton("Settings", view.getWidth() / 2 - 286, view.getHeight() / 2 - 22).setSize(572, 64).setCallback(new ButtonCallback() {

			@Override
			public void onClick() {
				System.out.println("Settings");
			}
			
		}));
		main_g.add(new LButton("Credits", view.getWidth() / 2 - 286, view.getHeight() / 2 + 42).setSize(572, 64).setCallback(new ButtonCallback() {

			@Override
			public void onClick() {
				view.remove(main_g);
				view.add(cred_g);
			}
			
		}));
		cred_g.add(new LButton("Main menu", view.getWidth() / 2 - 286, view.getHeight() / 2 + 100).setSize(572, 64).setCallback(new ButtonCallback() {

			@Override
			public void onClick() {
				view.remove(cred_g);
				view.add(main_g);
			}
			
		}));
		cred_g.add(new LText("This is a credits page\nHow's life? This seems fun, haha.\nI wonder if we can get text alignment.",
				view.getWidth() / 2 - 286,
				view.getHeight() / 2 - 160).setSize(572, 250));
		cred_g.add(new LText("Credits", view.getWidth() / 2 - 286, view.getHeight() / 2 - 210, 48, 0f).setSize(572, 50));
		view.add(main_g);
	}

}

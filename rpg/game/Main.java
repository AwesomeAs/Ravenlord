package game;

import graphic.Group;
import graphic.Viewport;
import graphic.map.*;
import graphic.ui.*;

public class Main {

	public static void main(String[] args) {
		Viewport view = Viewport.getInstance();
		Group mainbg_g = new Group();
		for (int x = 0; x < 800; x += 64) {
			for (int y = 0; y < 600; y += 64) {
				mainbg_g.add(new Grass(x, y));
			}
		}
		for (int x = 64; x < 800 - 64; x += 64) {
			mainbg_g.add(new CobbleWall(x, 0, (x > 64 ? (x < 800 - 128 ? "CTM" : "CTR") : "CTL")));
		}
		for (int x = 64; x < 800 - 64; x += 64) {
			for (int y = 64; y < 512; y += 64) {
				mainbg_g.add(new CobbleWall(x, y, (x > 64 ? (x < 800 - 128 ? "CMUM" : "CMUR") : "CMUL")));
			}
		}
		for (int x = 64; x < 800 - 64; x += 64) {
			mainbg_g.add(new CobbleWall(x, 512, (x > 64 ? (x < 800 - 128 ? "CBM" : "CBR") : "CBL")));
		}
		mainbg_g.add(new Fireplace(64, 640 - 128));
		view.add(mainbg_g);
		
		Group game_map = new Group();
		for (int x = 0; x < 800; x += 64) {
			for (int y = 0; y < 600; y += 64) {
				game_map.add(new Grass(x, y));
			}
		}
		
		game_map.add(new TreeG(5 * 64, 4 * 64, 2));
		game_map.add(new TreeG(8 * 64, 8 * 64, 3));
		game_map.add(new TreeG(2 * 64, 2 * 64, 1));
		game_map.add(new TreeG(4 * 64, -2 * 64, 1));
		game_map.add(new TreeG(12 * 64, 3 * 64, 2));
		game_map.add(new TreeG(3 * 64, 1 * 64, 3));
		game_map.add(new Fireplace(64, 640 - 128));
		game_map.add(new Fireplace(384, 640 - 192));
		
		Group main_g = new Group();
		Group cred_g = new Group();
		Group sett_g = new Group();
		main_g.add(new LButton("Play game", view.getWidth() / 2 - 286, view.getHeight() / 2 - 82).setSize(572, 64).setCallback(new ButtonCallback() {

			@Override
			public void onClick() {
				view.remove(main_g);
				view.remove(mainbg_g);
				view.add(game_map);
			}
			
		}));
		main_g.add(new LButton("Settings", view.getWidth() / 2 - 286, view.getHeight() / 2 - 22).setSize(572, 64).setCallback(new ButtonCallback() {

			@Override
			public void onClick() {
				view.remove(main_g);
				view.add(sett_g);
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
		sett_g.add(new LButton("Main menu", view.getWidth() / 2 - 286, view.getHeight() / 2 + 100).setSize(572, 64).setCallback(new ButtonCallback() {

			@Override
			public void onClick() {
				view.remove(sett_g);
				view.add(main_g);
			}
			
		}));
		sett_g.add(new LText("Settings", view.getWidth() / 2 - 286, view.getHeight() / 2 - 210, 48, 0f).setSize(572, 50));
		sett_g.add(new LText("Brightness", view.getWidth() / 2 - 286, view.getHeight() / 2 - 160, 28, 0f).setSize(572, 30));
		sett_g.add(new LSlider(view.getWidth() / 2 - 150, view.getHeight() / 2 - 135, 0.5f));
		sett_g.add(new LText("Craze mode", view.getWidth() / 2 - 286, view.getHeight() / 2 - 120, 28, 0f).setSize(572, 30));
		sett_g.add(new LToggle(view.getWidth() / 2 - 150, view.getHeight() / 2 - 110, false));
		sett_g.add(new LText("Fullscreen", view.getWidth() / 2 - 286, view.getHeight() / 2 - 80, 28, 0f).setSize(572, 30));
		sett_g.add(new LToggle(view.getWidth() / 2 - 150, view.getHeight() / 2 - 70, false).setCallback(new ButtonCallback() {

			@Override
			public void onClick() {
				view.setFullscreen(((LToggle)sett_g.getObjects()[7]).getValue());
			}
			
		}));
		view.add(main_g);
	}

}

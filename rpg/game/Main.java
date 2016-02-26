package game;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import graphic.Group;
import graphic.Viewport;
import graphic.map.*;
import graphic.ui.*;
import wsd_api.*;

public class Main {

	public static void main(String[] args) {
		Viewport view = Viewport.getInstance();
		WSDAPI userAPI = WSDAPI.getInstance();
		userAPI.logout();
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
		for (int x = 0; x < 2500; x += 64) {
			for (int y = 0; y < 1400; y += 64) {
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
		Group login_g = new Group();
		main_g.add(new LButton("Play game", view.getWidth() / 2 - 286, view.getHeight() / 2 - 82).setSize(572, 64).setCallback(new ButtonCallback() {

			@Override
			public void onClick() {
				if (userAPI.isOnline()) {
					view.remove(main_g);
					view.remove(mainbg_g);
					view.add(game_map);
				} else {
					view.remove(main_g);
					view.add(login_g);
				}
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
		login_g.add(new LText("", view.getWidth() / 2 - 296, view.getHeight() / 2 - 220).setSize(592, 220));
		login_g.add(new LText("Login", view.getWidth() / 2 - 286, view.getHeight() / 2 - 210, 48, 0f).setSize(572, 50));
		login_g.add(new LText("Username:", view.getWidth() / 2 - 286, view.getHeight() / 2 - 160, 28, 0f).setSize(572, 30));
		login_g.add(new LTextfield(view.getWidth() / 2 - 186, view.getHeight() / 2 - 140, 24, false));
		login_g.add(new LText("Password:", view.getWidth() / 2 - 286, view.getHeight() / 2 - 110, 28, 0f).setSize(572, 30));
		login_g.add(new LTextfield(view.getWidth() / 2 - 186, view.getHeight() / 2 - 90, 24, true));
		login_g.add(new LText("", view.getWidth() / 2 - 286, view.getHeight() / 2 - 60, 22, 0f).setSize(572, 30));
		login_g.add(new LButton("Create account", view.getWidth() / 2 + 196, view.getHeight() / 2 - 40, false, 22)
				.setBGActive(false).setSize(85, 20).setCallback(new ButtonCallback() {

					@Override
					public void onClick() {
						try {
							Desktop.getDesktop().browse(new URI("http://www.whysodanish.com/Forum/register.php"));
						} catch (IOException | URISyntaxException e) {
						}
					}
					
				}));
		
		login_g.add(new LButton("Login", view.getWidth() / 2 - 326, view.getHeight() / 2 + 50, true).setSize(332, 64).setCallback(new ButtonCallback() {

			@Override
			public void onClick() {
				
				APIResponse resp = userAPI.login(
						((LTextfield)login_g.getObjects()[3]).getText(),
						((LTextfield)login_g.getObjects()[5]).getText()
				);
				
				if (resp.success()) {
					view.remove(login_g);
					view.remove(mainbg_g);
					view.add(game_map);
				} else {
					((LText)login_g.getObjects()[6]).setText(resp.getError());
				}
			}
			
		}));
		login_g.add(new LButton("Main menu", view.getWidth() / 2 - 6, view.getHeight() / 2 + 50, true).setSize(332, 64)
				.setCallback(new ButtonCallback() {

			@Override
			public void onClick() {
				view.remove(login_g);
				view.add(main_g);
			}
			
		}));
		
		view.add(main_g);
	}

}

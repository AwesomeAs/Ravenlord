package game;

import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import character.Controllable;
import character.Player;
import graphic.*;
import graphic.map.*;
import graphic.ui.*;
import graphic.ui.UIElement.AnchorPoint;
import wsd_api.*;

public class Main {

	public static void main(String[] args) {
		Viewport view = Viewport.getInstance();
		WSDAPI userAPI = WSDAPI.getInstance();
		Group mainbg_g = new Group();
		Player player = new Player("Player 1", Controllable.Direction.DOWN, 0, 0);
		for (int x = -1600; x < 1600; x += 64) {
			for (int y = -1200; y < 1200; y += 64) {
				mainbg_g.add(new Grass(x + 32, y + 32));
			}
		}
		for (int x = -384; x < 384; x += 64) {
			mainbg_g.add(new CobbleWall(x + 32, -256 + 32 - 64, (x > -384 ? (x < 384 - 64 ? "CTM" : "CTR") : "CTL")));
		}
		for (int x = -384; x < 384; x += 64) {
			for (int y = -256; y < 256 - 64; y += 64) {
				mainbg_g.add(new CobbleWall(x + 32, y + 32, (x > -384 ? (x < 384 - 64 ? "CMUM" : "CMUR") : "CMUL")));
			}
		}
		for (int x = -384; x < 384; x += 64) {
			mainbg_g.add(new CobbleWall(x + 32, 256 - 32, (x > -384 ? (x < 384 - 64 ? "CBM" : "CBR") : "CBL")));
		}
		mainbg_g.add(new Fireplace(-384 + 128, 192 + 64));
		view.add(mainbg_g);

		Map game_map = new Map();
		game_map.setDarkness(0.75f);
		for (int x = -40 * 64; x < 40 * 64; x += 64) {
			for (int y = -30 * 64; y < 30 * 64; y += 64) {
				game_map.add(new Grass(x, y));
			}
		}

		game_map.add(new TreeG(5 * 64, -4 * 64, 2));
		game_map.add(new TreeG(7 * 64, 5 * 64, 3));
		game_map.add(new TreeG(-6 * 64, -2 * 64, 1));
		game_map.add(new TreeG(-2 * 64, -8 * 64, 1));
		game_map.add(new TreeG(3 * 64, 7 * 64, 2));
		game_map.add(new TreeG(-12 * 64, -7 * 64, 3));
		game_map.add(new Fireplace(-5 * 64, 1 * 64));
		game_map.add(new Fireplace(5 * 64, 4 * 64));
		game_map.add(new LightSource(-5 * 64, 1 * 64));
		game_map.add(new LightSource(5 * 64, 4 * 64));
		game_map.add(player);

		Group main_g = new Group();
		Group cred_g = new Group();
		Group sett_g = new Group();
		Group login_g = new Group();

		view.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					if (view.has(main_g)) {
						System.exit(0);
					} else {
						view.remove(game_map);
						view.remove(cred_g);
						view.remove(sett_g);
						view.remove(login_g);
						view.add(mainbg_g);
						view.add(main_g);
						view.setViewAnchor(null);
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

		});
		view.addKeyListener(new KeyListener() {
			Set<Integer> pressed = new HashSet<Integer>();

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				pressed.remove(e.getKeyCode());
				if(pressed.size() == 0) {
					player.setWalking(false);
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				pressed.add(e.getKeyCode());
				if (pressed.size() > 0) {
					for (Integer ke : pressed) {
						if (ke == KeyEvent.VK_W || ke == KeyEvent.VK_UP) {
							player.setDirection(Controllable.Direction.UP);
							player.setWalking(true);
							player.moveTo(0, -200, true);
						}
						if (ke == KeyEvent.VK_S || ke == KeyEvent.VK_DOWN) {
							player.setDirection(Controllable.Direction.DOWN);
							player.setWalking(true);
							player.moveTo(0, 200, true);
						}
						if (ke == KeyEvent.VK_A || ke == KeyEvent.VK_LEFT) {
							player.setDirection(Controllable.Direction.LEFT);
							player.setWalking(true);
							player.moveTo(-200, 0, true);
						}
						if (ke == KeyEvent.VK_D || ke == KeyEvent.VK_RIGHT) {
							player.setDirection(Controllable.Direction.RIGHT);
							player.setWalking(true);
							player.moveTo(200, 0, true);
						}
					}
				}
			}
		});
		main_g.add(new LButton("Play game", AnchorPoint.CENTER, -286, -82).setSize(572, 64)
				.setCallback(new ButtonCallback() {

					@Override
					public void onClick() {
						if (userAPI.isOnline()) {
							view.remove(main_g);
							view.remove(mainbg_g);
							view.add(game_map);
							view.setViewAnchor(player);
						} else {
							view.remove(main_g);
							view.add(login_g);
						}
					}

				}));
		main_g.add(new LButton("Settings", AnchorPoint.CENTER, -286, -22).setSize(572, 64)
				.setCallback(new ButtonCallback() {

					@Override
					public void onClick() {
						view.remove(main_g);
						view.add(sett_g);
					}

				}));
		main_g.add(
				new LButton("Credits", AnchorPoint.CENTER, -286, 42).setSize(572, 64).setCallback(new ButtonCallback() {

					@Override
					public void onClick() {
						view.remove(main_g);
						view.add(cred_g);
					}

				}));
		cred_g.add(new LButton("Main menu", AnchorPoint.CENTER, -286, 100).setSize(572, 64)
				.setCallback(new ButtonCallback() {

					@Override
					public void onClick() {
						view.remove(cred_g);
						view.add(main_g);
					}

				}));
		cred_g.add(new LText(
				"This is a credits page\nHow's life? This seems fun, haha.\nI wonder if we can get text alignment.",
				AnchorPoint.CENTER, -286, -160).setSize(572, 250));
		cred_g.add(new LText("Credits", AnchorPoint.CENTER, -286, -210, 48, 0f).setSize(572, 50));
		sett_g.add(new LButton("Main menu", AnchorPoint.CENTER, -286, 100).setSize(572, 64)
				.setCallback(new ButtonCallback() {

					@Override
					public void onClick() {
						view.remove(sett_g);
						view.add(main_g);
					}

				}));
		sett_g.add(new LText("Settings", AnchorPoint.CENTER, -286, -210, 48, 0f).setSize(572, 50));
		sett_g.add(new LText("Brightness", AnchorPoint.CENTER, -286, -160, 28, 0f).setSize(572, 30));
		sett_g.add(new LSlider(AnchorPoint.CENTER, -150, -135, 0.5f).addChangeListener(new ButtonCallback() {

			@Override
			public void onClick() {
			}

			@Override
			public void onClick(Object... caller) {
				game_map.setDarkness(0.95f - ((LSlider) caller[0]).getValue() * 0.65f);
				game_map.setLightFactor(Math.min(1f, ((LSlider) caller[0]).getValue() * 2f));
			}

		}));
		sett_g.add(new LText("Craze mode", AnchorPoint.CENTER, -286, -120, 28, 0f).setSize(572, 30));
		sett_g.add(new LToggle(AnchorPoint.CENTER, -150, -110, false));
		sett_g.add(new LText("Fullscreen", AnchorPoint.CENTER, -286, -80, 28, 0f).setSize(572, 30));
		sett_g.add(new LToggle(AnchorPoint.CENTER, -150, -70, false).setCallback(new ButtonCallback() {

			@Override
			public void onClick() {
				view.setFullscreen(((LToggle) sett_g.getObjects()[7]).getValue());
			}

		}));
		login_g.add(new LText("", AnchorPoint.CENTER, -296, -220).setSize(592, 220));
		login_g.add(new LText("Login", AnchorPoint.CENTER, -286, -210, 48, 0f).setSize(572, 50));
		login_g.add(new LText("Username:", AnchorPoint.CENTER, -286, -160, 28, 0f).setSize(572, 30));
		login_g.add(new LTextfield(AnchorPoint.CENTER, -186, -140, 24, false).setValidator("^[a-zA-Z0-9]*$"));
		login_g.add(new LText("Password:", AnchorPoint.CENTER, -286, -110, 28, 0f).setSize(572, 30));
		login_g.add(new LTextfield(AnchorPoint.CENTER, -186, -90, 24, true).setValidator("^[a-zA-Z0-9_]*$"));
		login_g.add(new LText("", AnchorPoint.CENTER, -286, -60, 22, 0f).setSize(572, 30));
		login_g.add(new LButton("Create account", AnchorPoint.CENTER, 196, -40, false, 22).setBGActive(false)
				.setSize(85, 20).setCallback(new ButtonCallback() {

					@Override
					public void onClick() {
						try {
							Desktop.getDesktop().browse(new URI("http://www.whysodanish.com/Forum/register.php"));
						} catch (IOException | URISyntaxException e) {
						}
					}

				}));

		login_g.add(new LButton("Login", AnchorPoint.CENTER, -326, 50, true).setSize(332, 64)
				.setCallback(new ButtonCallback() {

					@Override
					public void onClick() {

						APIResponse resp = userAPI.login(((LTextfield) login_g.getObjects()[3]).getText(),
								((LTextfield) login_g.getObjects()[5]).getText());

						if (resp.success()) {
							view.remove(login_g);
							view.remove(mainbg_g);
							view.add(game_map);
						} else {
							((LText) login_g.getObjects()[6]).setText(resp.getError());
						}
					}

				}));
		login_g.add(new LButton("Main menu", AnchorPoint.CENTER, -6, 50, true).setSize(332, 64)
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

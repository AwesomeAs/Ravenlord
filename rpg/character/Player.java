package character;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import feature.Animation;
import graphic.Drawable;

public class Player extends Drawable implements Controllable {
	private String name;
	private int level = 1;
	private Appearance appearance;
	private Animation anim;
	
	public Player(String name, double x, double y) {
		super.setPosition(x, y);
		super.setImgHeight(64);
		this.name = name;
		anim = new Animation("character/Avatar1d0", 48, 0.2);
	}
	
	@Override
	public void onDraw(Graphics2D g, float delta) {
		BufferedImage img = anim.getImage(delta);
		g.drawImage(img, 0, 0, null);
	}

	@Override
	public void jump() {
	}

	@Override
	public void block() {
	}

	@Override
	public void sprint() {
	}

	@Override
	public void moveTo(double x, double y) {
	}

	@Override
	public void attack(Object... objects) {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Appearance getAppearance() {
		return appearance;
	}

	public void setAppearance(Appearance appearance) {
		this.appearance = appearance;
	}
}

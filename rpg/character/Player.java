package character;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import feature.Animation;
import graphic.Drawable;

public class Player extends Drawable implements Controllable {
	private int level = 1;
	private String name;
	private boolean walking;
	private float walkSpeed = 20;
	private double goalX;
	private double goalY;
	private Appearance appearance;
	private Animation[] anim;
	private Controllable.Direction direction;

	public Player(String name, double x, double y) {
		this(name, Controllable.Direction.DOWN, x, y);
	}

	public Player(String name, Controllable.Direction direction, double x, double y) {
		super.setPosition(x, y);
		super.setImgHeight(64);
		this.name = name;
		this.direction = direction;
		goalX = x;
		goalY = y;
		anim = new Animation[] { new Animation("character/Avatar1d0", 48, 0.2),
				new Animation("character/Avatar1u0", 48, 0.2), new Animation("character/Avatar1l0", 48, 0.2),
				new Animation("character/Avatar1r0", 48, 0.2) };
	}

	@Override
	public void onDraw(Graphics2D g, float delta) {
		BufferedImage img = null;
		double dx = 0;
		double dy = 0; 
		switch (direction) {
		case DOWN:
			img = anim[0].getImage(delta);
			dy = 1;
			break;
		case UP:
			img = anim[1].getImage(delta);
			dy = -1;
			break;
		case LEFT:
			img = anim[2].getImage(delta);
			dx = -1;
			break;
		case RIGHT:
			img = anim[3].getImage(delta);
			dx = 1;
			break;
		}
		if(walking) {
			float distance = (float) Math.sqrt((goalX - getX())*(goalX-getX()) + (goalY - getY())*(goalY-getY()));
			if(distance > walkSpeed * delta) {
				setPosition(getX() + dx * walkSpeed * delta, getY() + dy * walkSpeed * delta);
			} else if(distance > 5) {
				setPosition(goalX, goalY);
			} else {
				walking = false;
			}
		}
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
	public void moveTo(double x, double y, boolean relative) {
		if (relative) {
			goalX = getX() + x;
			goalY = getY() + y;
		} else {
			goalX = x;
			goalY = y;
		}

		walking = true;
	}

	@Override
	public void attack(Object... objects) {
	}

	@Override
	public Direction getDirection() {
		return direction;
	}

	@Override
	public void setDicrection(Direction direction) {
		this.direction = direction;
	}

	@Override
	public boolean isWalking() {
		return walking;
	}

	@Override
	public void setWalking(boolean walking) {
		this.walking = walking;
	}

	@Override
	public float getWalkSpeed() {
		return walkSpeed;
	}

	@Override
	public void setWalkSpeed(float speed) {
		walkSpeed = speed;
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

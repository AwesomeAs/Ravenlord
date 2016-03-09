package character;

import java.awt.Graphics2D;

import graphic.Drawable;

public class Enemy extends Drawable implements Controllable{
	// TODO: Need to make the Enemy class.
	@Override
	public void onDraw(Graphics2D g, float delta) {
		
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
		
	}

	@Override
	public void attack(Object... objects) {
		
	}

	@Override
	public Direction getDirection() {
		return null;
	}

	@Override
	public void setDirection(Direction direction) {
		
	}

	@Override
	public boolean isWalking() {
		return false;
	}

	@Override
	public void setWalking(boolean walking) {
		
	}

	@Override
	public float getWalkSpeed() {
		return 0;
	}

	@Override
	public void setWalkSpeed(float speed) {
		
	}
}

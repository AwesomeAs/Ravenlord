package character;

public interface Controllable { 
	void jump();
	void block();
	void sprint();
	void moveTo(double x, double y, boolean relative);
	void attack(Object... objects);
	Direction getDirection();
	void setDicrection(Direction direction);
	boolean isWalking();
	void setWalking(boolean walking);
	float getWalkSpeed();
	void setWalkSpeed(float speed);
		
	
	enum Direction{LEFT, RIGHT, UP, DOWN};
}

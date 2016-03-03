package character;

public interface Controllable {
	void jump();
	void block();
	void sprint();
	void moveTo(double x, double y);
	void attack(Object... objects);
}

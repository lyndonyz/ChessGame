package chess;

public class knight {
	int x, y, color, id;
	boolean dead;
	knight(int color, int id, int x, int y, boolean dead){
		this.color = color;
		this.x = x;
		this.y = y;
		this.dead = dead;
		this.id = id;
	}
	void changeState(boolean dead) {
		this.dead = dead;
	}
	void changePos(int x, int y) {
		this.x = x;
		this.y = y;
	}
	int getXPos() {
		return x;
	}
	int getYPos() {
		return y;
	}
	boolean isAlive() {
		return dead;
	}
	int getId() {
		return id;
	}
	int getColor() {
		return color;
	}
}

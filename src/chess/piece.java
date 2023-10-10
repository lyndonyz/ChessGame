package chess;

public class piece {
	int x, y, color, id;
	char type;
	boolean dead, firstMove;
	piece(int color, int id, int x, int y, boolean dead, char type, boolean firstMove){
		this.color = color;
		this.id = id;
		this.x = x;
		this.y = y;
		this.dead = dead;
		this.type = type;
		this.firstMove = firstMove;
	}
	void changePos(int x, int y){
		this.x = x;
		this.y = y;
	}
	boolean isAlive() {
		return dead;
	}
	char getType() {
		return type;
	}
	void changeState(boolean dead, char type) {
		this.dead = dead;
		this.type = type;
	}
	void madeMove(boolean firstMove) {
		this.firstMove = firstMove;
	}
	int getXPos() {
		return this.x;
	}
	int getYPos() {
		return this.y;
	}
	int getId() {
		return this.id;
	}
	int getColor() {
		return this.color;
	}
	boolean hasMoved() {
		return this.firstMove;
	}
}

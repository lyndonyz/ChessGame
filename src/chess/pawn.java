package chess;

public class pawn {
	int x, y, color, id;
	char promotion;
	boolean dead, promoted, firstMove;
	pawn(int color, int id, int x, int y, boolean dead, boolean promoted, char promotion, boolean firstMove){
		this.color = color;
		this.id = id;
		this.x = x;
		this.y = y;
		this.dead = dead;
		this.promoted = promoted;
		this.promotion = promotion;
		this.firstMove = firstMove;
	}
	void changePos(int x, int y){
		this.x = x;
		this.y = y;
	}
	boolean isAlive() {
		return dead;
	}
	void changeState(boolean dead, boolean promoted, char promotion) {
		this.dead = dead;
		this.promoted = promoted;
		this.promotion= promotion;
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
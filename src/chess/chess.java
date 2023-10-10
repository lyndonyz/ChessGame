package chess;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class chess {
	public static void main(String[] args) {
		new chess();
	}
	int possibleMovesID = -1;
	boolean possibleMoves = false;
	boolean whiteTurn = true;
	final static int SIZE = 8;
	int[][] board = new int[SIZE][SIZE];
	int[][] moveBoard = new int[SIZE][SIZE];
	piece[] piece = new piece[32];
	Rectangle[] rectPiece = new Rectangle[32];
//	
//	pawn[] piece = new pawn[16];
//	Rectangle[] rectPiece = new Rectangle[16];
//	
//	bishop[] piece = new bishop[4];
//	Rectangle[] rectPiece = new Rectangle[4];
//	
//	rook[] piece = new rook[4];
//	Rectangle[] rectPiece = new Rectangle[4];
//	
//	queen[] piece = new queen[2];
//	Rectangle[] rectPiece = new Rectangle[2];
	
	Rectangle[] possibleMovesRect = new Rectangle[30];
//	Rectangle[] possibleTakeRect = new Rectangle[8];
	
	chess(){
		createGUI();
		createPieces();
	}
	void createGUI() {
		JFrame frame = new JFrame("Chess");
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,3));
		
		topPanel.setBackground(new Color(250,248,239));
		frame.add(topPanel, BorderLayout.NORTH);

		DrawingPanel dPanel = new DrawingPanel();
		frame.add(dPanel, BorderLayout.CENTER);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,800);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	void createPieces() {
		for(int i = 0; i < piece.length; i++) {
			//black pawn
			if(i < 8) {
				piece[i] = new piece(1,i+1,i,1,false,'p',false);
				board[piece[i].getYPos()][piece[i].getXPos()] = piece[i].getId();
			}
			//white pawn
			else if(i < 16){
				piece[i] = new piece(0,i+1,i-8,6,false,'p',false);
				board[piece[i].getYPos()][piece[i].getXPos()] = piece[i].getId();
			}
			//black bishop
			else if(i < 18) {
				piece[i] = new piece(1,i+1,(3*(i-16))+2,0,false, 'b', false);
				board[piece[i].getYPos()][piece[i].getXPos()] = piece[i].getId();
			}
			//white bishop
			else if(i < 20) {
				piece[i] = new piece(0,i+1,(3*(i-18))+2,7,false, 'b', false);
				board[piece[i].getYPos()][piece[i].getXPos()] = piece[i].getId();
			}
			//black rook
			else if(i < 22) {
				piece[i] = new piece(1,i+1,(7*(i-20)),0,false, 'r', false);
				board[piece[i].getYPos()][piece[i].getXPos()] = piece[i].getId();
			}
			//white rook
			else if(i < 24) {
				piece[i] = new piece(0,i+1,(7*(i-22)),7,false, 'r', false);
				board[piece[i].getYPos()][piece[i].getXPos()] = piece[i].getId();
			}
			//both queens
			else if(i < 26) {
				piece[i] = new piece(1-(i-24),i+1,3,(7*(i-24)),false, 'q', false);
				board[piece[i].getYPos()][piece[i].getXPos()] = piece[i].getId();
			}
			//black knights
			else if(i < 28) {
				piece[i] = new piece(1,i+1,1+(i-26)*5,0,false, 'n', false);
				board[piece[i].getYPos()][piece[i].getXPos()] = piece[i].getId();
			}
			//white knights
			else if(i < 30) {
				piece[i] = new piece(0,i+1,1+(i-28)*5,7,false, 'n', false);
				board[piece[i].getYPos()][piece[i].getXPos()] = piece[i].getId();
			}
			//both kings
			else if(i < 32) {
				piece[i] = new piece(1-(i-30),i+1,4,(7*(i-30)),false, 'k', false);
				board[piece[i].getYPos()][piece[i].getXPos()] = piece[i].getId();
			}
		}
		
		
		
	}
	
	
	
	
	private class DrawingPanel extends JPanel implements MouseListener{
		
		int panW, panH;
		int boxW, boxH; 
		
		void possibleMoves(int id) {
			possibleMovesID = id;
		}
		
		void initGrid() {
			panW = this.getSize().width;
			panH = this.getSize().height;
			boxW = (int) (panW/SIZE + 0.5);
			boxH = (int) (panH/SIZE + 0.5);
		}
		DrawingPanel() {
			this.setBackground(new Color(205,193,180));
			this.addMouseListener(this);
			this.setFocusable(true);
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			initGrid();	
			
			g.setColor(new Color(187,173,160));
			for (int i = 0; i<SIZE; i++) {
				for (int j = 0; j<SIZE; j++) {
					if(i%2 == 1) {
						if(j%2 == 0) {
							g.fillRect(j*boxW, i*boxH, (boxW), (boxH));
						}
					}
					if(i%2 == 0) {
						if(j%2 == 1) {
							g.fillRect(j*boxW, i*boxH, (boxW), (boxH));
						}
					}
				}
			}

			
//			for(int i = 0; i<board.length; i++) {
//				for(int j = 0; j<board.length; j++) {
//					System.out.print(board[i][j]+" ");
//				}
//				System.out.println("");
//			}
			
			for(int i = 0; i<board.length; i++) {
				for(int j = 0; j<board.length; j++) {
					if (board[i][j] != 0) {
						//draws all pieces
						if(piece[board[i][j]-1].getColor() == 1) {
							g.setColor(new Color(0,0,0));
						}
						else {
							g.setColor(new Color(255,255,255));
						}
						rectPiece[board[i][j]-1] = new Rectangle(j*boxW, i*boxH, (boxW), (boxH));
						((Graphics2D) g).fill(rectPiece[board[i][j]-1]);
						g.setColor(new Color(187,173,160));
						g.setFont(new Font("SansSerif", Font.BOLD, 60));
						
						if (piece[board[i][j]-1].getType() == 'p') {
							g.drawString("P", j*boxW+25, i*boxH+65);
						}
						else if (piece[board[i][j]-1].getType() == 'b') {
							g.drawString("B", j*boxW+25, i*boxH+65);
						}
						else if (piece[board[i][j]-1].getType() == 'r') {
							g.drawString("R", j*boxW+25, i*boxH+65);
						}
						else if (piece[board[i][j]-1].getType() == 'q') {
							g.drawString("Q", j*boxW+25, i*boxH+65);
						}
						else if (piece[board[i][j]-1].getType() == 'n') {
							g.drawString("Kn", j*boxW+10, i*boxH+65);
						}
						else if (piece[board[i][j]-1].getType() == 'k') {
							g.drawString("K", j*boxW+25, i*boxH+65);
						}
					}
				}
				if(possibleMoves) {
					for(int j = 0; j < possibleMovesRect.length; j++) {
						possibleMovesRect[j] = new Rectangle();
					}
//					for(int j = 0; j < possibleTakeRect.length; j++) {
//						possibleTakeRect[j] = new Rectangle();
//					}
					
					for(int a = 0; a<moveBoard.length; a++) {
						for(int b = 0; b<moveBoard.length; b++) {
							if(board[a][b] != 0) {
								if(piece[board[a][b]-1].getType() == 'p') {
									moveBoard[b][a] = 'P'+piece[board[a][b]-1].getColor();
								}
								if(piece[board[a][b]-1].getType() == 'b') {
									moveBoard[b][a] = 'P'+piece[board[a][b]-1].getColor();
								}
								if(piece[board[a][b]-1].getType() == 'r') {
									moveBoard[b][a] = 'P'+piece[board[a][b]-1].getColor();
								}
								if(piece[board[a][b]-1].getType() == 'q') {
									moveBoard[b][a] = 'P'+piece[board[a][b]-1].getColor();
								}
								if(piece[board[a][b]-1].getType() == 'n') {
									moveBoard[b][a] = 'P'+piece[board[a][b]-1].getColor();
								}
								if(piece[board[a][b]-1].getType() == 'k') {
									moveBoard[b][a] = 'P'+piece[board[a][b]-1].getColor();
								}
							}
						}
					}
					
					//pawn
					if(piece[possibleMovesID].getType() == 'p') {
						if(piece[possibleMovesID].getColor() == 0) {
							if(!piece[possibleMovesID].hasMoved()) {
								for(int j = 0; j < 2; j++) {
									possibleMovesRect[j] = new Rectangle(piece[possibleMovesID].getXPos()*boxW, (piece[possibleMovesID].getYPos()-(j+1))*boxH, (boxW), (boxH));
									moveBoard[piece[possibleMovesID].getXPos()][piece[possibleMovesID].getYPos()-(j+1)] = (j+1);
								}
							}
							else {
								possibleMovesRect[0] = new Rectangle(piece[possibleMovesID].getXPos()*boxW, (piece[possibleMovesID].getYPos()-1)*boxH, (boxW), (boxH));
								moveBoard[piece[possibleMovesID].getXPos()][piece[possibleMovesID].getYPos()-1] = (1);
							}
						}
						else {
							if(!piece[possibleMovesID].hasMoved()) {
								for(int j = 0; j < 2; j++) {
									possibleMovesRect[j] = new Rectangle(piece[possibleMovesID].getXPos()*boxW, (piece[possibleMovesID].getYPos()+(j+1))*boxH, (boxW), (boxH));
									moveBoard[piece[possibleMovesID].getXPos()][piece[possibleMovesID].getYPos()+(j+1)] = (j+1);
								}
							}
							else {
								possibleMovesRect[0] = new Rectangle(piece[possibleMovesID].getXPos()*boxW, (piece[possibleMovesID].getYPos()+1)*boxH, (boxW), (boxH));
								moveBoard[piece[possibleMovesID].getXPos()][piece[possibleMovesID].getYPos()+(1)] = (1);
							}
						}
					}
					//bishop
					else if(piece[possibleMovesID].getType() == 'b'){
						int leftX = piece[possibleMovesID].getXPos();
						int rightX = SIZE - piece[possibleMovesID].getXPos() -1;
						int topY = piece[possibleMovesID].getYPos();
						int bottomY = SIZE - piece[possibleMovesID].getYPos() -1;
						int var1;
						int counter = 0;
//						int takeCounter = 0;
				
						if(rightX > bottomY) {
							var1 = bottomY;
						}
						else {
							var1 = rightX;
						}
						for(int j = 0; j < var1; j++) {
							if(moveBoard[piece[possibleMovesID].getXPos()+(j+1)][piece[possibleMovesID].getYPos()+(j+1)]/10 == 8){
								if(piece[possibleMovesID].getColor() != moveBoard[piece[possibleMovesID].getXPos()+(j+1)][piece[possibleMovesID].getYPos()+(j+1)]-80) {
									possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos()+(j+1))*boxW, (piece[possibleMovesID].getYPos()+(j+1))*boxH, (boxW), (boxH));
									moveBoard[piece[possibleMovesID].getXPos()+(j+1)][piece[possibleMovesID].getYPos()+(j+1)] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos()+(j+1))*boxW, (piece[possibleMovesID].getYPos()+(j+1))*boxH, (boxW), (boxH));
								moveBoard[piece[possibleMovesID].getXPos()+(j+1)][piece[possibleMovesID].getYPos()+(j+1)] = (counter+1);
								counter++;
							}
						}
						if(rightX > topY) {
							var1 = topY;
						}
						else {
							var1 = rightX;
						}
						for(int j = 0; j < var1; j++) {
							if(moveBoard[piece[possibleMovesID].getXPos()+(j+1)][piece[possibleMovesID].getYPos()-(j+1)]/10 == 8) {
								if(piece[possibleMovesID].getColor() != moveBoard[piece[possibleMovesID].getXPos()+(j+1)][piece[possibleMovesID].getYPos()-(j+1)]-80) {
									possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos()+(j+1))*boxW, (piece[possibleMovesID].getYPos()-(j+1))*boxH, (boxW), (boxH));
									moveBoard[piece[possibleMovesID].getXPos()+(j+1)][piece[possibleMovesID].getYPos()-(j+1)] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos()+(j+1))*boxW, (piece[possibleMovesID].getYPos()-(j+1))*boxH, (boxW), (boxH));
								moveBoard[piece[possibleMovesID].getXPos()+(j+1)][piece[possibleMovesID].getYPos()-(j+1)] = (counter+1);
								counter++;
							}
						}
						if(leftX > bottomY) {
							var1 = bottomY;
						}
						else {
							var1 = leftX;
						}
						for(int j = 0; j < var1; j++) {
							if(moveBoard[piece[possibleMovesID].getXPos()-(j+1)][piece[possibleMovesID].getYPos()+(j+1)]/10 == 8) {
								if(piece[possibleMovesID].getColor() != moveBoard[piece[possibleMovesID].getXPos()-(j+1)][piece[possibleMovesID].getYPos()+(j+1)]-80) {
									possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos()-(j+1))*boxW, (piece[possibleMovesID].getYPos()+(j+1))*boxH, (boxW), (boxH));
									moveBoard[piece[possibleMovesID].getXPos()-(j+1)][piece[possibleMovesID].getYPos()+(j+1)] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos()-(j+1))*boxW, (piece[possibleMovesID].getYPos()+(j+1))*boxH, (boxW), (boxH));
								moveBoard[piece[possibleMovesID].getXPos()-(j+1)][piece[possibleMovesID].getYPos()+(j+1)] = (counter+1);
								counter++;
							}
						}
						if(leftX > topY) {
							var1 = topY;
						}
						else {
							var1 = leftX;
						}
						for(int j = 0; j < var1; j++) {
							if(moveBoard[piece[possibleMovesID].getXPos()-(j+1)][piece[possibleMovesID].getYPos()-(j+1)]/10 == 8) {
								if(piece[possibleMovesID].getColor() != moveBoard[piece[possibleMovesID].getXPos()-(j+1)][piece[possibleMovesID].getYPos()-(j+1)]-80) {
									possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos()-(j+1))*boxW, (piece[possibleMovesID].getYPos()-(j+1))*boxH, (boxW), (boxH));
									moveBoard[piece[possibleMovesID].getXPos()-(j+1)][piece[possibleMovesID].getYPos()-(j+1)] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos()-(j+1))*boxW, (piece[possibleMovesID].getYPos()-(j+1))*boxH, (boxW), (boxH));
								moveBoard[piece[possibleMovesID].getXPos()-(j+1)][piece[possibleMovesID].getYPos()-(j+1)] = (counter+1);
								counter++;
							}
						}
						
						
					}
					//rook
					else if(piece[possibleMovesID].getType() == 'r'){
						int leftX = piece[possibleMovesID].getXPos();
						int rightX = SIZE - piece[possibleMovesID].getXPos() -1;
						int topY = piece[possibleMovesID].getYPos();
						int bottomY = SIZE - piece[possibleMovesID].getYPos() -1;
						int counter = 0;
						for(int j = 0; j < leftX; j++) {
							if(moveBoard[piece[possibleMovesID].getXPos()-(j+1)][piece[possibleMovesID].getYPos()]/10 == 8) {
								if(piece[possibleMovesID].getColor() != moveBoard[piece[possibleMovesID].getXPos()-(j+1)][piece[possibleMovesID].getYPos()]-80) {
									possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos()-(j+1))*boxW, (piece[possibleMovesID].getYPos())*boxH, (boxW), (boxH));
									moveBoard[piece[possibleMovesID].getXPos()-(j+1)][piece[possibleMovesID].getYPos()] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos()-(j+1))*boxW, (piece[possibleMovesID].getYPos())*boxH, (boxW), (boxH));
								moveBoard[piece[possibleMovesID].getXPos()-(j+1)][piece[possibleMovesID].getYPos()] = (counter+1);
								counter++;
							}
						}
						for(int j = 0; j < rightX; j++) {
							if(moveBoard[piece[possibleMovesID].getXPos()+(j+1)][piece[possibleMovesID].getYPos()]/10 == 8) {
								if(piece[possibleMovesID].getColor() != moveBoard[piece[possibleMovesID].getXPos()+(j+1)][piece[possibleMovesID].getYPos()]-80) {
									possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos()+(j+1))*boxW, (piece[possibleMovesID].getYPos())*boxH, (boxW), (boxH));
									moveBoard[piece[possibleMovesID].getXPos()+(j+1)][piece[possibleMovesID].getYPos()] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos()+(j+1))*boxW, (piece[possibleMovesID].getYPos())*boxH, (boxW), (boxH));
								moveBoard[piece[possibleMovesID].getXPos()+(j+1)][piece[possibleMovesID].getYPos()] = (counter+1);
								counter++;
							}
						}
						for(int j = 0; j < topY; j++) {
							if(moveBoard[piece[possibleMovesID].getXPos()][piece[possibleMovesID].getYPos()-(j+1)]/10 == 8) {
								if(piece[possibleMovesID].getColor() != moveBoard[piece[possibleMovesID].getXPos()][piece[possibleMovesID].getYPos()-(j+1)]-80) {
									possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos())*boxW, (piece[possibleMovesID].getYPos()-(j+1))*boxH, (boxW), (boxH));
									moveBoard[piece[possibleMovesID].getXPos()][piece[possibleMovesID].getYPos()-(j+1)] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos())*boxW, (piece[possibleMovesID].getYPos()-(j+1))*boxH, (boxW), (boxH));
								moveBoard[piece[possibleMovesID].getXPos()][piece[possibleMovesID].getYPos()-(j+1)] = (counter+1);
								counter++;
							}
						}
						for(int j = 0; j < bottomY; j++) {
							if(moveBoard[piece[possibleMovesID].getXPos()][piece[possibleMovesID].getYPos()+(j+1)]/10 == 8) {
								if(piece[possibleMovesID].getColor() !=moveBoard[piece[possibleMovesID].getXPos()][piece[possibleMovesID].getYPos()+(j+1)]-80) {
									possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos())*boxW, (piece[possibleMovesID].getYPos()+(j+1))*boxH, (boxW), (boxH));
									moveBoard[piece[possibleMovesID].getXPos()][piece[possibleMovesID].getYPos()+(j+1)] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos())*boxW, (piece[possibleMovesID].getYPos()+(j+1))*boxH, (boxW), (boxH));
								moveBoard[piece[possibleMovesID].getXPos()][piece[possibleMovesID].getYPos()+(j+1)] = (counter+1);
								counter++;
							}
						}
						
					}
					//queen
					else if(piece[possibleMovesID].getType() == 'q') {
						int leftX = piece[possibleMovesID].getXPos();
						int rightX = SIZE - piece[possibleMovesID].getXPos() -1;
						int topY = piece[possibleMovesID].getYPos();
						int bottomY = SIZE - piece[possibleMovesID].getYPos() -1;
						int var1;
						int counter = 0;
//						int takeCounter = 0;
				
						if(rightX > bottomY) {
							var1 = bottomY;
						}
						else {
							var1 = rightX;
						}
						for(int j = 0; j < var1; j++) {
							if(moveBoard[piece[possibleMovesID].getXPos()+(j+1)][piece[possibleMovesID].getYPos()+(j+1)]/10 == 8){
								if(piece[possibleMovesID].getColor() != moveBoard[piece[possibleMovesID].getXPos()+(j+1)][piece[possibleMovesID].getYPos()+(j+1)]-80) {
									possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos()+(j+1))*boxW, (piece[possibleMovesID].getYPos()+(j+1))*boxH, (boxW), (boxH));
									moveBoard[piece[possibleMovesID].getXPos()+(j+1)][piece[possibleMovesID].getYPos()+(j+1)] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos()+(j+1))*boxW, (piece[possibleMovesID].getYPos()+(j+1))*boxH, (boxW), (boxH));
								moveBoard[piece[possibleMovesID].getXPos()+(j+1)][piece[possibleMovesID].getYPos()+(j+1)] = (counter+1);
								counter++;
							}
						}
						if(rightX > topY) {
							var1 = topY;
						}
						else {
							var1 = rightX;
						}
						for(int j = 0; j < var1; j++) {
							if(moveBoard[piece[possibleMovesID].getXPos()+(j+1)][piece[possibleMovesID].getYPos()-(j+1)]/10 == 8) {
								if(piece[possibleMovesID].getColor() != moveBoard[piece[possibleMovesID].getXPos()+(j+1)][piece[possibleMovesID].getYPos()-(j+1)]-80) {
									possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos()+(j+1))*boxW, (piece[possibleMovesID].getYPos()-(j+1))*boxH, (boxW), (boxH));
									moveBoard[piece[possibleMovesID].getXPos()+(j+1)][piece[possibleMovesID].getYPos()-(j+1)] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos()+(j+1))*boxW, (piece[possibleMovesID].getYPos()-(j+1))*boxH, (boxW), (boxH));
								moveBoard[piece[possibleMovesID].getXPos()+(j+1)][piece[possibleMovesID].getYPos()-(j+1)] = (counter+1);
								counter++;
							}
						}
						if(leftX > bottomY) {
							var1 = bottomY;
						}
						else {
							var1 = leftX;
						}
						for(int j = 0; j < var1; j++) {
							if(moveBoard[piece[possibleMovesID].getXPos()-(j+1)][piece[possibleMovesID].getYPos()+(j+1)]/10 == 8) {
								if(piece[possibleMovesID].getColor() != moveBoard[piece[possibleMovesID].getXPos()-(j+1)][piece[possibleMovesID].getYPos()+(j+1)]-80) {
									possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos()-(j+1))*boxW, (piece[possibleMovesID].getYPos()+(j+1))*boxH, (boxW), (boxH));
									moveBoard[piece[possibleMovesID].getXPos()-(j+1)][piece[possibleMovesID].getYPos()+(j+1)] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos()-(j+1))*boxW, (piece[possibleMovesID].getYPos()+(j+1))*boxH, (boxW), (boxH));
								moveBoard[piece[possibleMovesID].getXPos()-(j+1)][piece[possibleMovesID].getYPos()+(j+1)] = (counter+1);
								counter++;
							}
						}
						if(leftX > topY) {
							var1 = topY;
						}
						else {
							var1 = leftX;
						}
						for(int j = 0; j < var1; j++) {
							if(moveBoard[piece[possibleMovesID].getXPos()-(j+1)][piece[possibleMovesID].getYPos()-(j+1)]/10 == 8) {
								if(piece[possibleMovesID].getColor() != moveBoard[piece[possibleMovesID].getXPos()-(j+1)][piece[possibleMovesID].getYPos()-(j+1)]-80) {
									possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos()-(j+1))*boxW, (piece[possibleMovesID].getYPos()-(j+1))*boxH, (boxW), (boxH));
									moveBoard[piece[possibleMovesID].getXPos()-(j+1)][piece[possibleMovesID].getYPos()-(j+1)] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos()-(j+1))*boxW, (piece[possibleMovesID].getYPos()-(j+1))*boxH, (boxW), (boxH));
								moveBoard[piece[possibleMovesID].getXPos()-(j+1)][piece[possibleMovesID].getYPos()-(j+1)] = (counter+1);
								counter++;
							}
						}
						for(int j = 0; j < leftX; j++) {
							if(moveBoard[piece[possibleMovesID].getXPos()-(j+1)][piece[possibleMovesID].getYPos()]/10 == 8) {
								if(piece[possibleMovesID].getColor() != moveBoard[piece[possibleMovesID].getXPos()-(j+1)][piece[possibleMovesID].getYPos()]-80) {
									possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos()-(j+1))*boxW, (piece[possibleMovesID].getYPos())*boxH, (boxW), (boxH));
									moveBoard[piece[possibleMovesID].getXPos()-(j+1)][piece[possibleMovesID].getYPos()] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos()-(j+1))*boxW, (piece[possibleMovesID].getYPos())*boxH, (boxW), (boxH));
								moveBoard[piece[possibleMovesID].getXPos()-(j+1)][piece[possibleMovesID].getYPos()] = (counter+1);
								counter++;
							}
						}
						for(int j = 0; j < rightX; j++) {
							if(moveBoard[piece[possibleMovesID].getXPos()+(j+1)][piece[possibleMovesID].getYPos()]/10 == 8) {
								if(piece[possibleMovesID].getColor() != moveBoard[piece[possibleMovesID].getXPos()+(j+1)][piece[possibleMovesID].getYPos()]-80) {
									possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos()+(j+1))*boxW, (piece[possibleMovesID].getYPos())*boxH, (boxW), (boxH));
									moveBoard[piece[possibleMovesID].getXPos()+(j+1)][piece[possibleMovesID].getYPos()] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos()+(j+1))*boxW, (piece[possibleMovesID].getYPos())*boxH, (boxW), (boxH));
								moveBoard[piece[possibleMovesID].getXPos()+(j+1)][piece[possibleMovesID].getYPos()] = (counter+1);
								counter++;
							}
						}
						for(int j = 0; j < topY; j++) {
							if(moveBoard[piece[possibleMovesID].getXPos()][piece[possibleMovesID].getYPos()-(j+1)]/10 == 8) {
								if(piece[possibleMovesID].getColor() != moveBoard[piece[possibleMovesID].getXPos()][piece[possibleMovesID].getYPos()-(j+1)]-80) {
									possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos())*boxW, (piece[possibleMovesID].getYPos()-(j+1))*boxH, (boxW), (boxH));
									moveBoard[piece[possibleMovesID].getXPos()][piece[possibleMovesID].getYPos()-(j+1)] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos())*boxW, (piece[possibleMovesID].getYPos()-(j+1))*boxH, (boxW), (boxH));
								moveBoard[piece[possibleMovesID].getXPos()][piece[possibleMovesID].getYPos()-(j+1)] = (counter+1);
								counter++;
							}
						}
						for(int j = 0; j < bottomY; j++) {
							if(moveBoard[piece[possibleMovesID].getXPos()][piece[possibleMovesID].getYPos()+(j+1)]/10 == 8) {
								if(piece[possibleMovesID].getColor() !=moveBoard[piece[possibleMovesID].getXPos()][piece[possibleMovesID].getYPos()+(j+1)]-80) {
									possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos())*boxW, (piece[possibleMovesID].getYPos()+(j+1))*boxH, (boxW), (boxH));
									moveBoard[piece[possibleMovesID].getXPos()][piece[possibleMovesID].getYPos()+(j+1)] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((piece[possibleMovesID].getXPos())*boxW, (piece[possibleMovesID].getYPos()+(j+1))*boxH, (boxW), (boxH));
								moveBoard[piece[possibleMovesID].getXPos()][piece[possibleMovesID].getYPos()+(j+1)] = (counter+1);
								counter++;
							}
						}
					}
					else {
						
					}
					for(int ik = 0; ik<moveBoard.length; ik++) {
						for(int j = 0; j<moveBoard.length; j++) {
							System.out.print(moveBoard[ik][j]+" ");
						}
						System.out.println("");
					}
					System.out.println("");
					g.setColor(new Color(255,255,0,25));
					for(int j = 0; j < possibleMovesRect.length; j++) {
						((Graphics2D) g).fill(possibleMovesRect[j]);
					}
//					g.setColor(new Color(255,0,0,25));
//					for(int j = 0; j < possibleTakeRect.length; j++) {
//						((Graphics2D) g).fill(possibleTakeRect[j]);
//					}
				}
			}
			
			//lines
			g.setColor(new Color(156, 141, 126));
			g2.setStroke(new BasicStroke(5));
			for (int i = 0; i<SIZE+1; i++) {
				g2.drawLine(boxW*i, 0, boxW*i, panH);
				g2.drawLine(0, boxH*i, panW, boxH*i);
			}
			
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if(possibleMoves) {
				for(int i = 0; i < possibleMovesRect.length; i++) {
					if(possibleMovesRect[i].contains(e.getX(), e.getY())) {
						if(piece[possibleMovesID].getType() == 'p') {
							for(int a = 0; a<moveBoard.length; a++) {
								for(int b = 0; b<moveBoard.length; b++) {
									if(moveBoard[a][b] == i+1) {
										board[piece[possibleMovesID].getYPos()][piece[possibleMovesID].getXPos()] = 0;
										piece[possibleMovesID].changePos(a,b);
										piece[possibleMovesID].madeMove(true);
										board[piece[possibleMovesID].getYPos()][piece[possibleMovesID].getXPos()] = piece[possibleMovesID].getId();
										if(piece[possibleMovesID].getColor() == 0) {
											whiteTurn = false;
										}
										else {
											whiteTurn = true;
										}
									}
								}
							}	
						}
						else if(piece[possibleMovesID].getType() == 'b') {
							for(int a = 0; a<moveBoard.length; a++) {
								for(int b = 0; b<moveBoard.length; b++) {
									if(moveBoard[a][b] == i+1) {
										board[piece[possibleMovesID].getYPos()][piece[possibleMovesID].getXPos()] = 0;
										piece[possibleMovesID].changePos(a,b);
										board[piece[possibleMovesID].getYPos()][piece[possibleMovesID].getXPos()] = piece[possibleMovesID].getId();
										if(piece[possibleMovesID].getColor() == 0) {
											whiteTurn = false;
										}
										else {
											whiteTurn = true;
										}
									}
								}
							}
						}
						else if(piece[possibleMovesID].getType() == 'r') {
							for(int a = 0; a<moveBoard.length; a++) {
								for(int b = 0; b<moveBoard.length; b++) {
									if(moveBoard[a][b] == i+1) {
										board[piece[possibleMovesID].getYPos()][piece[possibleMovesID].getXPos()] = 0;
										piece[possibleMovesID].changePos(a,b);
										board[piece[possibleMovesID].getYPos()][piece[possibleMovesID].getXPos()] = piece[possibleMovesID].getId();
										if(piece[possibleMovesID].getColor() == 0) {
											whiteTurn = false;
										}
										else {
											whiteTurn = true;
										}
									}
								}
							}
						}
						else if(piece[possibleMovesID].getType() == 'q') {
							for(int a = 0; a<moveBoard.length; a++) {
								for(int b = 0; b<moveBoard.length; b++) {
									if(moveBoard[a][b] == i+1) {
										board[piece[possibleMovesID].getYPos()][piece[possibleMovesID].getXPos()] = 0;
										piece[possibleMovesID].changePos(a,b);
										board[piece[possibleMovesID].getYPos()][piece[possibleMovesID].getXPos()] = piece[possibleMovesID].getId();
										if(piece[possibleMovesID].getColor() == 0) {
											whiteTurn = false;
										}
										else {
											whiteTurn = true;
										}
									}
								}
							}
						}
						possibleMoves = false;
						break;
					}
//					else {
//						possibleMoves = false;
//						break;
//					}
				}
				
				
				
//				for(int i = 0; i<board.length; i++) {
//					for(int j = 0; j<board.length; j++) {
//						System.out.print(board[i][j]+" ");
//					}
//					System.out.println("");
//				}
//				System.out.println("");
				
				
				
				
			}
			else {
				if(!possibleMoves) {
					for(int i = 0; i < rectPiece.length; i++) {
						if(rectPiece[i].contains(e.getX(), e.getY())) {
							if(piece[i].getColor() == 0 && whiteTurn) {
								possibleMoves = true;
								possibleMoves(i);
								break;
							}
							else {
								possibleMoves = false;
							}
							if(piece[i].getColor() == 1 && !whiteTurn) {
								possibleMoves = true;
								possibleMoves(i);
								break;
							}
							else {
								possibleMoves = false;
							}
						}
						else {
							possibleMoves = false;
						}
					}
				}
				if(!possibleMoves) {
					for(int i = 0; i < rectPiece.length; i++) {
						if(rectPiece[i].contains(e.getX(), e.getY())) {
							if(piece[i].getColor() == 0 && whiteTurn) {
								possibleMoves = true;
								possibleMoves(i);
								break;
							}
							else {
								possibleMoves = false;
							}
							if(piece[i].getColor() == 1 && !whiteTurn) {
								possibleMoves = true;
								possibleMoves(i);
								break;
							}
							else {
								possibleMoves = false;
							}
						}
						else {
							possibleMoves = false;
						}
					}
				}
				if(!possibleMoves) {
					for(int i = 0; i < rectPiece.length; i++) {
						if(rectPiece[i].contains(e.getX(), e.getY())) {
							if(piece[i].getColor() == 0 && whiteTurn) {
								possibleMoves = true;
								possibleMoves(i);
								break;
							}
							else {
								possibleMoves = false;
							}
							if(piece[i].getColor() == 1 && !whiteTurn) {
								possibleMoves = true;
								possibleMoves(i);
								break;
							}
							else {
								possibleMoves = false;
							}
							
						}
						else {
							possibleMoves = false;
						}
					}
				}
				if(!possibleMoves) {
					for(int i = 0; i < rectPiece.length; i++) {
						if(rectPiece[i].contains(e.getX(), e.getY())) {
							if(piece[i].getColor() == 0 && whiteTurn) {
								possibleMoves = true;
								possibleMoves(i);
								break;
							}
							else {
								possibleMoves = false;
							}
							if(piece[i].getColor() == 1 && !whiteTurn) {
								possibleMoves = true;
								possibleMoves(i);
								break;
							}
							else {
								possibleMoves = false;
							}
							
						}
						else {
							possibleMoves = false;
						}
					}
				}
				for(int a = 0; a<moveBoard.length; a++) {
					for(int b = 0; b<moveBoard.length; b++) {
						moveBoard[a][b] = 0;
					}
				}
			}
			this.repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}


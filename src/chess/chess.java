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
	pawn[] pawns = new pawn[16];
	Rectangle[] rectPawns = new Rectangle[16];
	
	bishop[] bishops = new bishop[4];
	Rectangle[] rectBishops = new Rectangle[4];
	
	rook[] rooks = new rook[4];
	Rectangle[] rectRooks = new Rectangle[4];
	
	queen[] queens = new queen[2];
	Rectangle[] rectQueens = new Rectangle[2];
	
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
		//black pawns
		for(int i = 0; i < SIZE; i++) {
			pawns[i] = new pawn(1,11+i,i,1,false,false,'n',false);
			String tempPos = "1"+pawns[i].getId();
			board[pawns[i].getYPos()][pawns[i].getXPos()] = Integer.parseInt(tempPos);
		}
		//white pawns
		for(int i = 0; i < SIZE; i++) {
			pawns[i+8] = new pawn(0,19+i,i,6,false,false,'n',false);
			String tempPos = "1"+pawns[i+8].getId();
			board[pawns[i+8].getYPos()][pawns[i+8].getXPos()] = Integer.parseInt(tempPos);
		}
		//black bishops
		for(int i = 0; i < 2; i++) {
			bishops[i] = new bishop(1,11+i,(3*i)+2,0,false);
			String tempPos = "2"+bishops[i].getId();
			board[bishops[i].getYPos()][bishops[i].getXPos()] = Integer.parseInt(tempPos);
		}
		//white bishops
		for(int i = 0; i < 2; i++) {
			bishops[i+2] = new bishop(0,13+i,(3*i)+2,7,false);
			String tempPos = "2"+bishops[i+2].getId();
			board[bishops[i+2].getYPos()][bishops[i+2].getXPos()] = Integer.parseInt(tempPos);
		}
		//black rooks
		for(int i = 0; i < 2; i++) {
			rooks[i] = new rook(1,11+i,(7*i),0,false);
			String tempPos = "3"+rooks[i].getId();
			board[rooks[i].getYPos()][rooks[i].getXPos()] = Integer.parseInt(tempPos);
		}
		//white rooks
		for(int i = 0; i < 2; i++) {
			rooks[i+2] = new rook(0,13+i,(7*i),7,false);
			String tempPos = "3"+rooks[i+2].getId();
			board[rooks[i+2].getYPos()][rooks[i+2].getXPos()] = Integer.parseInt(tempPos);
		}
		//both queens
		for(int i = 0; i < 2; i++) {
			queens[i] = new queen(1-i,11+i,3,(7*i),false);
			String tempPos = "4"+queens[i].getId();
			board[queens[i].getYPos()][queens[i].getXPos()] = Integer.parseInt(tempPos);
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
						//pawn drawing
						if (board[i][j]/100 == 1) {
							if(pawns[(board[i][j]%100)-11].getColor() == 1) {
								g.setColor(new Color(0,0,0));
								rectPawns[(board[i][j]%100)-11] = new Rectangle(j*boxW, i*boxH, (boxW), (boxH));
								((Graphics2D) g).fill(rectPawns[(board[i][j]%100)-11]);
							}
							else {
								g.setColor(new Color(255,255,255));
								rectPawns[(board[i][j]%100)-11] = new Rectangle(j*boxW, i*boxH, (boxW), (boxH));
								((Graphics2D) g).fill(rectPawns[(board[i][j]%100)-11]);
							}
							g.setColor(new Color(187,173,160));
							g.setFont(new Font("SansSerif", Font.BOLD, 60));
							g.drawString("P", j*boxW+25, i*boxH+65);
						}
						//bishop drawing
						else if (board[i][j]/100 == 2) {
							if(bishops[(board[i][j]%200)-11].getColor() == 1) {
								g.setColor(new Color(0,0,0));
								rectBishops[(board[i][j]%200)-11] = new Rectangle(j*boxW, i*boxH, (boxW), (boxH));
								((Graphics2D) g).fill(rectBishops[(board[i][j]%200)-11]);
							}
							else {
								g.setColor(new Color(255,255,255));
								rectBishops[(board[i][j]%200)-11] = new Rectangle(j*boxW, i*boxH, (boxW), (boxH));
								((Graphics2D) g).fill(rectBishops[(board[i][j]%200)-11]);
							}
							g.setColor(new Color(187,173,160));
							g.setFont(new Font("SansSerif", Font.BOLD, 60));
							g.drawString("B", j*boxW+25, i*boxH+65);
						}
						//rook drawing
						else if (board[i][j]/100 == 3) {
							if(rooks[(board[i][j]%300)-11].getColor() == 1) {
								g.setColor(new Color(0,0,0));
								rectRooks[(board[i][j]%300)-11] = new Rectangle(j*boxW, i*boxH, (boxW), (boxH));
								((Graphics2D) g).fill(rectRooks[(board[i][j]%300)-11]);
							}
							else {
								g.setColor(new Color(255,255,255));
								rectRooks[(board[i][j]%300)-11] = new Rectangle(j*boxW, i*boxH, (boxW), (boxH));
								((Graphics2D) g).fill(rectRooks[(board[i][j]%300)-11]);
							}
							g.setColor(new Color(187,173,160));
							g.setFont(new Font("SansSerif", Font.BOLD, 60));
							g.drawString("R", j*boxW+25, i*boxH+65);
						}
						//queen drawing
						else if (board[i][j]/100 == 4) {
							if(queens[(board[i][j]%400)-11].getColor() == 1) {
								g.setColor(new Color(0,0,0));
								rectQueens[(board[i][j]%400)-11] = new Rectangle(j*boxW, i*boxH, (boxW), (boxH));
								((Graphics2D) g).fill(rectQueens[(board[i][j]%400)-11]);
							}
							else {
								g.setColor(new Color(255,255,255));
								rectQueens[(board[i][j]%400)-11] = new Rectangle(j*boxW, i*boxH, (boxW), (boxH));
								((Graphics2D) g).fill(rectQueens[(board[i][j]%400)-11]);
							}
							g.setColor(new Color(187,173,160));
							g.setFont(new Font("SansSerif", Font.BOLD, 60));
							g.drawString("Q", j*boxW+25, i*boxH+65);
						}
//						else if (board[i][j] == 8) {
//						g.setColor(new Color(243,178,122));//8
//						}
//						else if (board[i][j] == 16) {
//						g.setColor(new Color(246,150,100));//16
//						}
//						else if (board[i][j] == 32) {
//						g.setColor(new Color(247,124,95));//32
//						}
//						else if (board[i][j] == 64) {
//						g.setColor(new Color(247,95,95));//64
//						}
//						else{
//						g.setColor(new Color(237,208,115));//128
//						}
//						g.fillRect(j*boxW, i*boxH, (boxW), (boxH));
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
								if(board[a][b]/100 == 1) {
									moveBoard[b][a] = 'P'+pawns[board[a][b]-111].getColor();
								}
								if(board[a][b]/100 == 2) {
									moveBoard[b][a] = 'P'+bishops[board[a][b]-211].getColor();
								}
								if(board[a][b]/100 == 3) {
									moveBoard[b][a] = 'P'+rooks[board[a][b]-311].getColor();
								}
								if(board[a][b]/100 == 4) {
									moveBoard[b][a] = 'P'+queens[board[a][b]-411].getColor();
								}
							}
						}
					}
					
					//pawn
					if(possibleMovesID/100 == 1) {
						if(pawns[possibleMovesID-100].getColor() == 0) {
							if(!pawns[possibleMovesID-100].hasMoved()) {
								for(int j = 0; j < 2; j++) {
									possibleMovesRect[j] = new Rectangle(pawns[possibleMovesID-100].getXPos()*boxW, (pawns[possibleMovesID-100].getYPos()-(j+1))*boxH, (boxW), (boxH));
									moveBoard[pawns[possibleMovesID-100].getXPos()][pawns[possibleMovesID-100].getYPos()-(j+1)] = (j+1);
								}
							}
							else {
								possibleMovesRect[0] = new Rectangle(pawns[possibleMovesID-100].getXPos()*boxW, (pawns[possibleMovesID-100].getYPos()-1)*boxH, (boxW), (boxH));
								moveBoard[pawns[possibleMovesID-100].getXPos()][pawns[possibleMovesID-100].getYPos()-1] = (1);
							}
						}
						else {
							if(!pawns[possibleMovesID-100].hasMoved()) {
								for(int j = 0; j < 2; j++) {
									possibleMovesRect[j] = new Rectangle(pawns[possibleMovesID-100].getXPos()*boxW, (pawns[possibleMovesID-100].getYPos()+(j+1))*boxH, (boxW), (boxH));
									moveBoard[pawns[possibleMovesID-100].getXPos()][pawns[possibleMovesID-100].getYPos()+(j+1)] = (j+1);
								}
							}
							else {
								possibleMovesRect[0] = new Rectangle(pawns[possibleMovesID-100].getXPos()*boxW, (pawns[possibleMovesID-100].getYPos()+1)*boxH, (boxW), (boxH));
								moveBoard[pawns[possibleMovesID-100].getXPos()][pawns[possibleMovesID-100].getYPos()+(1)] = (1);
							}
						}
					}
					//bishop
					else if(possibleMovesID/100 == 2){
						int leftX = bishops[possibleMovesID-200].getXPos();
						int rightX = SIZE - bishops[possibleMovesID-200].getXPos() -1;
						int topY = bishops[possibleMovesID-200].getYPos();
						int bottomY = SIZE - bishops[possibleMovesID-200].getYPos() -1;
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
							if(moveBoard[bishops[possibleMovesID-200].getXPos()+(j+1)][bishops[possibleMovesID-200].getYPos()+(j+1)]/10 == 8){
								if(bishops[possibleMovesID-200].getColor() != moveBoard[bishops[possibleMovesID-200].getXPos()+(j+1)][bishops[possibleMovesID-200].getYPos()+(j+1)]-80) {
									possibleMovesRect[counter] = new Rectangle((bishops[possibleMovesID-200].getXPos()+(j+1))*boxW, (bishops[possibleMovesID-200].getYPos()+(j+1))*boxH, (boxW), (boxH));
									moveBoard[bishops[possibleMovesID-200].getXPos()+(j+1)][bishops[possibleMovesID-200].getYPos()+(j+1)] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((bishops[possibleMovesID-200].getXPos()+(j+1))*boxW, (bishops[possibleMovesID-200].getYPos()+(j+1))*boxH, (boxW), (boxH));
								moveBoard[bishops[possibleMovesID-200].getXPos()+(j+1)][bishops[possibleMovesID-200].getYPos()+(j+1)] = (counter+1);
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
							if(moveBoard[bishops[possibleMovesID-200].getXPos()+(j+1)][bishops[possibleMovesID-200].getYPos()-(j+1)]/10 == 8) {
								if(bishops[possibleMovesID-200].getColor() != moveBoard[bishops[possibleMovesID-200].getXPos()+(j+1)][bishops[possibleMovesID-200].getYPos()-(j+1)]-80) {
									possibleMovesRect[counter] = new Rectangle((bishops[possibleMovesID-200].getXPos()+(j+1))*boxW, (bishops[possibleMovesID-200].getYPos()-(j+1))*boxH, (boxW), (boxH));
									moveBoard[bishops[possibleMovesID-200].getXPos()+(j+1)][bishops[possibleMovesID-200].getYPos()-(j+1)] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((bishops[possibleMovesID-200].getXPos()+(j+1))*boxW, (bishops[possibleMovesID-200].getYPos()-(j+1))*boxH, (boxW), (boxH));
								moveBoard[bishops[possibleMovesID-200].getXPos()+(j+1)][bishops[possibleMovesID-200].getYPos()-(j+1)] = (counter+1);
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
							if(moveBoard[bishops[possibleMovesID-200].getXPos()-(j+1)][bishops[possibleMovesID-200].getYPos()+(j+1)]/10 == 8) {
								if(bishops[possibleMovesID-200].getColor() != moveBoard[bishops[possibleMovesID-200].getXPos()-(j+1)][bishops[possibleMovesID-200].getYPos()+(j+1)]-80) {
									possibleMovesRect[counter] = new Rectangle((bishops[possibleMovesID-200].getXPos()-(j+1))*boxW, (bishops[possibleMovesID-200].getYPos()+(j+1))*boxH, (boxW), (boxH));
									moveBoard[bishops[possibleMovesID-200].getXPos()-(j+1)][bishops[possibleMovesID-200].getYPos()+(j+1)] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((bishops[possibleMovesID-200].getXPos()-(j+1))*boxW, (bishops[possibleMovesID-200].getYPos()+(j+1))*boxH, (boxW), (boxH));
								moveBoard[bishops[possibleMovesID-200].getXPos()-(j+1)][bishops[possibleMovesID-200].getYPos()+(j+1)] = (counter+1);
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
							if(moveBoard[bishops[possibleMovesID-200].getXPos()-(j+1)][bishops[possibleMovesID-200].getYPos()-(j+1)]/10 == 8) {
								if(bishops[possibleMovesID-200].getColor() != moveBoard[bishops[possibleMovesID-200].getXPos()-(j+1)][bishops[possibleMovesID-200].getYPos()-(j+1)]-80) {
									possibleMovesRect[counter] = new Rectangle((bishops[possibleMovesID-200].getXPos()-(j+1))*boxW, (bishops[possibleMovesID-200].getYPos()-(j+1))*boxH, (boxW), (boxH));
									moveBoard[bishops[possibleMovesID-200].getXPos()-(j+1)][bishops[possibleMovesID-200].getYPos()-(j+1)] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((bishops[possibleMovesID-200].getXPos()-(j+1))*boxW, (bishops[possibleMovesID-200].getYPos()-(j+1))*boxH, (boxW), (boxH));
								moveBoard[bishops[possibleMovesID-200].getXPos()-(j+1)][bishops[possibleMovesID-200].getYPos()-(j+1)] = (counter+1);
								counter++;
							}
						}
						
						
					}
					//rook
					else if(possibleMovesID/100 == 3){
						int leftX = rooks[possibleMovesID-300].getXPos();
						int rightX = SIZE - rooks[possibleMovesID-300].getXPos() -1;
						int topY = rooks[possibleMovesID-300].getYPos();
						int bottomY = SIZE - rooks[possibleMovesID-300].getYPos() -1;
						int counter = 0;
						for(int j = 0; j < leftX; j++) {
							if(moveBoard[rooks[possibleMovesID-300].getXPos()-(j+1)][rooks[possibleMovesID-300].getYPos()]/10 == 8) {
								if(rooks[possibleMovesID-300].getColor() != moveBoard[rooks[possibleMovesID-300].getXPos()-(j+1)][rooks[possibleMovesID-300].getYPos()]-80) {
									possibleMovesRect[counter] = new Rectangle((rooks[possibleMovesID-300].getXPos()-(j+1))*boxW, (rooks[possibleMovesID-300].getYPos())*boxH, (boxW), (boxH));
									moveBoard[rooks[possibleMovesID-300].getXPos()-(j+1)][rooks[possibleMovesID-300].getYPos()] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((rooks[possibleMovesID-300].getXPos()-(j+1))*boxW, (rooks[possibleMovesID-300].getYPos())*boxH, (boxW), (boxH));
								moveBoard[rooks[possibleMovesID-300].getXPos()-(j+1)][rooks[possibleMovesID-300].getYPos()] = (counter+1);
								counter++;
							}
						}
						for(int j = 0; j < rightX; j++) {
							if(moveBoard[rooks[possibleMovesID-300].getXPos()+(j+1)][rooks[possibleMovesID-300].getYPos()]/10 == 8) {
								if(rooks[possibleMovesID-300].getColor() != moveBoard[rooks[possibleMovesID-300].getXPos()+(j+1)][rooks[possibleMovesID-300].getYPos()]-80) {
									possibleMovesRect[counter] = new Rectangle((rooks[possibleMovesID-300].getXPos()+(j+1))*boxW, (rooks[possibleMovesID-300].getYPos())*boxH, (boxW), (boxH));
									moveBoard[rooks[possibleMovesID-300].getXPos()+(j+1)][rooks[possibleMovesID-300].getYPos()] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((rooks[possibleMovesID-300].getXPos()+(j+1))*boxW, (rooks[possibleMovesID-300].getYPos())*boxH, (boxW), (boxH));
								moveBoard[rooks[possibleMovesID-300].getXPos()+(j+1)][rooks[possibleMovesID-300].getYPos()] = (counter+1);
								counter++;
							}
						}
						for(int j = 0; j < topY; j++) {
							if(moveBoard[rooks[possibleMovesID-300].getXPos()][rooks[possibleMovesID-300].getYPos()-(j+1)]/10 == 8) {
								if(rooks[possibleMovesID-300].getColor() != moveBoard[rooks[possibleMovesID-300].getXPos()][rooks[possibleMovesID-300].getYPos()-(j+1)]-80) {
									possibleMovesRect[counter] = new Rectangle((rooks[possibleMovesID-300].getXPos())*boxW, (rooks[possibleMovesID-300].getYPos()-(j+1))*boxH, (boxW), (boxH));
									moveBoard[rooks[possibleMovesID-300].getXPos()][rooks[possibleMovesID-300].getYPos()-(j+1)] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((rooks[possibleMovesID-300].getXPos())*boxW, (rooks[possibleMovesID-300].getYPos()-(j+1))*boxH, (boxW), (boxH));
								moveBoard[rooks[possibleMovesID-300].getXPos()][rooks[possibleMovesID-300].getYPos()-(j+1)] = (counter+1);
								counter++;
							}
						}
						for(int j = 0; j < bottomY; j++) {
							if(moveBoard[rooks[possibleMovesID-300].getXPos()][rooks[possibleMovesID-300].getYPos()+(j+1)]/10 == 8) {
								if(rooks[possibleMovesID-300].getColor() !=moveBoard[rooks[possibleMovesID-300].getXPos()][rooks[possibleMovesID-300].getYPos()+(j+1)]-80) {
									possibleMovesRect[counter] = new Rectangle((rooks[possibleMovesID-300].getXPos())*boxW, (rooks[possibleMovesID-300].getYPos()+(j+1))*boxH, (boxW), (boxH));
									moveBoard[rooks[possibleMovesID-300].getXPos()][rooks[possibleMovesID-300].getYPos()+(j+1)] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((rooks[possibleMovesID-300].getXPos())*boxW, (rooks[possibleMovesID-300].getYPos()+(j+1))*boxH, (boxW), (boxH));
								moveBoard[rooks[possibleMovesID-300].getXPos()][rooks[possibleMovesID-300].getYPos()+(j+1)] = (counter+1);
								counter++;
							}
						}
						
					}
					//queen
					else if(possibleMovesID/100 == 4) {
						int leftX = queens[possibleMovesID-400].getXPos();
						int rightX = SIZE - queens[possibleMovesID-400].getXPos() -1;
						int topY = queens[possibleMovesID-400].getYPos();
						int bottomY = SIZE - queens[possibleMovesID-400].getYPos() -1;
						int var1;
						int counter = 0;
						for(int j = 0; j < leftX; j++) {
							if(moveBoard[queens[possibleMovesID-400].getXPos()-(j+1)][queens[possibleMovesID-400].getYPos()]/10 == 8) {
								if(queens[possibleMovesID-400].getColor() != moveBoard[queens[possibleMovesID-400].getXPos()-(j+1)][queens[possibleMovesID-400].getYPos()]-80) {
									possibleMovesRect[counter] = new Rectangle((queens[possibleMovesID-400].getXPos()-(j+1))*boxW, (queens[possibleMovesID-400].getYPos())*boxH, (boxW), (boxH));
									moveBoard[queens[possibleMovesID-400].getXPos()-(j+1)][queens[possibleMovesID-400].getYPos()] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((queens[possibleMovesID-400].getXPos()-(j+1))*boxW, (queens[possibleMovesID-400].getYPos())*boxH, (boxW), (boxH));
								moveBoard[queens[possibleMovesID-400].getXPos()-(j+1)][queens[possibleMovesID-400].getYPos()] = (counter+1);
								counter++;
							}
						}
						for(int j = 0; j < rightX; j++) {
							if(moveBoard[queens[possibleMovesID-400].getXPos()+(j+1)][queens[possibleMovesID-400].getYPos()]/10 == 8) {
								if(queens[possibleMovesID-400].getColor() != moveBoard[queens[possibleMovesID-400].getXPos()+(j+1)][queens[possibleMovesID-400].getYPos()]-80) {
									possibleMovesRect[counter] = new Rectangle((queens[possibleMovesID-400].getXPos()+(j+1))*boxW, (queens[possibleMovesID-400].getYPos())*boxH, (boxW), (boxH));
									moveBoard[queens[possibleMovesID-400].getXPos()+(j+1)][queens[possibleMovesID-400].getYPos()] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((queens[possibleMovesID-400].getXPos()+(j+1))*boxW, (queens[possibleMovesID-400].getYPos())*boxH, (boxW), (boxH));
								moveBoard[queens[possibleMovesID-400].getXPos()+(j+1)][queens[possibleMovesID-400].getYPos()] = (counter+1);
								counter++;
							}
						}
						for(int j = 0; j < topY; j++) {
							if(moveBoard[queens[possibleMovesID-400].getXPos()][queens[possibleMovesID-400].getYPos()-(j+1)]/10 == 8) {
								if(queens[possibleMovesID-400].getColor() != moveBoard[queens[possibleMovesID-400].getXPos()][queens[possibleMovesID-400].getYPos()-(j+1)]-80) {
									possibleMovesRect[counter] = new Rectangle((queens[possibleMovesID-400].getXPos())*boxW, (queens[possibleMovesID-400].getYPos()-(j+1))*boxH, (boxW), (boxH));
									moveBoard[queens[possibleMovesID-400].getXPos()][queens[possibleMovesID-400].getYPos()-(j+1)] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((queens[possibleMovesID-400].getXPos())*boxW, (queens[possibleMovesID-400].getYPos()-(j+1))*boxH, (boxW), (boxH));
								moveBoard[queens[possibleMovesID-400].getXPos()][queens[possibleMovesID-400].getYPos()-(j+1)] = (counter+1);
								counter++;
							}
						}
						for(int j = 0; j < bottomY; j++) {
							if(moveBoard[queens[possibleMovesID-400].getXPos()][queens[possibleMovesID-400].getYPos()+(j+1)]/10 == 8) {
								if(queens[possibleMovesID-400].getColor() !=moveBoard[queens[possibleMovesID-400].getXPos()][queens[possibleMovesID-400].getYPos()+(j+1)]-80) {
									possibleMovesRect[counter] = new Rectangle((queens[possibleMovesID-400].getXPos())*boxW, (queens[possibleMovesID-400].getYPos()+(j+1))*boxH, (boxW), (boxH));
									moveBoard[queens[possibleMovesID-400].getXPos()][queens[possibleMovesID-400].getYPos()+(j+1)] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((queens[possibleMovesID-400].getXPos())*boxW, (queens[possibleMovesID-400].getYPos()+(j+1))*boxH, (boxW), (boxH));
								moveBoard[queens[possibleMovesID-400].getXPos()][queens[possibleMovesID-400].getYPos()+(j+1)] = (counter+1);
								counter++;
							}
						}
						if(rightX > bottomY) {
							var1 = bottomY;
						}
						else {
							var1 = rightX;
						}
						for(int j = 0; j < var1; j++) {
							if(moveBoard[queens[possibleMovesID-400].getXPos()+(j+1)][queens[possibleMovesID-400].getYPos()+(j+1)]/10 == 8){
								if(queens[possibleMovesID-400].getColor() != moveBoard[queens[possibleMovesID-400].getXPos()+(j+1)][queens[possibleMovesID-400].getYPos()+(j+1)]-80) {
									possibleMovesRect[counter] = new Rectangle((queens[possibleMovesID-400].getXPos()+(j+1))*boxW, (queens[possibleMovesID-400].getYPos()+(j+1))*boxH, (boxW), (boxH));
									moveBoard[queens[possibleMovesID-400].getXPos()+(j+1)][queens[possibleMovesID-400].getYPos()+(j+1)] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((queens[possibleMovesID-400].getXPos()+(j+1))*boxW, (queens[possibleMovesID-400].getYPos()+(j+1))*boxH, (boxW), (boxH));
								moveBoard[queens[possibleMovesID-400].getXPos()+(j+1)][queens[possibleMovesID-400].getYPos()+(j+1)] = (counter+1);
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
							if(moveBoard[queens[possibleMovesID-400].getXPos()+(j+1)][queens[possibleMovesID-400].getYPos()-(j+1)]/10 == 8) {
								if(queens[possibleMovesID-400].getColor() != moveBoard[queens[possibleMovesID-400].getXPos()+(j+1)][queens[possibleMovesID-400].getYPos()-(j+1)]-80) {
									possibleMovesRect[counter] = new Rectangle((queens[possibleMovesID-400].getXPos()+(j+1))*boxW, (queens[possibleMovesID-400].getYPos()-(j+1))*boxH, (boxW), (boxH));
									moveBoard[queens[possibleMovesID-400].getXPos()+(j+1)][queens[possibleMovesID-400].getYPos()-(j+1)] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((queens[possibleMovesID-400].getXPos()+(j+1))*boxW, (queens[possibleMovesID-400].getYPos()-(j+1))*boxH, (boxW), (boxH));
								moveBoard[queens[possibleMovesID-400].getXPos()+(j+1)][queens[possibleMovesID-400].getYPos()-(j+1)] = (counter+1);
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
							if(moveBoard[queens[possibleMovesID-400].getXPos()-(j+1)][queens[possibleMovesID-400].getYPos()+(j+1)]/10 == 8) {
								if(queens[possibleMovesID-400].getColor() != moveBoard[queens[possibleMovesID-400].getXPos()-(j+1)][queens[possibleMovesID-400].getYPos()+(j+1)]-80) {
									possibleMovesRect[counter] = new Rectangle((queens[possibleMovesID-400].getXPos()-(j+1))*boxW, (queens[possibleMovesID-400].getYPos()+(j+1))*boxH, (boxW), (boxH));
									moveBoard[queens[possibleMovesID-400].getXPos()-(j+1)][queens[possibleMovesID-400].getYPos()+(j+1)] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((queens[possibleMovesID-400].getXPos()-(j+1))*boxW, (queens[possibleMovesID-400].getYPos()+(j+1))*boxH, (boxW), (boxH));
								moveBoard[queens[possibleMovesID-400].getXPos()-(j+1)][queens[possibleMovesID-400].getYPos()+(j+1)] = (counter+1);
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
							if(moveBoard[queens[possibleMovesID-400].getXPos()-(j+1)][queens[possibleMovesID-400].getYPos()-(j+1)]/10 == 8) {
								if(queens[possibleMovesID-400].getColor() != moveBoard[queens[possibleMovesID-400].getXPos()-(j+1)][queens[possibleMovesID-400].getYPos()-(j+1)]-80) {
									possibleMovesRect[counter] = new Rectangle((queens[possibleMovesID-400].getXPos()-(j+1))*boxW, (queens[possibleMovesID-400].getYPos()-(j+1))*boxH, (boxW), (boxH));
									moveBoard[queens[possibleMovesID-400].getXPos()-(j+1)][queens[possibleMovesID-400].getYPos()-(j+1)] = (counter+1);
									counter++;
								}
								break;
							}
							else {
								possibleMovesRect[counter] = new Rectangle((queens[possibleMovesID-400].getXPos()-(j+1))*boxW, (queens[possibleMovesID-400].getYPos()-(j+1))*boxH, (boxW), (boxH));
								moveBoard[queens[possibleMovesID-400].getXPos()-(j+1)][queens[possibleMovesID-400].getYPos()-(j+1)] = (counter+1);
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
						if(possibleMovesID/100 == 1) {
							for(int a = 0; a<moveBoard.length; a++) {
								for(int b = 0; b<moveBoard.length; b++) {
									if(moveBoard[a][b] == i+1) {
										board[pawns[possibleMovesID-100].getYPos()][pawns[possibleMovesID-100].getXPos()] = 0;
										pawns[possibleMovesID-100].changePos(a,b);
										pawns[possibleMovesID-100].madeMove(true);
										String tempPos = "1"+pawns[possibleMovesID-100].getId();
										board[pawns[possibleMovesID-100].getYPos()][pawns[possibleMovesID-100].getXPos()] = Integer.parseInt(tempPos);
										if(pawns[possibleMovesID-100].getColor() == 0) {
											whiteTurn = false;
										}
										else {
											whiteTurn = true;
										}
									}
								}
							}	
						}
						else if(possibleMovesID/100 == 2) {
							for(int a = 0; a<moveBoard.length; a++) {
								for(int b = 0; b<moveBoard.length; b++) {
									if(moveBoard[a][b] == i+1) {
										board[bishops[possibleMovesID-200].getYPos()][bishops[possibleMovesID-200].getXPos()] = 0;
										bishops[possibleMovesID-200].changePos(a,b);
										String tempPos = "2"+bishops[possibleMovesID-200].getId();
										board[bishops[possibleMovesID-200].getYPos()][bishops[possibleMovesID-200].getXPos()] = Integer.parseInt(tempPos);
										if(bishops[possibleMovesID-200].getColor() == 0) {
											whiteTurn = false;
										}
										else {
											whiteTurn = true;
										}
									}
								}
							}
						}
						else if(possibleMovesID/100 == 3) {
							for(int a = 0; a<moveBoard.length; a++) {
								for(int b = 0; b<moveBoard.length; b++) {
									if(moveBoard[a][b] == i+1) {
										board[rooks[possibleMovesID-300].getYPos()][rooks[possibleMovesID-300].getXPos()] = 0;
										rooks[possibleMovesID-300].changePos(a,b);
										String tempPos = "3"+rooks[possibleMovesID-300].getId();
										board[rooks[possibleMovesID-300].getYPos()][rooks[possibleMovesID-300].getXPos()] = Integer.parseInt(tempPos);
										if(rooks[possibleMovesID-300].getColor() == 0) {
											whiteTurn = false;
										}
										else {
											whiteTurn = true;
										}
									}
								}
							}
						}
						else if(possibleMovesID/100 == 4) {
							for(int a = 0; a<moveBoard.length; a++) {
								for(int b = 0; b<moveBoard.length; b++) {
									if(moveBoard[a][b] == i+1) {
										board[queens[possibleMovesID-400].getYPos()][queens[possibleMovesID-400].getXPos()] = 0;
										queens[possibleMovesID-400].changePos(a,b);
										String tempPos = "4"+queens[possibleMovesID-400].getId();
										board[queens[possibleMovesID-400].getYPos()][queens[possibleMovesID-400].getXPos()] = Integer.parseInt(tempPos);
										if(queens[possibleMovesID-400].getColor() == 0) {
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
					for(int i = 0; i < rectPawns.length; i++) {
						if(rectPawns[i].contains(e.getX(), e.getY())) {
							if(pawns[i].getColor() == 0 && whiteTurn) {
								possibleMoves = true;
								possibleMoves(100+i);
								break;
							}
							else {
								possibleMoves = false;
							}
							if(pawns[i].getColor() == 1 && !whiteTurn) {
								possibleMoves = true;
								possibleMoves(100+i);
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
					for(int i = 0; i < rectBishops.length; i++) {
						if(rectBishops[i].contains(e.getX(), e.getY())) {
							if(bishops[i].getColor() == 0 && whiteTurn) {
								possibleMoves = true;
								possibleMoves(200+i);
								break;
							}
							else {
								possibleMoves = false;
							}
							if(bishops[i].getColor() == 1 && !whiteTurn) {
								possibleMoves = true;
								possibleMoves(200+i);
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
					for(int i = 0; i < rectRooks.length; i++) {
						if(rectRooks[i].contains(e.getX(), e.getY())) {
							if(rooks[i].getColor() == 0 && whiteTurn) {
								possibleMoves = true;
								possibleMoves(300+i);
								break;
							}
							else {
								possibleMoves = false;
							}
							if(rooks[i].getColor() == 1 && !whiteTurn) {
								possibleMoves = true;
								possibleMoves(300+i);
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
					for(int i = 0; i < rectQueens.length; i++) {
						if(rectQueens[i].contains(e.getX(), e.getY())) {
							if(queens[i].getColor() == 0 && whiteTurn) {
								possibleMoves = true;
								possibleMoves(400+i);
								break;
							}
							else {
								possibleMoves = false;
							}
							if(queens[i].getColor() == 1 && !whiteTurn) {
								possibleMoves = true;
								possibleMoves(400+i);
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


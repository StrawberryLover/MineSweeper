import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Board {
	private int widht;
	private int height;
	private int cellSize;
	private int [] [] board;
	private double [] [] valuedBoard;
	private Calibration window;
	
	public Board(int w, int h, int _cellSize, Calibration calibration) {
		this.window = calibration;
		this.cellSize = _cellSize;
		this.widht = w;
		this.height = h;
		this.board = new int [w][h];			//Contains Board
		this.valuedBoard = new double [w][h]; 	//Contains Board with each cells heuristic
	}
	
	public void info() {
		System.out.println("Width: " + widht*cellSize + " [ " + widht + " ]");
		System.out.println("Height: " + height*cellSize + " [ " + height + " ]");
		System.out.println("Cell Size: " + cellSize);
	}

	public void touch() throws InterruptedException {
		for(int i = 0; i < widht; i++) {
			for(int j = 0; j < height; j++) {
				window.moveTo(i, j);
			}
		}
	}

	public boolean isWin() {
		for(int i = 0; i < widht; i++) {
			for(int j = 0; j < height; j++) {
				if(board[i][j] == 0)
					return false;
			}
		}
		
		return true;
	}

	public void makeMove() throws InterruptedException {
		int bestX = 0, bestY = 0;
		double bestPrecent = 0;
				
		for(int i = 0; i < widht; i++) {
			for(int j = 0; j < height; j++) {
				if(valuedBoard[i][j] > bestPrecent) {
					bestPrecent = valuedBoard[i][j];
					bestX = i;
					bestY = j;
				}
			}
		}
		
		window.mousePress(bestX, bestY);
		Thread.sleep(500);
		
		/* Does Its thing */
		
		BufferedImage myBoard = window.getBoard(widht ,height);
		
		analaysMove(myBoard);
		upDateBoard(myBoard);
		
		System.out.println("Done");
	}

	private void analaysMove(BufferedImage myBoard) throws InterruptedException {
		for(int i = 0; i < widht; i++) {
			for(int j = 0; j < height; j++) {
				int c = window.getCell(i, j, myBoard);

				System.out.print("["+i+"]["+j+"]");
				Color.toString(c);
				setCell(c, i, j, myBoard);
			}
		}
	}
	
	private void setCell(int c, int x, int y, BufferedImage myBoard) {
		Point pos = window.getPos(x, y);new Point(x, y);
		
	    if(Color.isBlue(c) && Color.isOne(pos, myBoard)) { 	//Cell is 1
	    	board[x][y] = 1;
	    } else if(Color.isTwo(pos, myBoard)) {				//Cell is 2
	    	board[x][y] = 2;
	    } else if(Color.isThree(pos, myBoard)) {			//Cell is 3
	    	board[x][y] = 3;
	    } else if(Color.isFour(pos, myBoard)) {				//Cell is 4
	    	board[x][y] = 4;
	    } else if(Color.isGray(c)) {	//Empty Cell
	    	board[x][y] = -1;
		} else
			System.out.println("Not Found");
	}
	
	private void upDateBoard(BufferedImage myBoard) {
		for(int i = 0; i < widht; i++) {
			for(int j = 0; j < height; j++) {
				if(board[i][j] > 0) {
					setHeuristic(i, j, board[i][j]);
				}
			}
		}
	}

	private void setHeuristic(int x, int y, int value) {
		ArrayList<Point> list = new ArrayList<Point>();
		
		for(int i = x-1; i < x+2; i++) {
			for(int j = y-1; j < y+2; j++) {
				if((i >= 0 && j >= 0) && (i < widht && j < height)) {
					if(board[i][j] == 0)
						list.add(new Point(i, j));
				}
			}
		}
		
			double present = 100 / list.size();
		present = present / value;

		for(Point cell : list) {
			valuedBoard[cell.x][cell.y] = present;
		}
	}
}

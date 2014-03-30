import java.awt.Point;
import java.awt.image.BufferedImage;
import CSP.CSP;


public class Board {
	private int widht;
	private int height;
	private int cellSize;
	private int [] [] board;
	private Calibration window;
	private CSP solver;
	
	public Board(int w, int h, int _cellSize, Calibration calibration) {
		this.window = calibration;
		this.cellSize = _cellSize;
		this.widht = w;
		this.height = h;	
		this.board = new int[w][h];			//Contains Board
		this.solver = new CSP(w, h);
	}
	
	public void info() {
		//System.out.println("Width: " + widht*cellSize + " [ " + widht + " ]");
		//System.out.println("Height: " + height*cellSize + " [ " + height + " ]");
		//System.out.println("Cell Size: " + cellSize);
	}
	
	public void print() {
		int i, j;
		
		for(i = 0; i < height; i++) {
			for(j = 0; j < widht; j++) {
				if(board[j][i] == -1)
					System.out.print("| " + board[j][i] + " |");
				else
					System.out.print("|  " + board[j][i] + " |");
			}
			
			//System.out.println();
		}
	}

	public void touch() throws InterruptedException {
		int i, j;

		for(i = 0; i < widht; i++) {
			for(j = 0; j < height; j++) {
				window.moveTo(i, j);
			}
		}
	}

	public boolean isWin() {
		int i, j;
		
		if(window.isDone())
			return true;
		
		for(i = 0; i < widht; i++) {
			for(j = 0; j < height; j++) {
				if(board[i][j] == 0)
					return false;
			}
		}
		
		return true;
	}

	public void makeMove() throws InterruptedException {
		Point p = solver.getNextMove();

		window.mousePress(p.x, p.y);
		Thread.sleep(500);
		
		/* Update board */
		BufferedImage myBoard = window.getBoard(widht ,height);
		analyzeMove(myBoard);
	
		solver.removePointFromConstraints(p);
		solver.newConstraintFromPoint(p, board[p.x][p.y]);
	}

	private void analyzeMove(BufferedImage myBoard) throws InterruptedException {
		int i, j, c;
		
		for(i = 0; i < widht; i++) {
			for(j = 0; j < height; j++) {
				c = window.getCell(i, j, myBoard);
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
	    } else if(Color.isGray(c))											//Empty Cell
	    	board[x][y] = -1;
	}
}

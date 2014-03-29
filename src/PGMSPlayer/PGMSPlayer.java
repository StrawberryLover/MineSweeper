package PGMSPlayer;

import java.awt.Point;

import CSP.CSP;
import map.Map;
import map.Strategy;

public class PGMSPlayer implements Strategy {
	CSP csp;
	int xSize, ySize;  
	int [][] state; 
	Point lastMove; 
	int lastResult; 
	Map map; 
	
	@Override
	public void play(Map m) {
		map = m; 
		xSize = (int) map.columns();
		ySize = (int) map.rows(); 
		csp = new CSP(xSize, ySize);
		initState(xSize, ySize);
		firstMove(); 
		csp.newConstraint(lastMove, lastResult);
		Point p; 
		boolean play = true; 
		while (play) {
			probe(csp.getNextMove()); 
			if (map.done()) {
				System.out.println("Game over!"); 
				play = false; 
			}
			else {
			}
		}
	}
	
	public void probe(Point p) {
		lastMove = p; 
		lastResult = map.probe(p.x, p.y);
		//-1 is programmer minesweeper for BOOM, so no need to add anything
		if (lastResult != -1) {
			state[p.x][p.y] = lastResult; 
			csp.removeConstraints(lastMove);
			csp.newConstraint(p,  lastResult);
		}
	}
	
	public void firstMove() {
		//clicking the center square is the best first move. 
		//if we blow up, oh well 
		lastMove = new Point(xSize/2, ySize/2);
		lastResult = map.probe(xSize/2, ySize/2);
	}
	
	/**
	 * Initializes the array with -1 for unprobed
	 * @param x
	 * @param y
	 */
	public void initState(int x, int y) {
		state = new int[x][y]; 
		for (int i = 0; i < x; i++) 
			for (int n = 0; n < y; n++) 
				state[i][n] = -1; 
	}
	
	/**
	 * Map map codes to integers
	 * @param x
	 * @return
	 */
	public int processResult(int x) {
		if (x == Map.MARKED)
			return 9; 
		if (x == Map.UNPROBED)
			return -1; 
		else 
			return x;
	}
}

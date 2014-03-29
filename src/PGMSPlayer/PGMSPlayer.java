package PGMSPlayer;

import java.awt.Point;

import CSP.CSP;
import map.Map;
import map.Strategy;

public class PGMSPlayer implements Strategy {
	CSP csp;
	int xSize, ySize;  
	Map map; 
	
	@Override
	public void play(Map m) {
		map = m; 
		xSize = (int) map.columns();
		ySize = (int) map.rows(); 
		csp = new CSP(xSize, ySize);

		firstMove(); 

		while (map.done()) {
			probe(csp.getNextMove()); 
		}
		
		System.out.println("Game over!"); 
	}
	
	public void probe(Point p) {
		Point lastMove = p; 
		int lastResult = map.probe(p.x, p.y);
		//-1 is programmer minesweeper for BOOM, so no need to add anything
		if (lastResult != -1) {
			csp.removePointFromConstraints(lastMove);
			csp.newConstraintFromPoint(p,  lastResult);
		}
	}
	
	public void firstMove() {
		//clicking the center square is the best first move. 
		//if we blow up, oh well 
		Point lastMove = new Point(xSize/2, ySize/2);
		int lastResult = map.probe(xSize/2, ySize/2);
		
		csp.newConstraintFromPoint(lastMove, lastResult);
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

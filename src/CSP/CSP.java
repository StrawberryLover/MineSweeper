package CSP;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class CSP {
	int [][] knownPoints; 
	List<Constraint> constraints; 
	
	public CSP (int boardSizeX, int boardSizeY) {
		knownPoints = new int[boardSizeX][boardSizeY];
		constraints = new ArrayList<Constraint>();
		
		for (int i = 0; i < boardSizeX; i++) 
			for (int j = 0; j < boardSizeY; j++) 
				knownPoints[i][j] = -1;
	}
	
	public CSP() {
		constraints = new ArrayList<Constraint>();
	}
	
	/**
	 * Removes given point from all constraints. 
	 * This should be called every time a cell is clicked in the game. 
	 * Note: We simply after removing the point, as removing a point will probably have resulted in 
	 * new simplification options. 
	 * This in turn will probably result in more known bombs or clear points. 
	 * @param point
	 */
	public void removeConstraints(Point point) {
		for (Constraint c : constraints) {
			if (c.cells.contains(point)) {
				c.cells.remove(point); 
			}
		}
		
		//We must simplify after removing a point
		simplifyConstraints(); 
	}
	
	/**
	 * Add a new constraint. This is called when a new point is revealed. 
	 * Every unrevealed around the point is added
	 *  to a constraint with the value of the point. 
	 */
	public void newConstraint(Point origin, int value) {
		System.out.println("Adding constraint to point " + origin.x + "." + origin.y + " value " + value);
		List<Point> points = new ArrayList<Point>();
		
		//add every point around the point to the constraint if it is unknown
		int i, j; 
		for (i = -1; i < 2; i++) {
			for (j = -1; j < 2; j++) {
				//if we are at the origin we obviously do nothing
				if (!(i == 0 && j == 0)) {
					try { 
						if (knownPoints[origin.x + i][origin.y + j] == -1) { 
							points.add(new Point(origin.x+i, origin.y+j)); 
						} 
					}
					catch (ArrayIndexOutOfBoundsException e) {
						//Do nothing. This is required if we are inspecting the area around a border point
					} 	
				}
			}
		}
		
		if (points.size() > 0) {
			constraints.add(new Constraint(points, value));
		}
		
		simplifyConstraints(); 
	}
	
	/**
	 * adds a new constraint to the list of constraints. 
	 */
	public void addConstraint(List<Point> points, int value) {
		constraints.add(new Constraint(points, value));
	}
	
	/**
	 * Iterate through the list of constraints and check if they can be simplified. 
	 * This should be called every time a new constraint is added to the list. 
	 */
	public void simplifyConstraints() {
		System.out.println("Simplifying constraints...");
		int i, j;
		
		for (i = 0; i < constraints.size(); i++) {
			for (j = 0; j < constraints.size(); j++) {
				if (i != j) {
					//if n is a subset of i
					if (constraints.get(i).cells.containsAll(constraints.get(j).cells)) {
						//remove n from i and subtract n's value from i's value
						constraints.get(i).cells.removeAll(constraints.get(j).cells);
						constraints.get(i).value -= constraints.get(j).value; 
					}
				}
			}
		}
	}

	
	/**
	 * If the value of a constraint is equal to the number of cells in it, all of the cells in the constraint are bombs. 
	 * Ex: The value of a constraint is 2 and the constraint contains points 0,1 and 0,2. Both are bombs.
	 */
	public List<Point> getKnownBombs() {
		List<Point> bombs = new ArrayList<Point>();
		for (Constraint c : constraints) {
			if (c.value == c.cells.size()) {
				bombs.addAll(c.cells);
			}
		}
		return bombs; 
	}
	
	/**
	 * If the value of a constraint is 0, every cell in it is not a bomb and should be clicked. 
	 * Ex: The value of a constraint is 0, and it contains 0,1 and 0,2. Both are known to be not bombs. 
	 */
	public List<Point> getKnownClearPoints() {
		System.out.println("Getting clear points...");
		List<Point> clear = new ArrayList<Point>(); 
		for (Constraint c : constraints) {
			if (c.value == 0) {
				clear.addAll(c.cells); 
			}
		}
		
		System.out.println("Found " + clear.size() + " clear points");
		return clear; 
	}
	
	/**
	 * First tries to find a known clear point. If none is found, select a random point. 
	 * @return
	 */
	public Point getNextMove() {
		List<Point> clearPoints = getKnownClearPoints(); 
		if (clearPoints.size() == 0) {
			return new Point(0, 0);
		} else {
			System.out.println("Returning clear point " + clearPoints.get(0).x + "." + clearPoints.get(0).y);
			return clearPoints.get(0);
		}
	}
	
	private int randomIntInRange(int min, int max) {
		return min + (int)(Math.random() * ((max - min) + 1));
	}
}

package CSP;

import java.awt.Point;
import java.util.List;

public class Constraint {
	List<Point> cells; 
	int value; 
	
	public Constraint(List<Point> cells, int value) {
		this.cells = cells; 
		this.value = value; 
	}
	
	@Override 
	public boolean equals(Object obj) {
		Constraint that; 
		try {
			that = (Constraint) obj;
		}
		catch (Exception e) {
			//if cast fails, not a constraint
			return false; 
		}
		//if not of same size
		if (this.cells.size() != that.cells.size()) {
			return false; 
		}
		//if each point we have is not contained in their cells
		for (Point c : this.cells) {
			if (!that.cells.contains(c)) {
				return false; 
			}
		}
		//if all these tests passed, we're equal
		return true; 
	}
}

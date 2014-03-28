package CSP;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


public class CSP {
	List<Constraint> constraints; 
	
	public CSP() {
		constraints = new ArrayList<Constraint>();
	}
	public void addConstraint(List<Point> points, int value) {
		constraints.add(new Constraint(points, value));
	}
	
	/**
	 * Iterate through the list of contraints and check if they can be simplified. 
	 */
	public void simplifyConstraints() {
		int i, n;
		for (i = 0; i < constraints.size(); i++) {
			for (n = 0; n < constraints.size(); n++) {
				if (i != n) {
					//if n is a subset of i
					if (constraints.get(i).cells.containsAll(constraints.get(n).cells)) {
						//remove n from i and subtract n's value from i's value
						constraints.get(i).cells.removeAll(constraints.get(n).cells);
						constraints.get(i).value -= constraints.get(n).value; 
					}
				}
			}
		}
	}
}

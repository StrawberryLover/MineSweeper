package CSP;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CSPTest {

	CSP csp; 
	@Before
	public void setUp() throws Exception {
		csp = new CSP(); 
	}

	@After
	public void tearDown() throws Exception {
		csp = null;
	}
	
	@Test
	public void testAddConstraint() {
		csp.addConstraint(new ArrayList<Point>(), 1); 
		assertEquals(1, csp.constraints.size());
	}
	@Test
	public void testSimplifyConstraints() {
		List<Point> points1 = new ArrayList<Point>(); 
		points1.add(new Point(1,1));
		points1.add(new Point (2,2));
		points1.add(new Point(3,3));
		points1.add(new Point(4,4)); 
		csp.constraints.add(new Constraint(points1, 2)); 
		
		List<Point> points2 = new ArrayList<Point>(); 
		points2.add(new Point(1,1)); 
		points2.add(new Point(2,2)); 
		csp.constraints.add(new Constraint(points2, 1)); 
		//The list should be simplified to 3,3 + 4,4 = 1 and 1,1 + 2,2 = 1
		csp.simplifyConstraints(); 
		assertEquals(1, csp.constraints.get(1).value);
		assertEquals(1, csp.constraints.get(0).value);
		assertEquals(new Point(3,3), csp.constraints.get(0).cells.get(0));
		assertEquals(new Point(4,4), csp.constraints.get(0).cells.get(1));
	}
	
	@Test
	public void testSimplifyConstraints2() {
		List<Point> points1 = new ArrayList<Point>(); 
		points1.add(new Point(1,1));
		points1.add(new Point (2,2));
		points1.add(new Point(3,3));
		points1.add(new Point(4,4)); 
		csp.constraints.add(new Constraint(points1, 1)); 
		
		List<Point> points2 = new ArrayList<Point>(); 
		points2.add(new Point(5,5)); 
		points2.add(new Point(6,6)); 
		csp.constraints.add(new Constraint(points2, 2)); 
		
		//nothing should happen
		csp.simplifyConstraints(); 
		assertEquals(1, csp.constraints.get(0).value); 
		assertEquals(2, csp.constraints.get(1).value);
	}
	
	@Test 
	public void testSimplifyConstraints3() {
		List<Point> points1 = new ArrayList<Point>(); 
		points1.add(new Point(1,1));
		points1.add(new Point (2,2));
		points1.add(new Point(3,3));
		points1.add(new Point(4,4)); 
		csp.constraints.add(new Constraint(points1, 2)); 
		
		List<Point> points2 = new ArrayList<Point>(); 
		points2.add(new Point(1,1)); 
		points2.add(new Point(2,2)); 
		csp.constraints.add(new Constraint(points2, 1)); 
		
		List<Point> points3 = new ArrayList<Point>(); 
		points3.add(new Point(5,5));
		points3.add(new Point(6,6));
		csp.constraints.add(new Constraint(points3, 3));
		//The list should be simplified to 3,3 + 4,4 = 1 and 1,1 + 2,2 = 1, but the third element should be untouched
		csp.simplifyConstraints(); 
		assertEquals(1, csp.constraints.get(0).value);
		assertEquals(1, csp.constraints.get(1).value);
		assertEquals(3, csp.constraints.get(2).value);
		assertEquals(new Point(5,5), csp.constraints.get(2).cells.get(0));
		assertEquals(new Point(6,6), csp.constraints.get(2).cells.get(1));
	}
	
	private void setUpTestNewConstraint() {
		csp.knownPoints = new int[3][3]; 
		for (int i = 0; i < 3; i++) 
			for (int n = 0; n < 3; n++) 
				csp.knownPoints[i][n] = -1;
	}
	
	@Test
	public void testNewConstraintFromPoint1() {
		setUpTestNewConstraint(); 
		csp.newConstraintFromPoint(new Point(1,1), 2); 
		assertEquals(1, csp.constraints.size()); 
		assertEquals(8, csp.constraints.get(0).cells.size());
		assertEquals(true, csp.constraints.get(0).cells.contains(new Point(2,2)));
		assertEquals(true, csp.constraints.get(0).cells.contains(new Point(1,2)));
		assertEquals(true, csp.constraints.get(0).cells.contains(new Point(0,2)));
		assertEquals(true, csp.constraints.get(0).cells.contains(new Point(0,1)));
	}
	
	@Test
	public void testNewConstraintFromPoint2() {
		setUpTestNewConstraint(); 
		csp.newConstraintFromPoint(new Point(0,0), 2); 
		assertEquals(1, csp.constraints.size()); 
		assertEquals(3, csp.constraints.get(0).cells.size());
		assertEquals(true, csp.constraints.get(0).cells.contains(new Point(0,1)));
		assertEquals(true, csp.constraints.get(0).cells.contains(new Point(1,1)));
		assertEquals(true, csp.constraints.get(0).cells.contains(new Point(1,0)));
	}
	
	@Test
	public void testNewConstraintFromPoint3() {
		setUpTestNewConstraint(); 
		csp.newConstraintFromPoint(new Point(2,2), 2); 
		assertEquals(1, csp.constraints.size()); 
		assertEquals(3, csp.constraints.get(0).cells.size());
		assertEquals(true, csp.constraints.get(0).cells.contains(new Point(2,1)));
		assertEquals(true, csp.constraints.get(0).cells.contains(new Point(1,1)));
		assertEquals(true, csp.constraints.get(0).cells.contains(new Point(1,2)));
	}
	
	@Test
	public void testNewConstraintFromPoint4() {
		setUpTestNewConstraint(); 
		csp.newConstraintFromPoint(new Point(1,2), 2); 
		assertEquals(1, csp.constraints.size()); 
		assertEquals(5, csp.constraints.get(0).cells.size());
		assertEquals(true, csp.constraints.get(0).cells.contains(new Point(0,2)));
		assertEquals(true, csp.constraints.get(0).cells.contains(new Point(2,2)));
		assertEquals(true, csp.constraints.get(0).cells.contains(new Point(1,1)));
		assertEquals(true, csp.constraints.get(0).cells.contains(new Point(0,1)));
	}
	
	@Test
	public void testNewConstraintFromPoint5() {
		csp.knownPoints = new int[1][1]; 
		csp.newConstraintFromPoint(new Point(0,0), 2);
		assertEquals(0, csp.constraints.size());
	}
	
	@Test
	public void testGetKnownBombs() {
		List<Point> points = new ArrayList<Point>(); 
		points.add(new Point(1,1)); 
		points.add(new Point(2,2)); 
		csp.constraints.add(new Constraint(points, 2)); 
		points = new ArrayList<Point>(); 
		points.add(new Point(3,3)); 
		points.add(new Point(4,4)); 
		csp.constraints.add(new Constraint(points, 4)); 
		
		List<Point> bombs = csp.getKnownBombs(); 
		assertEquals(2, bombs.size()); 
		assertEquals(true, bombs.contains(new Point(1,1)));
		assertEquals(true, bombs.contains(new Point(2,2)));
		assertEquals(false, bombs.contains(new Point(3,3)));
	}
	
	@Test
	public void testGetKnownClearPoints() {
		List<Point> points = new ArrayList<Point>(); 
		points.add(new Point(1,1)); 
		points.add(new Point(2,2)); 
		csp.constraints.add(new Constraint(points, 2)); 
		points = new ArrayList<Point>(); 
		points.add(new Point(3,3)); 
		points.add(new Point(4,4)); 
		csp.constraints.add(new Constraint(points, 0)); 
		
		List<Point> clears = csp.getKnownClearPoints(); 
		assertEquals(2, clears.size());
		assertEquals(true, clears.contains(new Point(3,3)));
		assertEquals(true, clears.contains(new Point(4,4)));
	}
	
	@Test
	public void removePointFromConstraints() {
		List<Point> points = new ArrayList<Point>(); 
		points.add(new Point(1,1)); 
		points.add(new Point(2,2)); 
		points.add(new Point(3,3)); 
		points.add(new Point(4,4)); 
		csp.constraints.add(new Constraint(points, 2));
		List<Point> points2 = new ArrayList<Point>(); 
		points2.add(new Point(1,1)); 
		points2.add(new Point(2,2));
		points2.add(new Point(5,5));
		csp.constraints.add(new Constraint(points2, 1));
		List<Point> points3 = new ArrayList<Point>(); 
		points3.add(new Point(6,6));
		points3.add(new Point(7,7)); 
		csp.constraints.add(new Constraint(points3,1));
		
		csp.removePointFromConstraints(new Point(1,1));
		
		assertEquals(false, csp.constraints.get(0).cells.contains(new Point(1,1)));
		assertEquals(3, csp.constraints.get(0).cells.size());
		assertEquals(2, csp.constraints.get(1).cells.size());
		assertEquals(2, csp.constraints.get(2).cells.size());
	}
	
	private void setUpEnd2End() {
		csp.knownPoints = new int[5][5]; 
		for (int i = 0; i < 5; i++) 
			for (int n = 0; n < 5; n++) 
				csp.knownPoints[i][n] = -1;
	}
	
	@Test
	public void end2endTest1() {
		csp = new CSP(5,5); 
		csp.newConstraintFromPoint(new Point(3,3), 0); 
		assertEquals(new Point(2,2), csp.getNextMove()); 
		
	}
}

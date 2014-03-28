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

}

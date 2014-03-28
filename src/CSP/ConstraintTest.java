package CSP;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConstraintTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testEquals() {
		List<Point> points1 = new ArrayList<Point>();
		points1.add(new Point(1,1)); 
		points1.add(new Point(2,2)); 
		Constraint c1 = new Constraint(points1, 1); 
		
		List<Point> points2 = new ArrayList<Point>(); 
		points2.add(new Point(1,1)); 
		points2.add(new Point(2,2)); 
		Constraint c2 = new Constraint(points2, 1); 
		
		assertEquals(true, c1.equals(c2));
		c1.cells.add(new Point(3,3));
		assertEquals(false, c1.equals(c2));
		c2.cells.add(new Point(4,4));
		assertEquals(false, c1.equals(c2));
	}
}

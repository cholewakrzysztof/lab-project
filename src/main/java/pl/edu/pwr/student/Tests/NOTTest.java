package pl.edu.pwr.student.Tests;

import junit.framework.TestCase;
import pl.edu.pwr.student.Gates.BasicGates.NOT;
import pl.edu.pwr.student.IO.Input.Switch;

public class NOTTest extends TestCase {
	private Switch FirstInput = new Switch();
	private NOT gate = new NOT();
	
	protected void setUp() {
		FirstInput.connection(gate);
	}
	
	/*
	 *Test NOT gate input 0
	 */
	public void testTruthTableCase1() {
		assertTrue(gate.getState());
	}
	
	/*
	 *Test NOT gate input 1
	 */
	public void testTruthTableCase2() {
		FirstInput.toggle();
		assertFalse(gate.getState());
	}
}
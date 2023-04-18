package pl.edu.pwr.student.Tests;

import junit.framework.TestCase;
import pl.edu.pwr.student.Gates.BasicGates.NAND;
import pl.edu.pwr.student.IO.Input.Switch;

public class NANDTest extends TestCase {
	private Switch FirstInput = new Switch();
	private Switch SecondInput = new Switch();
	private NAND gate = new NAND();
	
	protected void setUp() {
		FirstInput.connection(gate);
		SecondInput.connection(gate);
	}
	
	/*
	 *Test NAND gate input 0 and 0
	 */
	public void testTruthTableCase1() {
		assertTrue(gate.getState());
	}
	
	/*
	 *Test NAND gate input 1 and 0
	 */
	public void testTruthTableCase2() {
		FirstInput.toggle();
		assertTrue(gate.getState());
	}
	/*
	 * Test NAND gate input 0 and 1
	 */
	public void testTruthTableCase3() {
		SecondInput.toggle();
		assertTrue(gate.getState());
	}
	/*
	 *Test NAND gate input 1 and 1
	 */
	public void testTruthTableCase4() {
		FirstInput.toggle();
		SecondInput.toggle();
		
		assertFalse(gate.getState());
	}
}
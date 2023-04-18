package pl.edu.pwr.student.Tests;

import junit.framework.TestCase;
import pl.edu.pwr.student.Gates.BasicGates.XOR;
import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.IO.Output.LED;

public class XORTest extends TestCase {
	private Switch FirstInput = new Switch();
	private Switch SecondInput = new Switch();
	private LED LED = new LED("led",100);
	private XOR gate = new XOR();
	
	protected void setUp() {
		FirstInput.connection(gate);
		SecondInput.connection(gate);
		LED.connection(gate);
	}
	
	/*
	 *Test XOR gate input 0 and 0
	 */
	public void testTruthTableCase1() {
		assertFalse(gate.getState());
	}
	
	/*
	 *Test XOR gate input 1 and 0
	 */
	public void testTruthTableCase2() {
		FirstInput.toggle();
		assertTrue(gate.getState());
	}
	/*
	 * Test XOR gate input 0 and 1
	 */
	public void testTruthTableCase3() {
		SecondInput.toggle();
		assertTrue(gate.getState());
	}
	/*
	 *Test XOR gate input 1 and 1
	 */
	public void testTruthTableCase4() {
		FirstInput.toggle();
		SecondInput.toggle();
		
		assertFalse(gate.getState());
	}
	/*
	 * Test connection with two inputs
	 */
	public void testGateHasInputs() {
		assertTrue(gate.hasInputs());
	}
	/*
	 * Test disconnect inputs
	 */
	public void testDisconnectInputs() {
		gate.disconnectInputs();
		assertFalse(gate.hasInputs());
	}
	/*
	 * Test full disconnection
	 */
	public void testFullDisconnect() {
		gate.fullDisconnect();
		assertFalse(gate.hasInputs());
	}
}
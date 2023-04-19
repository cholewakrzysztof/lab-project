package pl.edu.pwr.student.Tests;

import junit.framework.TestCase;
import pl.edu.pwr.student.Gates.BasicGates.NOT;
import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.IO.Output.LED;

public class NOTTest extends TestCase {
	private Switch FirstInput = new Switch();
	private LED LED = new LED("led",100);
	private NOT gate = new NOT();
	
	protected void setUp() {
		FirstInput.connection(gate);
		gate.connection(LED);
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
	/*
	 * Test disconnect outputs
	 */
	public void testDisconnectOutputs() {
		gate.disconnectOutputs();
		assertEquals(LED.getOutputs().size(),0);
	}
}
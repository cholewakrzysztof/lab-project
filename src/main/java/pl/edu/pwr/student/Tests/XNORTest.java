package pl.edu.pwr.student.Tests;

import junit.framework.TestCase;
import pl.edu.pwr.student.Gates.BasicGates.XNOR;
import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.IO.Output.LED;

public class XNORTest extends TestCase {
	private Switch FirstInput = new Switch();
	private Switch SecondInput = new Switch();
	private LED LED = new LED("led",100);
	private XNOR gate = new XNOR();
	
	protected void setUp() {
		FirstInput.connection(gate);
		SecondInput.connection(gate);
		gate.connection(LED);
	}
	
	/*
	 *Test XNOR gate input 0 and 0
	 */
	public void testTruthTableCase1() {
		assertTrue(gate.getState());
	}
	
	/*
	 *Test XNOR gate input 1 and 0
	 */
	public void testTruthTableCase2() {
		FirstInput.toggle();
		assertFalse(gate.getState());
	}
	/*
	 * Test XNOR gate input 0 and 1
	 */
	public void testTruthTableCase3() {
		SecondInput.toggle();
		assertFalse(gate.getState());
	}
	/*
	 *Test XNOR gate input 1 and 1
	 */
	public void testTruthTableCase4() {
		FirstInput.toggle();
		SecondInput.toggle();
		
		assertTrue(gate.getState());
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
		assertEquals(LED.getInputs().size(),0);
	}
}
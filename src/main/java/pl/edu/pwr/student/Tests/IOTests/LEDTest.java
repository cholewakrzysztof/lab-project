package pl.edu.pwr.student.Tests.IOTests;

import junit.framework.TestCase;
import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.IO.Output.LED;

public class LEDTest extends TestCase {
	private Switch FirstInput = new Switch();
	private LED LED = new LED("LED",100);
	
	protected void setUp() {
		FirstInput.connection(LED);
	}
	
	/*
	 *Test printer set to OFF
	 */
	public void testSetOFF() {
		assertFalse(LED.getState());
	}
	/*
	 *Test printer set to ON
	 */
	public void testSetON() {
		FirstInput.toggle();
		assertTrue(LED.getState());
	}
	/*
	 * Test change frequency success (to 2)
	 */
	public void testChangeFrequencySuccess() {
		LED.changeUpdateFreq(2);
		FirstInput.toggle();
		assertTrue(LED.getState());
	}
	/*
	 * Test change frequency failure (to 1)
	 */
	public void testChangeFrequencyFailure() {
		LED.changeUpdateFreq(1);
		assertFalse(LED.getState());
	}
}
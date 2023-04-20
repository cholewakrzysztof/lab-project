package pl.edu.pwr.student.Tests.IOTests;

import junit.framework.TestCase;
import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.IO.Output.Speaker;

public class SpeakerTest extends TestCase {
	private Switch FirstInput = new Switch();
	private Speaker Speaker = new Speaker();
	
	protected void setUp() {
		FirstInput.connection(Speaker);
	}
	
	/*
	 *Test Speaker set to OFF
	 */
	public void testSetOFF() {
		assertFalse(Speaker.getState());
	}
	/*
	 *Test Speaker set to ON
	 */
	public void testSetON() {
		FirstInput.toggle();
		assertTrue(Speaker.getState());
	}
}
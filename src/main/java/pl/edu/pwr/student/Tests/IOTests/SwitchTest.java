package pl.edu.pwr.student.Tests.IOTests;

import static pl.edu.pwr.student.Simulation.simWait;

import junit.framework.TestCase;
import pl.edu.pwr.student.IO.Input.Switch;

public class SwitchTest extends TestCase {
	private Switch S = new Switch();
	
	/*
	 * Test output set to ON
	 */
	public void testToggleOn() {
	    S.toggle();
	    
	    assertTrue(S.getState());
	}
	
	/*
	 * Test output set to OFF
	 */
	public void testToggleOff() {
	    S.toggle();
	    S.toggle();
	    
	    assertFalse(S.getState());
	}
	
	/*
	 * Test state before change
	 */
	public void testPressOn() {
		S.press(150);
		simWait(149);
		assertTrue(S.getState());
	}
	
	/*
	 * Test state after change
	 */
	public void testPressOff() {
		S.press(150);
		simWait(151);
		assertFalse(S.getState());
	}
}

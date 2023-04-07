package pl.edu.pwr.student.Tests;

import static pl.edu.pwr.student.Simulation.simWait;

import junit.framework.TestCase;
import pl.edu.pwr.student.IO.Input.Switch;

public class SwitchTest extends TestCase {
	private Switch S = new Switch();
	
	public void testToggleOn() {
	    S.toggle();
	    
	    assertTrue(S.getState());
	}
	
	public void testToggleOff() {
	    S.toggle();
	    S.toggle();
	    
	    assertFalse(S.getState());
	}
	
	public void testPressOn() {
		S.press(150);
		simWait(149);
		assertTrue(S.getState());
	}
	
	public void testPressOff() {
		S.press(150);
		simWait(151);
		assertFalse(S.getState());
	}
}

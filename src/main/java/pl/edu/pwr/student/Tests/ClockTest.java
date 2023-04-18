package pl.edu.pwr.student.Tests;

import junit.framework.TestCase;
import pl.edu.pwr.student.IO.Input.Clock;
import static pl.edu.pwr.student.Simulation.simWait;

public class ClockTest extends TestCase {
	//100ms On, 100ms Off
	private Clock c = new Clock(100,100);
	
	public void testWitoutPower() {
		c.run();
		
		simWait(99);
	    assertFalse(c.getState());
	}
	
	public void testWithPower() {
		c.toggle();
		c.run();
		
		simWait(99);
	    assertTrue(c.getState());
	}
	
	public void testTurnOffAfterTime() {
		c.toggle();
		c.run();
		
		simWait(101);
		
	    assertFalse(c.getState());
	}
	
	public void testTurnOnAfterTime() {
		c.toggle();
		c.run();
		
		simWait(201);
	    assertTrue(c.getState());
	}
}

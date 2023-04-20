package pl.edu.pwr.student.Tests.GateTests;

import static pl.edu.pwr.student.Simulation.simWait;

import junit.framework.TestCase;
import pl.edu.pwr.student.Gates.Delay;
import pl.edu.pwr.student.IO.Input.Switch;

public class DelayTest extends TestCase {
	protected Switch FirstInput = new Switch();
	protected Delay delay = new Delay(100);
	
	protected void setUp() {
		if(FirstInput.getState())
			FirstInput.toggle();
		
		FirstInput.connection(delay);
	}
	
	/*
	 * Check state before updating state
	 */
	public void testStateBeforeUpdate() {
		FirstInput.toggle();
		
		simWait(50);
		assertFalse(delay.getState());
	}
	
	/*
	 * Check state after updating state
	 */
	public void testStateAfterUpdate() {
		FirstInput.toggle();
		
		simWait(150);
		assertFalse(delay.getState());
	}

}

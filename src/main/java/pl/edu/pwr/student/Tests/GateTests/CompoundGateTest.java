package pl.edu.pwr.student.Tests.GateTests;

import junit.framework.TestCase;
import pl.edu.pwr.student.Gates.BasicGates.NOR;
import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.IO.Output.LED;

public class CompoundGateTest extends TestCase {
	private Switch R = new Switch();
	private Switch S = new Switch();
	private LED out = new LED("SR Latch", 1000);
	private NOR norUp = new NOR();
	private NOR norDown = new NOR();
	
	/*
	 * Create gate SRLatch from examples
	 */
	protected void setUp() {
		if(R.getState())
			R.toggle();
		if(S.getState())
			S.toggle();
		
        R.connection(norUp);
        S.connection(norDown);
        norDown.connection(norUp);
        norUp.connection(norDown);
        norUp.connection(out);
	}
	
	/*
	 * Test gate work input 0 and 0
	 */
	public void testWorkCase1() {
		assertFalse(out.getState());
	}
	/*
	 * Test gate work input 0 and 1
	 */
	public void testWorkCase2() {
		R.toggle();
		assertFalse(out.getState());
	}
	/*
	 * Test gate work input 1 and 0
	 */
	public void testWorkCase3() {
		S.toggle();
		assertTrue(out.getState());
	}
	/*
	 * 
	 */
}
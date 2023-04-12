package pl.edu.pwr.student.Tests;

import junit.framework.TestCase;
import pl.edu.pwr.student.Gates.BasicGates.AND;
import pl.edu.pwr.student.IO.Input.Switch;

import static pl.edu.pwr.student.Simulation.simWait;

public class ANDTest extends TestCase {
	private Switch FirstInput = new Switch();
	private Switch SecondInput = new Switch();
	private AND gate = new AND();
	
	protected void setUp() {
		FirstInput.connection(gate);
		SecondInput.connection(gate);
	}
	
	public void testTruthTableCase1() {
		FirstInput.toggle();
		
	}
	public void testTruthTableCase2() {
		
	}
	public void testTruthTableCase3() {
	
	}
	public void testTruthTableCase4() {
	
	}
}

package pl.edu.pwr.student.Tests;

import junit.framework.TestCase;
import pl.edu.pwr.student.Gates.BasicGates.NOT;
import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.IO.Output.Printer;

public class PrinterTest extends TestCase {
	private Switch FirstInput = new Switch();
	private Printer printer = new Printer("printer");
	
	protected void setUp() {
		FirstInput.connection(printer);
	}
	
	/*
	 *Test printer set to OFF
	 */
	public void testSetOFF() {
		assertFalse(printer.getState());
	}
	/*
	 *Test printer set to ON
	 */
	public void testSetON() {
		FirstInput.toggle();
		assertTrue(printer.getState());
	}
}

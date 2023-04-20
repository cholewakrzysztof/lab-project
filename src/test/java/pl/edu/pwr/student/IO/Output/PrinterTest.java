package pl.edu.pwr.student.IO.Output;

import org.junit.jupiter.api.Test;
import pl.edu.pwr.student.IO.Input.Switch;

import static org.junit.jupiter.api.Assertions.*;

class PrinterTest {
    private Switch FirstInput = new Switch();
    private Printer printer = new Printer("printer");

    protected void setUp() {
        FirstInput.connection(printer);
    }

    /*
     *Test printer set to OFF
     */
    @Test
    public void testSetOFF() {
        assertFalse(printer.getState());
    }
    /*
     *Test printer set to ON
     */
    @Test
    public void testSetON() {
        FirstInput.toggle();
        assertTrue(printer.getState());
    }
}
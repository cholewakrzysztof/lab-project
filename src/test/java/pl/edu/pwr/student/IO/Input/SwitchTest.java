package pl.edu.pwr.student.IO.Input;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pwr.student.IO.Output.Speaker;

import static org.junit.jupiter.api.Assertions.*;

class SwitchTest {
    private Switch FirstInput = new Switch();
    private pl.edu.pwr.student.IO.Output.Speaker Speaker = new Speaker();

    @BeforeEach
    protected void setUp() {
        FirstInput.connection(Speaker);
    }

    /*
     *Test Speaker set to OFF
     */
    @Test
    public void testSetOFF() {
        assertFalse(Speaker.getState());
    }
    /*
     *Test Speaker set to ON
     */
    @Test
    public void testSetON() {
        FirstInput.toggle();
        assertTrue(Speaker.getState());
    }
}
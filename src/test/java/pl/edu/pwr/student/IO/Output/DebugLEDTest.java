package pl.edu.pwr.student.IO.Output;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pwr.student.IO.Input.Switch;

import static org.junit.jupiter.api.Assertions.*;

class DebugLEDTest {
    private Switch FirstInput = new Switch();
    private DebugLED LED = new DebugLED("LED",100);

    @BeforeEach
    protected void setUp() {
        FirstInput.connection(LED);
    }

    /*
     *Test printer set to OFF
     */
    @Test
    public void testSetOFF() {
        assertFalse(LED.getState());
    }
    /*
     *Test printer set to ON
     */
    @Test
    public void testSetON() {
        FirstInput.react();
        assertTrue(LED.getState());
    }
    /*
     * Test change frequency success (to 2)
     */
    @Test
    public void testChangeFrequencySuccess() {
        LED.changeUpdateFreq(2);
        FirstInput.react();
        assertTrue(LED.getState());
    }
    /*
     * Test change frequency failure (to 1)
     */
    @Test
    public void testChangeFrequencyFailure() {
        LED.changeUpdateFreq(1);
        assertFalse(LED.getState());
    }
}
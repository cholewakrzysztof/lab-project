package pl.edu.pwr.student.IO.Input;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static pl.edu.pwr.student.Simulation.simWait;

class ClockTest {
    /*
     * Creating clock working 100ms On, 100ms Off
     */
    private Clock c = new Clock(100,100);

    /*
     * Test clock state if no power
     */
    @Test
    public void testWitoutPower() {
        simWait(99);
        assertFalse(c.getState());
    }

    /*
     * Test clock state with power
     */
    @Test
    public void testWithPower() {
        c.toggle();

        simWait(99);
        assertTrue(c.getState());
    }

    /*
     * Test state after one change
     */
    @Test
    public void testTurnOffAfterTime() {
        c.toggle();

        simWait(101);
        assertFalse(c.getState());

    }

    /*
     * Test state after two changes
     */
    @Test
    public void testTurnOnAfterTime() {
        c.toggle();

        simWait(201);
        assertTrue(c.getState());
    }
}


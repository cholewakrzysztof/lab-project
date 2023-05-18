package pl.edu.pwr.student.IO.Input;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static pl.edu.pwr.student.Simulation.simWait;

class ClockTest {
    /*
     * Creating clock working 100ms On, 100ms Off
     */
    private Clock c;


    @BeforeEach
    protected void setUp() {
        c = new Clock(100, 100);
    }

    /*
     * Test clock state if no power
     */
    @Test
    public void testWithoutPower() {
        simWait(75);
        assertFalse(c.getState());
        simWait(75);
        assertFalse(c.getState());
    }

    /*
     * Test clock state with power
     */
    @Test
    public void testWithPower() {
        c.react();
        simWait(99);
        assertTrue(c.getState());
        simWait(99);
        assertFalse(c.getState());
        simWait(99);
        assertTrue(c.getState());
        simWait(99);
        assertFalse(c.getState());
        simWait(99);
        assertTrue(c.getState());
        simWait(99);
        assertFalse(c.getState());
    }
}


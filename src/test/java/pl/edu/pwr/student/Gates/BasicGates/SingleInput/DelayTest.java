package pl.edu.pwr.student.Gates.BasicGates.SingleInput;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pwr.student.IO.Input.Switch;

import static org.junit.jupiter.api.Assertions.*;
import static pl.edu.pwr.student.Simulation.simWait;

class DelayTest {
    protected Switch FirstInput = new Switch();
    protected Delay delay = new Delay(100);

    @BeforeEach
    protected void setUp() {
        if(FirstInput.getState())
            FirstInput.react();

        FirstInput.connection(delay);
        simWait(50);
    }

    /*
     * Check state before updating state
     */
    @Test
    public void testStateBeforeUpdate() {
        FirstInput.react();

        simWait(50);
        assertFalse(delay.getState());
    }

    /*
     * Check state after updating state
     */
    @Test
    public void testStateAfterUpdate() {
        FirstInput.react();
        simWait(75);
        assertFalse(delay.getState());
        simWait(75);
        assertTrue(delay.getState());
    }
}
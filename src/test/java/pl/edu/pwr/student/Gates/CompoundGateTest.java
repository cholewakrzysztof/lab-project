package pl.edu.pwr.student.Gates;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pwr.student.Gates.BasicGates.MultipleInput.NOR;
import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.IO.Output.LED;

import static org.junit.jupiter.api.Assertions.*;
import static pl.edu.pwr.student.Simulation.simWait;

class CompoundGateTest {
    private Switch R = new Switch();
    private Switch S = new Switch();
    private LED out = new LED("SR Latch", 1000);
    private NOR norUp = new NOR();
    private NOR norDown = new NOR();

    /**
     * Create gate SRLatch from examples
     */
    @BeforeEach
    protected void setUp() {
        if(R.getState())
            R.react();
        if(S.getState())
            S.react();

        R.connection(norUp);
        S.connection(norDown);
        norDown.connection(norUp);
        norUp.connection(norDown);
        norUp.connection(out);
        simWait(50);
    }

    /**
     * Test gate work input 0 and 0
     */
    @Test
    public void testWorkCase1() {
        simWait(100);
        assertTrue(out.getState());
    }
    /**
     * Test gate work input 0 and 1
     */
    @Test
    public void testWorkCase2() {
        R.react();
        simWait(50);
        assertFalse(out.getState());
    }
    /**
     * Test gate work input 1 and 0
     */
    @Test
    public void testWorkCase3() {
        S.react();
        simWait(50);
        assertTrue(out.getState());
    }
}
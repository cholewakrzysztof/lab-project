package pl.edu.pwr.student.Gates.BasicGates.SingleInput;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.IO.Output.LED;

import static org.junit.jupiter.api.Assertions.*;
import static pl.edu.pwr.student.Simulation.simWait;

class NOTTest {
    private Switch FirstInput = new Switch();
    private LED LED = new LED("led",100);
    private NOT gate = new NOT();

    @BeforeEach
    protected void setUp() {
        if(FirstInput.getState())
            FirstInput.react();

        FirstInput.connection(gate);
        gate.connection(LED);
        simWait(50);
    }

    /*
     *Test NOT gate input 0
     */
    @Test
    public void testTruthTableCase1() {
        assertTrue(gate.getState());
    }

    /*
     *Test NOT gate input 1
     */
    @Test
    public void testTruthTableCase2() {
        FirstInput.react();
        simWait(50);
        assertFalse(gate.getState());
    }
    /*
     * Test connection with two inputs
     */
    @Test
    public void testGateHasInputs() {
        assertTrue(gate.hasInputs());
    }
    /*
     * Test disconnect inputs
     */
    @Test
    public void testDisconnectInputs() {
        gate.disconnectInputs();
        simWait(50);
        assertFalse(gate.hasInputs());
    }
    /*
     * Test full disconnection
     */
    @Test
    public void testFullDisconnect() {
        gate.fullDisconnect();
        simWait(50);
        assertFalse(gate.hasInputs());
    }
    /*
     * Test disconnect outputs
     */
    @Test
    public void testDisconnectOutputs() {
        gate.disconnectOutputs();
        simWait(50);
        assertEquals(LED.getOutputs().size(),0);
    }
}
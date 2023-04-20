package pl.edu.pwr.student.Gates.BasicGates;

import org.junit.jupiter.api.Test;
import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.IO.Output.LED;

import static org.junit.jupiter.api.Assertions.*;

class NOTTest {
    private Switch FirstInput = new Switch();
    private pl.edu.pwr.student.IO.Output.LED LED = new LED("led",100);
    private NOT gate = new NOT();

    protected void setUp() {
        if(FirstInput.getState())
            FirstInput.toggle();

        FirstInput.connection(gate);
        gate.connection(LED);
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
        FirstInput.toggle();
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
        assertFalse(gate.hasInputs());
    }
    /*
     * Test full disconnection
     */
    @Test
    public void testFullDisconnect() {
        gate.fullDisconnect();
        assertFalse(gate.hasInputs());
    }
    /*
     * Test disconnect outputs
     */
    @Test
    public void testDisconnectOutputs() {
        gate.disconnectOutputs();
        assertEquals(LED.getOutputs().size(),0);
    }
}
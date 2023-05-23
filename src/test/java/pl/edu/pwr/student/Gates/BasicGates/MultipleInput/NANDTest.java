package pl.edu.pwr.student.Gates.BasicGates.MultipleInput;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.IO.Output.LED;

import static org.junit.jupiter.api.Assertions.*;
import static pl.edu.pwr.student.Simulation.simWait;

class NANDTest {
    private Switch FirstInput = new Switch();
    private Switch SecondInput = new Switch();
    private pl.edu.pwr.student.IO.Output.LED LED = new LED("led", 100);
    private NAND gate = new NAND();

    @BeforeEach
    protected void setUp() {
        if (FirstInput.getState())
            FirstInput.react();
        if (SecondInput.getState())
            SecondInput.react();

        FirstInput.connection(gate);
        SecondInput.connection(gate);
        gate.connection(LED);
        simWait(50);
    }

    /*
     *Test NAND gate input 0 and 0
     */
    @Test
    public void testTruthTableCase1() {
        assertTrue(gate.getState());
    }

    /*
     *Test NAND gate input 1 and 0
     */
    @Test
    public void testTruthTableCase2() {
        FirstInput.react();
        simWait(50);
        assertTrue(gate.getState());
    }

    /*
     * Test NAND gate input 0 and 1
     */
    @Test
    public void testTruthTableCase3() {
        SecondInput.react();
        simWait(50);
        assertTrue(gate.getState());
    }

    /*
     *Test NAND gate input 1 and 1
     */
    @Test
    public void testTruthTableCase4() {
        FirstInput.react();
        SecondInput.react();
        simWait(50);

        assertFalse(gate.getState());
    }

    /*
     * Test connection with two inputs
     */
    @Test
    public void testGateHasInputs() {
        assertFalse(gate.getInputs().isEmpty());
    }

    /*
     * Test disconnect inputs
     */
    @Test
    public void testDisconnectInputs() {
        gate.disconnectInputs();
        simWait(50);
        assertTrue(gate.getInputs().isEmpty());
    }

    /*
     * Test full disconnection
     */
    @Test
    public void testFullDisconnect() {
        gate.fullDisconnect();
        simWait(50);
        assertTrue(gate.getInputs().isEmpty());
    }

    /*
     * Test disconnect outputs
     */
    @Test
    public void testDisconnectOutputs() {
        gate.disconnectOutputs();
        simWait(50);
        assertEquals(LED.getOutputs().size(), 0);
    }
}
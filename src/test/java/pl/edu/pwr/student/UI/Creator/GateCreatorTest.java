package pl.edu.pwr.student.UI.Creator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pwr.student.Gates.BasicGates.MultipleInput.AND;
import pl.edu.pwr.student.Gates.BasicGates.MultipleInput.OR;
import pl.edu.pwr.student.Gates.CompoundGate;
import pl.edu.pwr.student.UI.UiAvailable;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class GateCreatorTest {
    /**
     * Setup GateCreator class before test
     */
    @BeforeEach
    void setCreator(){
        AbstractGateFactory.initGates();
    }

    /**
     * Check instance of created object
     */
    @Test
    void create() {
        UiAvailable gate = AbstractGateFactory.create("AND");

        assertTrue(gate instanceof AND);
        assertFalse(gate instanceof OR);
    }

    /**
     * Test list of initialized gates
     */
    @Test
    void initGates() {
        AbstractGateFactory.initGates();

        assertTrue(AbstractGateFactory.isRegistered("AND"));
        assertTrue(AbstractGateFactory.isRegistered("SWITCH"));
        assertFalse(AbstractGateFactory.isRegistered("not"));
        assertFalse(AbstractGateFactory.isRegistered(" NOT "));
        assertFalse(AbstractGateFactory.isRegistered("/NOT."));
    }

    /**
     * Test if CompoundGate with specific name is register
     */
    @Test
    void registerGate() {
        CompoundGate cg = new CompoundGate("cg","",new HashSet<>());
        AbstractGateFactory.registerGate("cg",cg);

        assertTrue(AbstractGateFactory.isRegistered("cg"));
    }

    /**
     * Test if gate with specific name is register
     */
    @Test
    void isRegistered() {
        assertTrue(AbstractGateFactory.isRegistered("SWITCH"));
        assertFalse(AbstractGateFactory.isRegistered("and"));
    }
}
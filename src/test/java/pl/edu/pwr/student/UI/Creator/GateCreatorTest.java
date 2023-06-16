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
    @BeforeEach
    void setCreator(){
        GateCreator.initGates();
    }
    @Test
    void create() {
        UiAvailable gate = GateCreator.create("AND");

        assertTrue(gate instanceof AND);
        assertFalse(gate instanceof OR);
    }

    @Test
    void initGates() {
        GateCreator.initGates();

        assertTrue(GateCreator.isRegistered("AND"));
        assertTrue(GateCreator.isRegistered("SWITCH"));
        assertFalse(GateCreator.isRegistered("not"));
        assertFalse(GateCreator.isRegistered(" NOT "));
        assertFalse(GateCreator.isRegistered("/NOT."));
    }

    @Test
    void registerGate() {
        CompoundGate cg = new CompoundGate("cg","",new HashSet<>());
        GateCreator.registerGate("cg",cg);

        assertTrue(GateCreator.isRegistered("cg"));
    }

    @Test
    void isRegistered() {
        assertTrue(GateCreator.isRegistered("SWITCH"));
        assertFalse(GateCreator.isRegistered("and"));
    }
}
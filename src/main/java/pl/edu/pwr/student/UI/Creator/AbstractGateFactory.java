package pl.edu.pwr.student.UI.Creator;

import pl.edu.pwr.student.Gates.BasicGates.MultipleInput.*;
import pl.edu.pwr.student.Gates.BasicGates.SingleInput.Delay;
import pl.edu.pwr.student.Gates.BasicGates.SingleInput.NOT;
import pl.edu.pwr.student.Gates.BasicGates.SingleInput.VirtualIO;
import pl.edu.pwr.student.Gates.CompoundGate;
import pl.edu.pwr.student.Gates.CreatableInstance;
import pl.edu.pwr.student.IO.Input.Clock;
import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.IO.Output.LED;
import pl.edu.pwr.student.IO.Output.Speaker;
import pl.edu.pwr.student.IO.UserUsable;
import pl.edu.pwr.student.UI.UiAvailable;

import java.util.HashMap;

/**
 * Creates gate, saves it to proper place and performs necessary actions
 */
public abstract class AbstractGateFactory {

    /**
     * Map of all possible gates
     */
    private static HashMap<String, CreatableInstance> possibleGates;

    /**
     * Constructor of {@link AbstractGateFactory}
     */
    private AbstractGateFactory() {}

    /**
     * Creates {@link UiAvailable}, saves it to proper place and performs necessary actions
     *
     * @param type - type of gate
     */
    public static UiAvailable create(String type) {
        UiAvailable ci = (UiAvailable) possibleGates.get(type).getNewInstance();

        if (ci instanceof UserUsable){
            ((UserUsable) ci).react();
        }

        return ci;
    }

    /**
     * Initializes map of {@link UiAvailable}
     */
    public static void initGates() {
        possibleGates = new HashMap<>();
        possibleGates.put("AND", new AND());
        possibleGates.put("NAND", new NAND());
        possibleGates.put("OR", new OR());
        possibleGates.put("NOR", new NOR());
        possibleGates.put("XOR", new XOR());
        possibleGates.put("XNOR", new XNOR());
        possibleGates.put("NOT", new NOT());
        possibleGates.put("SPEAKER", new Speaker());
        possibleGates.put("LED", new LED());
        possibleGates.put("SWITCH", new Switch());
        possibleGates.put("CLOCK", new Clock());
        possibleGates.put("DELAY", new Delay());
        possibleGates.put("VIRTUALIO", new VirtualIO());
        possibleGates.put("VirtualIO", new VirtualIO());
    }

    /**
     * Registers gate in map of {@link UiAvailable}
     *
     * @param name - name of gate
     * @param gate - gate to register
     */
    public static void registerGate(String name, CompoundGate gate) {
        AbstractGateFactory.possibleGates.put(name, gate);
    }

    /**
     * Checks if gate is registered
     *
     * @param name - name of gate
     * @return true if gate is registered, false otherwise
     */
    public static boolean isRegistered(String name){
        return AbstractGateFactory.possibleGates.containsKey(name);
    }
}

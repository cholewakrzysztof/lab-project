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
import pl.edu.pwr.student.UI.UiAvailable;

import java.util.HashMap;

/**
 * Creates gate, saves it to proper place and performs necessary actions
 */
public class GateCreator {

    private static HashMap<String, CreatableInstance> possibleGates;

    /**
     * Constructor of GateCreator
     */
    public GateCreator() {}

    /**
     * Creates gate, saves it to proper place and performs necessary actions
     *
     * @param type - type of gate
     */
    public static UiAvailable create(String type) {
        UiAvailable ci = (UiAvailable) possibleGates.get(type).getNewInstance();

        if (ci instanceof Clock){
            ((Clock) ci).toggle();
        } else if (ci instanceof Switch){
            ((Switch) ci).toggle();
        } else if (ci instanceof LED){
            ((LED) ci).toggle();
        }

        return ci;
    }

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
    }

    public static void registerGate(String name, CompoundGate gate) {
        possibleGates.put(name, gate);
    }
}

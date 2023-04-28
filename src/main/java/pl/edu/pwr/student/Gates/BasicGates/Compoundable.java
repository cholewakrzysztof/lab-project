package pl.edu.pwr.student.Gates.BasicGates;

import pl.edu.pwr.student.IO.Output.SignalReceiver;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;

public interface Compoundable {
    HashSet<SignalReceiver> getOutputs();
    default Compoundable getNewInstance() {
        try {
            return this.getClass().getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException ignored) {}
        return null;
    }
    int connection(SignalReceiver receiver);
    void fullDisconnect();
    default boolean isIO() {
        return false;
    }
}

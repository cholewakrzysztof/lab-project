package src.Gates;

import src.IO.Output.SignalReceiver;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;

public interface Compoundable {
    default HashSet<SignalReceiver> getOutputs() {
        return null;
    }
    boolean hasInputs();
    default Compoundable getNewInstance() {
        try {
            return this.getClass().getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException ignored) {}
        return null;
    }
    int connection(SignalReceiver receiver);
    default boolean isIO() {
        return false;
    }
}

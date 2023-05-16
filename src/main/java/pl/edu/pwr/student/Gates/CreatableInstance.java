package pl.edu.pwr.student.Gates;

import java.lang.reflect.InvocationTargetException;

public interface CreatableInstance {


    /**
     * Returns a new instance of the gate.
     *
     * @return a new instance of the gate.
     */
    default CreatableInstance getNewInstance() {
        try {
            return this.getClass().getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException ignored) {}
        return null;
    }
}

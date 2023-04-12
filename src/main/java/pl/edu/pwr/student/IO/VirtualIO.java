package pl.edu.pwr.student.IO;

import pl.edu.pwr.student.Gates.Compoundable;

public class VirtualIO extends BasicPassThrough {
    public final String name;
    public boolean isIO() {
        return true;
    }
    protected boolean checkState(boolean inputState) {
        return inputState;
    }
    public VirtualIO(String name) {
        this.name = name;
    }
    public Compoundable getNewInstance() {
        return new VirtualIO(name);
    }
}

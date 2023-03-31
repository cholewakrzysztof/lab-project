package pl.edu.pwr.student.Gates.BasicGates;

import pl.edu.pwr.student.IO.BasicPassThrough;

public class NOT extends BasicPassThrough {
    protected boolean checkState(boolean inputState) {
        return !inputState;
    }

    public NOT() {}
}

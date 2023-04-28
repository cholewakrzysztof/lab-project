package pl.edu.pwr.student.Gates.BasicGates.SingleInput;

public class NOT extends BasicPassThrough {
    protected boolean checkState(boolean inputState) {
        return !inputState;
    }

    public NOT() {}
}

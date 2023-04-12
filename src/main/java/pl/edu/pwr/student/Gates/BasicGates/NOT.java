package pl.edu.pwr.student.Gates.BasicGates;

import pl.edu.pwr.student.IO.BasicPassThrough;
import pl.edu.pwr.student.UI.UiAvailable;

public class NOT extends BasicPassThrough implements UiAvailable {
    protected boolean checkState(boolean inputState) {
        return !inputState;
    }

    public NOT() {}
}

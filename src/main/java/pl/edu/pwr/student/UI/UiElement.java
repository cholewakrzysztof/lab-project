package pl.edu.pwr.student.UI;

import pl.edu.pwr.student.Gates.BasicGate;
import pl.edu.pwr.student.Utility.ShapeLoader;
import processing.core.PApplet;
import processing.core.PVector;

public class UiElement extends PApplet {
    public final PVector position;
    public final String elName;
    public final PApplet sketch;
    public final BasicGate gate;

    public UiElement(String type, PApplet s, PVector v, BasicGate gate) {
        position = v.copy();
        elName = type;
        sketch = s;
        this.gate = gate;
    }

    public void run() {
        sketch.fill(0);
        sketch.shape(ShapeLoader.getShape(elName), position.x, position.y);
    }
}

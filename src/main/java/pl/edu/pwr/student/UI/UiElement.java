package pl.edu.pwr.student.UI;

import pl.edu.pwr.student.Utility.ShapeLoader;
import processing.core.PApplet;
import processing.core.PVector;

public abstract class UiElement extends PApplet {
    public final PVector position;
    public final String elName;
    public final PApplet sketch;

    public UiElement(String type, PApplet s, PVector v) {
        position = v.copy();
        elName = type;
        sketch = s;
    }

    public void run() {
        sketch.fill(0);
        sketch.shape(ShapeLoader.getShape(elName), position.x, position.y);
    }
}

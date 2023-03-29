package pl.edu.pwr.student.UI;

import pl.edu.pwr.student.Gates.Compoundable;
import pl.edu.pwr.student.Utility.ShapeLoader;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Class representing every element on canvas
 */
public class UiElement extends PApplet {

    /**
     * Position of element
     */
    public final PVector position;

    /**
     * Name of element
     */
    public final String elName;

    /**
     * Processing sketch
     */
    public final PApplet sketch;

    /**
     * Gate represented by element
     */
    public final Compoundable gate;

    /**
     * Constructor
     * @param type name of element
     * @param s Processing sketch
     * @param v position of element
     * @param gate gate represented by element
     */
    public UiElement(String type, PApplet s, PVector v, Compoundable gate) {
        position = v.copy();
        elName = type;
        sketch = s;
        this.gate = gate;
    }

    /**
     * Draws element
     */
    public void run() {
        sketch.fill(0);
        sketch.shape(ShapeLoader.getShape(elName), position.x, position.y);
    }

    /**
     * Returns true if mouse over element
     */
    public boolean over(PVector v)  {
        return position.x <= v.x &&
                position.x + 512*ShapeLoader.scale >=v.x &&
                position.y <= v.y &&
                position.y + 512*ShapeLoader.scale >=v.y;
    }
}
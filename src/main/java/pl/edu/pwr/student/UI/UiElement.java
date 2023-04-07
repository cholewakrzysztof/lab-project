package pl.edu.pwr.student.UI;

import pl.edu.pwr.student.IO.Input.SignalSender;
import pl.edu.pwr.student.IO.Output.LED;
import pl.edu.pwr.student.IO.Output.SignalReceiver;
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
    public PVector position;

    /**
     * Name of element
     */
    public final String elName;

    /**
     * Processing sketch
     */
    public final Canvas sketch;

    /**
     * Gate represented by element
     */
    public final UiAvailable uiElem;

    /**
     * Constructor
     * @param type name of element
     * @param s Processing sketch
     * @param v position of element
     * @param uiElem gate represented by element
     */
    public UiElement(String type, Canvas s, PVector v, UiAvailable uiElem) {
        position = v.copy();
        elName = type;
        sketch = s;
        this.uiElem = uiElem;
    }

    /**
     * Draws element
     */
    public void run() {
        sketch.fill(0);
        sketch.shape(ShapeLoader.getShape(elName), position.x, position.y);

        for (SignalReceiver s : uiElem.getOutputs()) {
            if (uiElem.getState()){
                sketch.stroke(0, 255, 0);
                // Jak zacznie działać getstate to będzie printować
                if (elName == "LED"){
                    System.out.println(1);
                    sketch.circle(position.x, position.y,100);
                }
                if (elName == "SPEAKER"){
                    System.out.println(2);
                    sketch.circle(position.x, position.y,100);
                }
            } else {
                sketch.stroke(0, 0, 0);
            }

            for (UiElement u : sketch.elements){
                if (u.uiElem.equals(s)) {
                    sketch.line(position.x + 512*ShapeLoader.scale, position.y + 256*ShapeLoader.scale, u.position.x, u.position.y + 256*ShapeLoader.scale);
                }
            }
        }
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
package pl.edu.pwr.student.UI.Buttons;

import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.UiElement;
import pl.edu.pwr.student.Utility.ShapeLoader;
import processing.core.PShape;
import processing.core.PVector;

public abstract class Button extends UiElement {


    /**
     * Shape of button
     */
    protected PShape shape;

    /**
     * Constructor
     *
     * @param s      Processing sketch
     */
    public Button(Canvas s, String name, PVector position) {
        super(name, s, position, null);
        shape = ShapeLoader.getShape(name);
    }

    public abstract void run();
    public abstract void click();
}

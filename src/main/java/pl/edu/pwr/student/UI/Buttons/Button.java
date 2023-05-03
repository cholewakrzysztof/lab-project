package pl.edu.pwr.student.UI.Buttons;

import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.UiElement;
import pl.edu.pwr.student.Utility.ShapeLoader;
import processing.core.PShape;
import processing.core.PVector;

/**
 * This is an abstract class that defines the basic structure of a button
 * on the canvas. It extends the UiElement class and adds a shape
 * field to store the button's shape. The constructor takes the processing
 * sketch, name and position of the button and sets the shape using
 * the ShapeLoader utility class.
 * <p>
 * The class also defines two abstract methods: run() and click().
 * These methods are meant to be implemented by any concrete button
 * subclass and define what happens when the button is clicked.
 * </p>
 */
public abstract class Button extends UiElement {


    /**
     * Shape of button
     */
    protected PShape shape;

    /**
     * Constructor
     *
     * @param s         Processing sketch
     * @param name      Name of the button
     * @param position  Position of the button
     */
    public Button(Canvas s, String name, PVector position) {
        super(name, s, position, null);
        shape = ShapeLoader.getShape(name);
    }

    /**
     * Method to be executed when the button is clicked
     */
    public abstract void run();

    /**
     * Method to be executed when the button is clicked
     */
    public abstract void click();
}

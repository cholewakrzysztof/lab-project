package pl.edu.pwr.student.UI.Blocks;

import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.IO.Output.LED;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.Creator.AbstractGateFactory;
import pl.edu.pwr.student.UI.UiAvailable;
import pl.edu.pwr.student.Utility.FileManagement.JSONAvailable;
import pl.edu.pwr.student.Utility.ShapeLoader;
import processing.core.PVector;

import java.awt.*;

import static processing.core.PApplet.cos;
import static processing.core.PApplet.sin;
import static processing.core.PConstants.PI;

public abstract class Drawable {
    /**
     * The position of this element.
     */
    public PVector position;

    /**
     * The name of this element.
     */
    public final String elName;

    /**
     * The {@link Canvas} associated with this element.
     */
    public final Canvas sketch;

    /**
     * The gate represented by this element.
     */
    public final UiAvailable uiElem;

    /**
     * The color associated with this element.
     */
    public Color color = new Color(0, 255, 0);

    /**
     * The color associated with this element.
     */
    public float rotation = 0;

    /**
     * Creates a new UI element object with the specified parameters.
     *
     * @param type The name of the UI element.
     * @param s The Processing sketch used to render the UI element.
     * @param v The position of the UI element on the canvas, specified as a PVector object.
     * @param uiElem The gate represented by the UI element, specified as a UiAvailable object.
     *
     * <p>
     * The {@code UiElement} constructor creates a new UI element object with the specified
     * properties. The {@code type} parameter specifies the name of the UI element, such as
     * "button" or "checkbox". The {@code s} parameter specifies the Processing sketch used to
     * render the UI element. The {@code v} parameter specifies the position of the UI element
     * on the canvas, as a PVector object with x and y coordinates. The {@code uiElem} parameter
     * specifies the gate represented by the UI element, as a UiAvailable object.
     * </p>
     */
    public Drawable(String type, Canvas s, PVector v, UiAvailable uiElem, float rotation) {
        position = v.copy();
        elName = type;
        sketch = s;
        this.uiElem = uiElem;
        this.rotation = rotation;
    }

    /**
     * Creates a new UI element from JSONAvaible object
     *
     * @param jsonAvailable Object created from file
     * @param s The Processing sketch used to render the UI element.
     *
     * <p>
     * The {@code UiElement} constructor creates a new UI element object with the specified
     * properties. The {@code jsonAvailable} is a middle version of object between JSON string and UIElement.
     * The {@code s} parameter specifies the Processing sketch used to render the UI element. If JSONAvaible has bad name throw exception.
     * </p>
     */
    public Drawable(JSONAvailable jsonAvailable, Canvas s) {
        position = jsonAvailable.getPosition();
        elName = jsonAvailable.getElName();
        sketch = s;
        uiElem = AbstractGateFactory.create(jsonAvailable.getElName());
        sketch.addElement(new UiElement(jsonAvailable.getElName(), sketch, jsonAvailable.getPosition(), uiElem));
        rotation = jsonAvailable.getRotation();
    }

    /**
     * Renders the UI element on the canvas and displays any connected signals.
     *
     * <p>
     * The {@code run} method is responsible for rendering the UI element on the canvas
     * and displaying any connected signals. First, it checks whether the mouse is currently
     * hovering over the element and, if so, fills the element with a dark color to indicate
     * that it is active. Next, it renders the shape of the element using the {@link ShapeLoader}
     * class. If the element is a {@link Switch}, it displays either the "true" or "false" state
     * of the switch using a specific shape for each state. If the element is an {@link LED}
     * and its state is "on", it displays a circle in the specified color. Finally, it iterates
     * through each of the element's output signals and draws a line to each connected element
     * to indicate the signal flow.
     * </p>
     */
    public abstract void run();



    /**
     * Determines whether the mouse is currently over the element.
     *
     * @param v the mouse position as a PVector
     * @return true if the mouse is over the element, false otherwise
     */
    public boolean over(PVector v)  {
        return (Math.abs((v.x/ShapeLoader.scale-position.x+sketch.getOffset().x-ShapeLoader.size/2f)*cos(rotation*PI)+(v.y/ShapeLoader.scale-position.y+sketch.getOffset().y-ShapeLoader.size/2f)*sin(rotation*PI))-ShapeLoader.size/2f) < 0 &&
                (Math.abs((v.y/ShapeLoader.scale-position.y+sketch.getOffset().y-ShapeLoader.size/2f)*cos(rotation*PI)-(v.x/ShapeLoader.scale-position.x+sketch.getOffset().x-ShapeLoader.size/2f)*sin(rotation*PI))-ShapeLoader.size/2f) < 0;
    }

    /**
     * changes the rotation of the element by the specified amount.
     *
     * @param direction >0 if rotate right, <0 if rotate left
     */
    public void rotate(int direction) {
        rotation = (rotation - direction * 0.01f) % 2;
    }

    /**
     * Updates the position of the element.
     */
    public abstract void updatePosition(PVector pVector);

    /**
     * Returns the object associated with this element.
     *
     * @return the {@link UiAvailable} object associated with this element
     */
    public UiAvailable getGate(){
        return uiElem;
    }

    /**
     * Returns the {@link UiAvailable} object associated with input of this element
     *
     * @return the {@link UiAvailable} object associated with input of this element
     */
    public abstract UiAvailable getInput();

    /**
     * Returns the {@link UiAvailable} object associated with output of this element
     *
     * @return the {@link UiAvailable} object associated with output of this element
     */
    public abstract UiAvailable getOutput();

}

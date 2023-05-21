package pl.edu.pwr.student.UI;

import pl.edu.pwr.student.IO.Input.SignalSender;
import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.IO.Output.LED;
import pl.edu.pwr.student.IO.Output.SignalReceiver;
import pl.edu.pwr.student.UI.Creator.GateCreator;
import pl.edu.pwr.student.Utility.FileManagement.JSONAvailable;
import pl.edu.pwr.student.Utility.ShapeLoader;
import processing.core.PVector;

import java.awt.*;
import java.util.HashSet;

import static processing.core.PConstants.PI;

/**
 * This is a class definition for a UiElement class, which represents every element on a canvas.
 */
public class UiElement {

    /**
     * The position of this element.
     */
    public PVector position;

    /**
     * The name of this element.
     */
    public final String elName;

    /**
     * The Processing sketch associated with this element.
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
    public UiElement(String type, Canvas s, PVector v, UiAvailable uiElem) {
        position = v.copy();
        elName = type;
        sketch = s;
        this.uiElem = uiElem;
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
    public UiElement(JSONAvailable jsonAvailable, Canvas s) throws Exception {
        position = jsonAvailable.position;
        elName = jsonAvailable.elName;
        sketch = s;
        uiElem = GateCreator.create(elName, position, s);
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
    public void run() {
        // Code for drawing the element shape and mouse hover effects

        if (over(new PVector(sketch.mouseX, sketch.mouseY))){
            sketch.fill(0, 30);
            sketch.square((position.x-sketch.getOffset().x)*ShapeLoader.scale,(position.y-sketch.getOffset().y)*ShapeLoader.scale,ShapeLoader.size*ShapeLoader.scale);
        }


        // Code for displaying the state of Switch and LED elements

        sketch.fill(0);
        if (uiElem instanceof Switch) {
            if (uiElem.getState()){
                sketch.shape(ShapeLoader.getShape("SWITCH-TRUE", PI*rotation), (position.x-sketch.getOffset().x)*ShapeLoader.scale, (position.y-sketch.getOffset().y)*ShapeLoader.scale);
            } else {
                sketch.shape(ShapeLoader.getShape("SWITCH-FALSE", PI*rotation), (position.x-sketch.getOffset().x)*ShapeLoader.scale, (position.y-sketch.getOffset().y)*ShapeLoader.scale);
            }
        } else {

            sketch.shape(ShapeLoader.getShape(elName, PI*rotation),
                    (position.x-sketch.getOffset().x)*ShapeLoader.scale,
                    (position.y-sketch.getOffset().y)*ShapeLoader.scale
            );
        }

        if (uiElem.getState()){
            if (uiElem instanceof LED) {
                if (color == null) color = new Color(0, 255, 0);
                sketch.stroke(color.getRGB());
                sketch.fill(color.getRGB());
                sketch.circle(
                        (position.x-sketch.getOffset().x)*ShapeLoader.scale + ShapeLoader.size *(ShapeLoader.scale)/2,
                        (position.y-sketch.getOffset().y)*ShapeLoader.scale + (ShapeLoader.size-10)*ShapeLoader.scale/2,
                        ShapeLoader.size*ShapeLoader.scale/2
                );
            }
        }

        // Code for drawing signal lines to connected elements

        for (SignalReceiver s : uiElem.getOutputs()) {
            if (uiElem.getState()){
                sketch.stroke(0, 255, 0);
            } else {
                sketch.stroke(0, 0, 0);
            }

            for (UiElement u : sketch.getElements()){
                if (u.uiElem.equals(s)) {
                    HashSet<SignalSender> inputs = ((UiAvailable) s).getInputs();
                    int i = 1;
                    int pos = 1;
                    for (SignalSender inp : inputs){
                        if(inp.equals(uiElem)) {
                            pos = i;
                        }
                        i++;
                    }

                    float startx = (position.x-sketch.getOffset().x)*ShapeLoader.scale + ShapeLoader.size*ShapeLoader.scale;
                    float starty = (position.y-sketch.getOffset().y)*ShapeLoader.scale + ShapeLoader.size/2f*ShapeLoader.scale;
                    float endx = (u.position.x-sketch.getOffset().x)*ShapeLoader.scale;
                    float endy = (u.position.y-sketch.getOffset().y)*ShapeLoader.scale + ShapeLoader.size*ShapeLoader.scale*pos/i;
                    if (startx <= endx){
                        float midx = (startx+endx)/2 + ShapeLoader.size*ShapeLoader.scale*pos/i;
                        sketch.line(startx, starty, midx, starty);
                        sketch.line(midx, starty, midx, endy);
                        sketch.line(midx, endy, endx, endy);
                    } else {
                        float padding;
                        float midy;
                        if (starty <= (u.position.y-sketch.getOffset().y) + ShapeLoader.size*1.5f*ShapeLoader.scale && starty >= (u.position.y-sketch.getOffset().y) - ShapeLoader.size/2f*ShapeLoader.scale) {
                            padding = ShapeLoader.size*ShapeLoader.scale + ShapeLoader.size*ShapeLoader.scale*pos/i;
                            midy = (starty+endy)/2+padding;
                        } else {
                            midy = (starty+endy)/2;
                            padding = ShapeLoader.size*ShapeLoader.scale + ShapeLoader.size*ShapeLoader.scale*pos/i;
                        }

                        sketch.line(startx, starty, startx+padding, starty);
                        sketch.line(startx+padding, starty, startx+padding, midy);
                        sketch.line(startx+padding, midy, endx-padding, midy);
                        sketch.line(endx-padding, midy, endx-padding, endy);
                        sketch.line(endx-padding, endy, endx, endy);
                    }
                }
            }
        }
    }

    /**
     * Determines whether the mouse is currently over the element.
     *
     * @param v the mouse position as a PVector
     * @return true if the mouse is over the element, false otherwise
     */
    public boolean over(PVector v)  {
        return (position.x-sketch.getOffset().x)*ShapeLoader.scale <= v.x &&
                (position.x-sketch.getOffset().x + ShapeLoader.size)*ShapeLoader.scale >= v.x &&
                (position.y-sketch.getOffset().y)*ShapeLoader.scale <= v.y &&
                (position.y-sketch.getOffset().y + ShapeLoader.size)*ShapeLoader.scale >= v.y;
    }

    public void decrementRotation() {
        rotation = (rotation - 0.01f) % 2;
    }

    public void incrementRotation() {
        rotation = (rotation + 0.01f) % 2;
    }
}
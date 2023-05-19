package pl.edu.pwr.student.UI.Blocks;

import pl.edu.pwr.student.Gates.BasicGates.SingleInput.VirtualIO;
import pl.edu.pwr.student.IO.Input.SignalSender;
import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.IO.Output.LED;
import pl.edu.pwr.student.IO.Output.SignalReceiver;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.UiAvailable;
import pl.edu.pwr.student.Utility.FileManagement.JSONAvailable;
import pl.edu.pwr.student.Utility.ShapeLoader;
import processing.core.PVector;

import java.awt.*;
import java.util.HashSet;

/**
 * This is a class definition for a UiElement class, which represents every element on a canvas.
 */
public class UiElement extends Drawable {
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
        super(type, s, v, uiElem);
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
        super(jsonAvailable, s);
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
    @Override
    public void run() {
        sketch.textSize(32);
        // Code for drawing the element shape and mouse hover effects

        if (over(new PVector(sketch.mouseX, sketch.mouseY))){
            sketch.fill(0, 30);
            sketch.square((position.x-sketch.getOffset().x)*ShapeLoader.scale,(position.y-sketch.getOffset().y)*ShapeLoader.scale,ShapeLoader.size*ShapeLoader.scale);
        }


        // Code for displaying the state of Switch and LED elements

        sketch.fill(0);
        if (uiElem instanceof VirtualIO){
            sketch.fill(0);
            sketch.textAlign(sketch.CENTER, sketch.CENTER);
            String temp = ((VirtualIO)uiElem).name;
            float width = sketch.textWidth(temp);
            if (width > 50){
                sketch.textSize(32*ShapeLoader.scale*ShapeLoader.size/width);
            } else {
                sketch.textSize(32*ShapeLoader.scale);
            }
            sketch.text(
                    temp,
                    (position.x-sketch.getOffset().x+ShapeLoader.size/2f)*ShapeLoader.scale,
                    (position.y-sketch.getOffset().y+ShapeLoader.size/2f)*ShapeLoader.scale
            );
        } else if (uiElem instanceof Switch) {
            if (uiElem.getState()){
                sketch.shape(
                        ShapeLoader.getShape("SWITCH-TRUE"),
                        (position.x-sketch.getOffset().x)*ShapeLoader.scale,
                        (position.y-sketch.getOffset().y)*ShapeLoader.scale
                );
            } else {
                sketch.shape(
                        ShapeLoader.getShape("SWITCH-FALSE"),
                        (position.x-sketch.getOffset().x)*ShapeLoader.scale,
                        (position.y-sketch.getOffset().y)*ShapeLoader.scale
                );
            }
        } else {
            sketch.shape(ShapeLoader.getShape(elName),
                    (position.x-sketch.getOffset().x)*ShapeLoader.scale,
                    (position.y-sketch.getOffset().y)*ShapeLoader.scale
            );
        }

        if (uiElem instanceof LED) {
            if (uiElem.getState()){
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

            for (Drawable u : sketch.getElements()){
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

    @Override
    public void updatePosition(PVector pVector) {
        position = pVector;
    }

    @Override
    public UiAvailable getInput() {
        return getGate();
    }

    @Override
    public UiAvailable getOutput() {
        return getGate();
    }
}
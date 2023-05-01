package pl.edu.pwr.student.UI;

import pl.edu.pwr.student.IO.Input.SignalSender;
import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.IO.Output.LED;
import pl.edu.pwr.student.IO.Output.SignalReceiver;
import pl.edu.pwr.student.Utility.ShapeLoader;
import processing.core.PVector;

import java.awt.*;
import java.util.HashSet;

/**
 * Class representing every element on canvas
 */
public class UiElement {

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

    public Color color = new Color(0, 255, 0);


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
        if (over(new PVector(sketch.mouseX, sketch.mouseY))){
            sketch.fill(0, 30);
            sketch.square(position.x,position.y,512*ShapeLoader.scale);
        }

        sketch.fill(0);
        if (uiElem instanceof Switch) {
            if (uiElem.getState()){
                sketch.shape(ShapeLoader.getShape("SWITCH-TRUE"), position.x, position.y);
            } else {
                sketch.shape(ShapeLoader.getShape("SWITCH-FALSE"), position.x, position.y);
            }
        } else {
            sketch.shape(ShapeLoader.getShape(elName), position.x, position.y);
        }

        if (uiElem.getState()){
            if (uiElem instanceof LED) {
                sketch.fill(color.getRGB());
                sketch.circle(position.x + 512*(ShapeLoader.scale)/2, position.y + 400*ShapeLoader.scale/2, 512*ShapeLoader.scale/2);
            }
        }

        for (SignalReceiver s : uiElem.getOutputs()) {
            if (uiElem.getState()){
                sketch.stroke(0, 255, 0);
            } else {
                sketch.stroke(0, 0, 0);
            }

            for (UiElement u : sketch.elements){
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

                    float startx = position.x + 512*ShapeLoader.scale;
                    float starty = position.y + 256*ShapeLoader.scale;
                    float endx = u.position.x;
                    float endy = u.position.y + 512*ShapeLoader.scale*pos/i;
                    if (startx <= endx){
                        float midx = (startx+endx)/2 + 512*ShapeLoader.scale*pos/i;
                        sketch.line(startx, starty, midx, starty);
                        sketch.line(midx, starty, midx, endy);
                        sketch.line(midx, endy, endx, endy);
                    } else {
                        float padding;
                        float midy;
                        if (starty <= u.position.y + 768*ShapeLoader.scale && starty >= u.position.y - 256*ShapeLoader.scale) {
                            padding = 512*ShapeLoader.scale + 512*ShapeLoader.scale*pos/i;
                            midy = (starty+endy)/2+padding;
                        } else {
                            midy = (starty+endy)/2;
                            padding = 512*ShapeLoader.scale + 512*ShapeLoader.scale*pos/i;
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
     * Returns true if mouse over element
     */
    public boolean over(PVector v)  {
        return position.x <= v.x &&
                position.x + 512*ShapeLoader.scale >=v.x &&
                position.y <= v.y &&
                position.y + 512*ShapeLoader.scale >=v.y;
    }
}
package pl.edu.pwr.student.UI.Buttons;

import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.UiElement;
import pl.edu.pwr.student.Utility.ShapeLoader;
import processing.core.PShape;
import processing.core.PVector;

/**
 * Class representing button for creating new elements
 */
public class ConnectButton extends UiElement {

    /**
     * Shape of button
     */
    PShape shape;

    /**
     * Constructor
     * @param s Processing sketch
     */
    public ConnectButton(Canvas s) {
        super("CONNECT", s, new PVector(130,10), null);
        shape = ShapeLoader.getShape("CONNECT");
    }

    /**
     * Draws element
     */
    @Override
    public void run() {
        sketch.stroke(0);
        if (over(new PVector(sketch.mouseX, sketch.mouseY))){
            if (sketch.state == 2 || sketch.state == 4){
                sketch.fill(0,255,0,30);
                sketch.square(position.x,position.y,48);
            } else {
                sketch.fill(0, 30);
                sketch.square(position.x,position.y,48);
            }
        } else if(sketch.state == 2 || sketch.state == 4){
            sketch.fill(0,255,0,20);
            sketch.square(position.x,position.y,48);
        } else {
            sketch.fill(0, 20);
            sketch.square(position.x,position.y,48);
        }
        sketch.shape(shape, position.x, position.y);
    }
}

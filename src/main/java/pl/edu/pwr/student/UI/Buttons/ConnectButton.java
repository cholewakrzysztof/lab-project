package pl.edu.pwr.student.UI.Buttons;

import pl.edu.pwr.student.UI.Canvas;
import processing.core.PVector;

/**
 * Represents a button for creating new elements on the canvas.
 * Extends the abstract Button class.
 */
public class ConnectButton extends Button {


    /**
     * Constructs a new ConnectButton object.
     *
     * @param s The Processing sketch that this button is a part of.
     */
    public ConnectButton(Canvas s) {
        super(s,"CONNECT", new PVector(130,10));
    }

    /**
     * Draws the ConnectButton element on the canvas.
     * Overrides the run method of the UiElement class.
     */
    @Override
    public void run() {
        sketch.stroke(0);
        if (over(new PVector(sketch.mouseX, sketch.mouseY))){
            if (sketch.getState() == 2 || sketch.getState() == 4){
                sketch.fill(0,255,0,30);
                sketch.square(position.x,position.y,48);
            } else {
                sketch.fill(0, 30);
                sketch.square(position.x,position.y,48);
            }
        } else if(sketch.getState() == 2 || sketch.getState() == 4){
            sketch.fill(0,255,0,20);
            sketch.square(position.x,position.y,48);
        } else {
            sketch.fill(0, 20);
            sketch.square(position.x,position.y,48);
        }
        sketch.shape(shape, position.x, position.y);
    }

    /**
     * Handles the click event of the ConnectButton element.
     * Overrides the click method of the Button class.
     */
    @Override
    public void click() {
        sketch.setState(2);
        sketch.hideForm();
    }
}

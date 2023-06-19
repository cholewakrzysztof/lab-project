package pl.edu.pwr.student.UI.Buttons;

import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.CanvasState;
import processing.core.PVector;

/**
 * Represents a button for creating new elements on the {@link Canvas}.
 * Extends the abstract {@link Button} class.
 */
public class ConnectButton extends Button {


    /**
     * Constructs a new {@link ConnectButton} object.
     *
     * @param s The {@link Canvas} that this button is a part of.
     */
    public ConnectButton(Canvas s) {
        super(s,"CONNECT", new PVector(130,10));
    }

    /**
     * Draws the {@link ConnectButton} element on the {@link Canvas}.
     * Overrides the run method of the {@link pl.edu.pwr.student.UI.Blocks.UiElement} class.
     */
    @Override
    public void run() {
        sketch.stroke(0);
        if (over(new PVector(sketch.mouseX, sketch.mouseY))){
            if (CanvasState.getState() == CanvasState.State.OUTPUT || CanvasState.getState() == CanvasState.State.CONNECTING){
                sketch.fill(0,255,0,30);
                sketch.square(position.x,position.y,48);
            } else {
                sketch.fill(0, 30);
                sketch.square(position.x,position.y,48);
            }
        } else if(CanvasState.getState() == CanvasState.State.OUTPUT || CanvasState.getState() == CanvasState.State.CONNECTING){
            sketch.fill(0,255,0,20);
            sketch.square(position.x,position.y,48);
        } else {
            sketch.fill(0, 20);
            sketch.square(position.x,position.y,48);
        }
        sketch.shape(shape, position.x, position.y);
    }

    /**
     * Handles the click event of the {@link ConnectButton} element.
     * Overrides the click method of the {@link Button} class.
     */
    @Override
    public void click() {
        CanvasState.setState(CanvasState.State.OUTPUT);
        sketch.hideForm();
    }
}

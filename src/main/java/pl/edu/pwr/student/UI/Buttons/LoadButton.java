package pl.edu.pwr.student.UI.Buttons;

import pl.edu.pwr.student.UI.Canvas;
import processing.core.PVector;

/**
 * Class representing button for creating new elements
 */
public class LoadButton extends Button {
    /**
     * Constructor
     * @param s Processing sketch
     */
    public LoadButton(Canvas s) {
        super(s, "LOAD", new PVector(310,10));
    }

    /**
     * Draws element
     */
    @Override
    public void run() {
        sketch.stroke(0);
        if (over(new PVector(sketch.mouseX, sketch.mouseY))){
            sketch.fill(0, 30);
            sketch.square(position.x,position.y,48);
        } else {
            sketch.fill(0, 20);
            sketch.square(position.x,position.y,48);
        }
        sketch.shape(shape, position.x, position.y);
    }

    @Override
    public void click() {
        //TODO: load
    }
}

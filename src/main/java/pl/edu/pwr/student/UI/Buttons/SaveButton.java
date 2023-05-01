package pl.edu.pwr.student.UI.Buttons;

import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.UiElement;
import pl.edu.pwr.student.Utility.ShapeLoader;
import processing.core.PShape;
import processing.core.PVector;

/**
 * Class representing button for creating new elements
 */
public class SaveButton extends Button {
    /**
     * Constructor
     * @param s Processing sketch
     */
    public SaveButton(Canvas s) {
        super(s, "SAVE", new PVector(250,10));
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
        //TODO: save
    }
}

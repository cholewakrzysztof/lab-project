package pl.edu.pwr.student.UI;

import processing.core.PShape;
import processing.core.PVector;

/**
 * Class representing button for creating new elements
 */
public class EditButton extends UiElement {

    PShape shape;

    /**
     * Constructor
     * @param s Processing sketch
     */
    public EditButton(Canvas s) {
        super("EDIT", s, new PVector(60,10), null);
        shape = sketch.loadShape("src/main/resources/buttons/EDIT.svg");
        shape.scale(3);
    }

    /**
     *
     * Draws element
     */
    @Override
    public void run() {
        sketch.fill(0);
        sketch.shape(shape, position.x, position.y);
    }
}

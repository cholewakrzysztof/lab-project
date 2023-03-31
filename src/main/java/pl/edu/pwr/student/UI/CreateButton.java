package pl.edu.pwr.student.UI;

import processing.core.PShape;
import processing.core.PVector;

/**
 * Class representing button for creating new elements
 */
public class CreateButton extends UiElement {

    PShape shape;

    /**
     * Constructor
     * @param s Processing sketch
     */
    public CreateButton(Canvas s) {
        super("CREATE", s, new PVector(10,10), null);
        shape = sketch.loadShape("src/main/resources/buttons/CREATE.svg");
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

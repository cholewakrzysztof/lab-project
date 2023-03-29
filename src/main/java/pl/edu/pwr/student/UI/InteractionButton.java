package pl.edu.pwr.student.UI;

import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

/**
 * Class representing button for creating new elements
 */
public class InteractionButton extends UiElement {

    PShape shape;

    /**
     * Constructor
     * @param s Processing sketch
     */
    public InteractionButton(PApplet s) {
        super("INTERACT", s, new PVector(120,10), null);
        shape = sketch.loadShape("src/main/resources/buttons/INTERACT.svg");
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

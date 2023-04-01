package pl.edu.pwr.student.UI.Buttons;

import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.UiElement;
import processing.core.PShape;
import processing.core.PVector;

/**
 * Class representing button for creating new elements
 */
public class ConnectButton extends UiElement {

    PShape shape;

    /**
     * Constructor
     * @param s Processing sketch
     */
    public ConnectButton(Canvas s) {
        super("INTERACT", s, new PVector(180,10), null);
        shape = sketch.loadShape("src/main/resources/buttons/CONNECT.svg");
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

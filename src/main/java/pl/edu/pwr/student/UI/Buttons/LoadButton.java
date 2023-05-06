package pl.edu.pwr.student.UI.Buttons;

import pl.edu.pwr.student.UI.Canvas;
import processing.core.PVector;

/**
 * Represents a button for creating new elements on the canvas.
 * Extends the abstract Button class.
 */
public class LoadButton extends Button {

    /**
     * Constructs a new LoadButton object.
     *
     * @param s The Processing sketch that this button is a part of.
     */
    public LoadButton(Canvas s) {
        super(s, "LOAD", new PVector(310,10));
    }

    /**
     * Handles the click event of the LoadButton element.
     * Overrides the click method of the Button class.
     */
    @Override
    public void click() {
        //TODO: load
    }
}

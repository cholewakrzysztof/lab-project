package pl.edu.pwr.student.UI.Buttons;

import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.Utility.FileManagement.DataWriter;
import processing.core.PVector;

import java.io.IOException;

/**
 * Represents a button for creating new elements on the canvas.
 * Extends the abstract Button class.
 */
public class SaveButton extends Button {

    /**
     * Constructs a new SaveButton object.
     *
     * @param s The Processing sketch that this button is a part of.
     */
    public SaveButton(Canvas s) {
        super(s, "SAVE", new PVector(250,10));
    }

    /**
     * Handles the click event of the SaveButton element.
     * Overrides the click method of the Button class.
     */
    @Override
    public void click() {
        if (sketch.getElements().size() <= 0){
            sketch.showPopup("Nothing to save!");
            return;
        }

        try {
            DataWriter.saveToFile(sketch, sketch.getDirectory());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

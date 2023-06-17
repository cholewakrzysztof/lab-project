package pl.edu.pwr.student.UI.Buttons;

import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.Utility.FileManagement.DataReader;
import processing.core.PVector;

/**
 * Represents a button for creating new elements on the {@link Canvas}.
 * Extends the abstract {@link Button} class.
 */
public class LoadButton extends Button {

    /**
     * Constructs a new {@link LoadButton} object.
     *
     * @param s The {@link Canvas} that this button is a part of.
     */
    public LoadButton(Canvas s) {
        super(s, "LOAD", new PVector(310,10));
    }

    /**
     * Handles the click event of the {@link LoadButton} element.
     * Overrides the click method of the {@link Button} class.
     */
    @Override
    public void click() {
        try {
            DataReader.readFromFile(sketch.getFile(), sketch);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

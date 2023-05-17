package pl.edu.pwr.student.UI.Buttons;

import pl.edu.pwr.student.IO.UserUsable;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.UiElement;
import pl.edu.pwr.student.Utility.FileManagement.DataWriter;
import processing.core.PVector;

import java.io.File;
import java.io.IOException;

/**
 * Represents a button for creating new elements on the canvas.
 * Extends the abstract Button class.
 */
public class AddButton extends Button {

    /**
     * Constructs a new SaveButton object.
     *
     * @param s The Processing sketch that this button is a part of.
     */
    public AddButton(Canvas s) {
        super(s, "ADD", new PVector(370,10));
    }

    /**
     * Handles the click event of the SaveButton element.
     * Overrides the click method of the Button class.
     */
    @Override
    public void click() throws IOException {
        for (UiElement ui : sketch.getElements()) {
            if (ui.uiElem instanceof UserUsable) {
                sketch.showPopup("You cannot have user usable element in the compound gate!");
                return;
            }
        }
        //TODO: save in local storage
        DataWriter.safeToFileCompoundGate(sketch,new File("gates"),"example","example");
    }
}

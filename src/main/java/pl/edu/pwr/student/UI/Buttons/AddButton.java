package pl.edu.pwr.student.UI.Buttons;

import pl.edu.pwr.student.IO.UserUsable;
import pl.edu.pwr.student.UI.Blocks.Drawable;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.Utility.FileManagement.DataWriter;
import processing.core.PVector;
import uibooster.model.Form;

import java.io.IOException;
import java.util.List;

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
        List<Drawable> elem = sketch.getElements();
        if (elem.size() <= 0){
            sketch.showPopup("You cannot save empty compound gate!");
            return;
        }

        for (Drawable ui : elem) {
            if (ui.getGate() instanceof UserUsable) {
                sketch.showPopup("You cannot have user usable element in the compound gate!");
                return;
            }
        }
        Form form = sketch.saveCompoundGateDialog();
        DataWriter.saveToFileCompoundGate(sketch, form.getByLabel("Name of the gate").asString(), form.getByLabel("Message for the gate").asString());
    }
}

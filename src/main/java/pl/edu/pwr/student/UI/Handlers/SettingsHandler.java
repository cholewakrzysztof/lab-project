package pl.edu.pwr.student.UI.Handlers;

import pl.edu.pwr.student.UI.Canvas;
import uibooster.model.ListElement;

/**
 * Class responsible for handling settings of the application
 */
public class SettingsHandler {

    /**
     * Private constructor to prevent creating instances of this class
     */
    private SettingsHandler() {}

    /**
     * Sets settings for the sketch
     * @param sketch - sketch to set settings for
     */
    public static void settings(Canvas sketch) {
        sketch.size(1000, 1000);
        //TODO: make it added automatically (created new gates by user are now a problem)
        sketch.form = sketch.booster
                .createForm("Gates")
                .addList("Select Gate",
                        new ListElement("AND", null,""),
                        new ListElement("NAND", null,""),
                        new ListElement("OR", null, ""),
                        new ListElement("NOR", null,""),
                        new ListElement("XOR", null,""),
                        new ListElement("XNOR", null,""),
                        new ListElement("NOT", null, ""),
                        new ListElement("SPEAKER", null, ""),
                        new ListElement("LED", null, ""),
                        new ListElement("SWITCH", null, ""),
                        new ListElement("CLOCK", null, ""),
                        new ListElement("DELAY", null, "")
                ).run().hide();

        sketch.booster.createNotification(
                "Started",
                "Gates-Simulation");
    }
}

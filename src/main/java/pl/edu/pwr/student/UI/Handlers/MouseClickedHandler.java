package pl.edu.pwr.student.UI.Handlers;


import pl.edu.pwr.student.Gates.BasicGates.SingleInput.Delay;
import pl.edu.pwr.student.IO.Input.Clock;
import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.IO.Output.Speaker;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.UiElement;
import processing.core.PVector;
import uibooster.model.Form;

/**
 * Handles mouse clicked event
 */
public class MouseClickedHandler {

    /**
     * Private constructor to prevent creating instances of this class
     */
    private MouseClickedHandler() {}

    /**
     * Handles mouse clicked event
     * @param sketch - sketch to handle mouse clicked event for
     */
    public static void mouseClicked(Canvas sketch){
        if (sketch.state == 0) {
            for (UiElement g : sketch.elements) {
                if(g.over(new PVector(sketch.mouseX, sketch.mouseY))){
                    switch (g.elName) {
                        case "SWITCH" -> ((Switch) g.uiElem).toggle();
                        case "LED" -> g.color = sketch.booster.showColorPicker("Choose color of LED", "Color picking");
                        case "CLOCK" -> {
                            Form temp = sketch.booster.createForm("Clock")
                                    .addSlider("On time", 100, 10000, 1000, 10000, 1000)
                                    .addSlider("Off time", 100, 10000, 1000, 10000, 1000)
                                    .show();
                            ((Clock) g.uiElem).setIntervals(
                                    temp.getByLabel("On time").asInt(),
                                    temp.getByLabel("Off time").asInt()
                            );
                        }
                        case "DELAY" -> {
                            Form temp = sketch.booster.createForm("Delay")
                                    .addSlider("Delay time", 100, 10000, 1000, 10000, 1000)
                                    .show();
                            ((Delay) g.uiElem).setDelay(
                                    temp.getByLabel("Delay time").asInt()
                            );
                        }
                        case "SPEAKER" -> {
                            Form temp = sketch.booster.createForm("SPEAKER")
                                    .addSlider("Frequency", 100, 10000, 200, 10000, 1000)
                                    .show();
                            ((Speaker) g.uiElem).setFrequency(
                                    temp.getByLabel("Frequency").asInt()
                            );
                        }
                    }
                    break;
                }
            }
        }
    }
}

package pl.edu.pwr.student.UI.Handlers;

import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.UiElement;
import processing.core.PVector;

/**
 * Handles mouse clicked event
 */
public class MouseClickedHandler {
    /**
     * Handles mouse clicked event
     * @param sketch - sketch to handle mouse clicked event for
     */
    public static void mouseClicked(Canvas sketch){
        if (sketch.state == 0) {
            for (UiElement g : sketch.elements) {
                if(g.over(new PVector(sketch.mouseX, sketch.mouseY))){
                    switch (g.elName) {
                        case "SWITCH":
                            ((Switch)g.uiElem).toggle();
                            break;
                        case "LED":
                            g.color = sketch.booster.showColorPicker("Choose color of LED", "Color picking");
                            break;
                    }
                }
            }
        }
    }
}

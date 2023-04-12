package pl.edu.pwr.student.UI.Handlers;

import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.UiElement;
import processing.core.PVector;

public class MouseClickedHandler {
    public static void mouseClicked(Canvas sketch){
        if (sketch.state == 0) {
            for (UiElement g : sketch.elements) {
                if(g.over(new PVector(sketch.mouseX, sketch.mouseY))){
                    if (g.elName == "SWITCH"){
                        ((Switch)g.uiElem).toggle();
                    }
                    break;
                }
            }
        }
    }
}

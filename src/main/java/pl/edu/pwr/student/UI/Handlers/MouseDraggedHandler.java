package pl.edu.pwr.student.UI.Handlers;

import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.UiElement;
import pl.edu.pwr.student.Utility.ShapeLoader;
import processing.core.PVector;

public class MouseDraggedHandler {
    public static void mouseDragged(Canvas sketch){
        if (sketch.mousePressed && (sketch.mouseButton == sketch.LEFT)) {
            if (sketch.state == 2) {
                for (UiElement g : sketch.elements) {
                    if(g.over(new PVector(sketch.mouseX, sketch.mouseY))){
                        g.position = new PVector(sketch.mouseX - 256 * ShapeLoader.scale, sketch.mouseY- 256 * ShapeLoader.scale);
                        break;
                    }
                }
            }
        }
    }
}

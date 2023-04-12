package pl.edu.pwr.student.UI.Handlers;

import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.Utility.ShapeLoader;
import processing.core.PVector;

public class MouseDraggedHandler {
    public static void mouseDragged(Canvas sketch){
        if (sketch.state == 0 && sketch.selectedElement != null) {
            sketch.selectedElement.position = new PVector(sketch.mouseX - 256 * ShapeLoader.scale, sketch.mouseY- 256 * ShapeLoader.scale);
        }
    }
}

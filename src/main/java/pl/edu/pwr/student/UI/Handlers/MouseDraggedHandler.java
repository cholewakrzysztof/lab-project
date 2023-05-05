package pl.edu.pwr.student.UI.Handlers;

import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.Utility.ShapeLoader;
import processing.core.PVector;

import java.util.Arrays;

/**
 * Handles mouse dragged event
 */
public class MouseDraggedHandler {

    /**
     * Private constructor to prevent creating instances of this class
     */
    private MouseDraggedHandler() {}

    /**
     * Handles mouse dragged event
     * @param sketch - sketch to handle mouse dragged event for
     */
    public static void mouseDragged(Canvas sketch){
        if (sketch.state == 0) {
            if (sketch.selectedElement != null) {
                sketch.selectedElement.position = new PVector(
                        (sketch.mouseX+sketch.offset.x) / ShapeLoader.scale - ShapeLoader.size/2f,
                        (sketch.mouseY+sketch.offset.y) / ShapeLoader.scale - ShapeLoader.size/2f
                );
            } else {
                if (sketch.startingMousePosition == null) {
                    sketch.startingMousePosition = new PVector(sketch.mouseX, sketch.mouseY);
                } else {
                    sketch.offset.x = sketch.tempOffset.x + (sketch.startingMousePosition.x - sketch.mouseX) / ShapeLoader.scale;
                    sketch.offset.y = sketch.tempOffset.y + (sketch.startingMousePosition.y - sketch.mouseY) / ShapeLoader.scale;
                }
            }
        }
    }
}

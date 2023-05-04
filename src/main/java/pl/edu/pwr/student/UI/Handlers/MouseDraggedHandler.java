package pl.edu.pwr.student.UI.Handlers;

import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.Utility.ShapeLoader;
import processing.core.PVector;

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
        if (sketch.state == 0 && sketch.selectedElement != null) {
            sketch.selectedElement.position = new PVector(
                    sketch.mouseX / ShapeLoader.scale - ShapeLoader.size/2f,
                    sketch.mouseY / ShapeLoader.scale - ShapeLoader.size/2f
            );
        }
    }
}

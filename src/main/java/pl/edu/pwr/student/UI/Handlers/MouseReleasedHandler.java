package pl.edu.pwr.student.UI.Handlers;

import pl.edu.pwr.student.UI.Canvas;

/**
 * Handles mouse released event
 */
public class MouseReleasedHandler {

    /**
     * Private constructor to prevent creating instances of this class
     */
    private MouseReleasedHandler() {}

    /**
     * Handles mouse released event
     * @param sketch - sketch to handle mouse released event for
     */
    public static void mouseReleased(Canvas sketch){
        if (sketch.state == 0) {
            sketch.selectedElement = null;
            if (sketch.startingMousePosition != null){
                sketch.tempOffset.x = sketch.offset.x;
                sketch.tempOffset.y = sketch.offset.y;
                sketch.startingMousePosition = null;
            }
        }
    }
}

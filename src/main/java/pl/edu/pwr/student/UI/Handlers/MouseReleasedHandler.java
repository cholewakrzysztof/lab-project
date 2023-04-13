package pl.edu.pwr.student.UI.Handlers;

import pl.edu.pwr.student.UI.Canvas;

/**
 * Handles mouse released event
 */
public class MouseReleasedHandler {
    /**
     * Handles mouse released event
     * @param sketch - sketch to handle mouse released event for
     */
    public static void mouseReleased(Canvas sketch){
        if (sketch.state == 0) {
            sketch.selectedElement = null;
        }
    }
}

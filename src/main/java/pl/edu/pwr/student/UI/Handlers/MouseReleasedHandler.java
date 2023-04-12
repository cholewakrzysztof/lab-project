package pl.edu.pwr.student.UI.Handlers;

import pl.edu.pwr.student.UI.Canvas;

public class MouseReleasedHandler {
    public static void mouseReleased(Canvas sketch){
        if (sketch.state == 0) {
            sketch.selectedElement = null;
        }
    }
}

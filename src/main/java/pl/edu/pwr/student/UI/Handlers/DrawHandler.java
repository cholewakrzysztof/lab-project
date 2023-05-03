package pl.edu.pwr.student.UI.Handlers;

import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.UiElement;

/**
 * Handles draw event
 */
public class DrawHandler {

    /**
     * Private constructor to prevent creating instances of this class
     */
    private DrawHandler() {}

    /**
     * Handles draw event
     * @param sketch - sketch to handle draw event for
     */
    public static void draw(Canvas sketch) {
        sketch.background(255);

        for (int i = 0; i < sketch.buttons.size(); i++) {
            sketch.buttons.get(i).run();
        }

        for (UiElement g : sketch.elements) {
            g.run();
        }
        if (sketch.elements.isEmpty()) {
            sketch.fill(0);
            sketch.textAlign(sketch.CENTER);
            sketch.textSize(32);
            sketch.text("Select gate and place it", sketch.width / 2, sketch.height / 2);
        }
    }
}

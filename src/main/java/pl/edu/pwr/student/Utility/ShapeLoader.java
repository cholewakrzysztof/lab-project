package pl.edu.pwr.student.Utility;

import pl.edu.pwr.student.UI.Canvas;
import processing.core.PShape;
import java.util.HashMap;

import static processing.core.PApplet.*;

/**
 * Loads all shapes from resources/gates/ directory
 */
public class ShapeLoader {

    /**
     * Private constructor to prevent creating instances of this class
     */
    private ShapeLoader() {}

    /**
     * Map of all shapes
     */
    private static final HashMap<String, PShape> shapes = new HashMap<>();

    /**
     * Map of all buttons
     */
    private static final HashMap<String, PShape> buttons = new HashMap<>();

    /**
     * Scale of all shapes
     */
    public static float scale = 1f;

    /**
     * Size of all shapes
     */
    public static final int size = 50;

    /**
     * Loads all shapes from resources/gates/ directory to {@link pl.edu.pwr.student.UI.Canvas}
     * @param sketch Canvas object
     */
    public static void loadShapes(Canvas sketch){
        buttons.put("CONNECT", sketch.loadShape("buttons/CONNECT.svg"));
        buttons.put("CREATE", sketch.loadShape("buttons/CREATE.svg"));
        buttons.put("DELETE", sketch.loadShape("buttons/DELETE.svg"));
        buttons.put("INTERACT", sketch.loadShape("buttons/INTERACT.svg"));
        buttons.put("SAVE", sketch.loadShape("buttons/SAVE.svg"));
        buttons.put("LOAD", sketch.loadShape("buttons/LOAD.svg"));
        buttons.put("ADD", sketch.loadShape("buttons/ADD.svg"));

        shapes.put("AND", sketch.loadShape("elements/AND.svg"));
        shapes.put("OR", sketch.loadShape("elements/OR.svg"));
        shapes.put("XOR", sketch.loadShape("elements/XOR.svg"));
        shapes.put("NOT", sketch.loadShape("elements/NOT.svg"));
        shapes.put("NAND", sketch.loadShape("elements/NAND.svg"));
        shapes.put("NOR", sketch.loadShape("elements/NOR.svg"));
        shapes.put("XNOR", sketch.loadShape("elements/XNOR.svg"));
        shapes.put("CLOCK", sketch.loadShape("elements/CLOCK.svg"));
        shapes.put("LED", sketch.loadShape("elements/LED.svg"));
        shapes.put("SWITCH-TRUE", sketch.loadShape("elements/SWITCH-TRUE.svg"));
        shapes.put("SWITCH-FALSE", sketch.loadShape("elements/SWITCH-FALSE.svg"));
        shapes.put("SPEAKER", sketch.loadShape("elements/SPEAKER.svg"));
        shapes.put("DELAY", sketch.loadShape("elements/DELAY.svg"));
    }

    /**
     * Resizes all shapes
     */
    public static void resize(){
        for (PShape p : shapes.values()) {
            p.resetMatrix();
            p.scale(scale);
        }
    }

    /**
     * Returns shape by name
     * @param shape name of shape
     * @param rotation rotation of shape
     * @param x x position of shape
     * @param y y position of shape
     * @return PShape object
     */
    public static PShape getShape(String shape, float rotation, float x, float y){
        PShape temp = shapes.get(shape);

        if (temp != null) {
            temp.resetMatrix();
            temp.rotate(rotation*PI);
            temp.translate(x, y);
            temp.scale(scale);
            return temp;
        }
        return shapes.get("AND");
    }

    /**
     * Returns button shape by name
     * @param button name of shape
     * @return PShape object
     */
    public static PShape getButton(String button){
        return buttons.get(button);
    }

    /**
     * Changes scale of all shapes
     * @param direction higher than 0 for zoom in, lower than 0 for zoom out
     */
    public static void scale(int direction) {
        float temp = scale - direction * 0.1f;
        if (temp > 0.2f && temp < 3f) scale = temp;


//        resize();
    }
}

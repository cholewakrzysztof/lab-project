package pl.edu.pwr.student.Utility;

import processing.core.PApplet;
import processing.core.PShape;
import java.util.HashMap;

/**
 * Loads all shapes from resources/gates/ directory
 */
public class ShapeLoader {

    /**
     * Map of all shapes
     */
    private static HashMap<String, PShape> shapes = new HashMap<String, PShape>();

    /**
     * Scale of all shapes
     */
    public static float scale = 0.1f;

    /**
     * Loads all shapes from resources/gates/ directory
     * @param sketch PApplet object
     */
    public static void loadShapes(PApplet sketch){
        //dumb way cus its working in JAR file

        shapes.put("CONNECT", sketch.loadShape("buttons/CONNECT.svg"));
        shapes.put("CREATE", sketch.loadShape("buttons/CREATE.svg"));
        shapes.put("DELETE", sketch.loadShape("buttons/DELETE.svg"));
        shapes.put("INTERACT", sketch.loadShape("buttons/INTERACT.svg"));
//        shapes.put("SAVE", sketch.loadShape("buttons/SAVE.svg"));
//        shapes.put("LOAD", sketch.loadShape("buttons/LOAD.svg"));
//        shapes.put("RESET", sketch.loadShape("buttons/RESET.svg"));
//        shapes.put("START", sketch.loadShape("buttons/START.svg"));
//        shapes.put("STOP", sketch.loadShape("buttons/STOP.svg"));
//        shapes.put("SPEEDUP", sketch.loadShape("buttons/SPEED.svg"));
//        shapes.put("SPEEDDOWN", sketch.loadShape("buttons/SPEED2.svg"));
        shapes.put("AND", sketch.loadShape("elements/AND.svg"));
        shapes.put("OR", sketch.loadShape("elements/OR.svg"));
        shapes.put("XOR", sketch.loadShape("elements/XOR.svg"));
        shapes.put("NOT", sketch.loadShape("elements/NOT.svg"));
        shapes.put("NAND", sketch.loadShape("elements/NAND.svg"));
        shapes.put("NOR", sketch.loadShape("elements/NOR.svg"));
        shapes.put("XNOR", sketch.loadShape("elements/XNOR.svg"));
        shapes.put("CLOCK", sketch.loadShape("elements/CLOCK.svg"));
        shapes.put("LED", sketch.loadShape("elements/LED.svg"));
        shapes.put("SWITCH", sketch.loadShape("elements/SWITCH.svg"));
        shapes.put("SWITCH-TRUE", sketch.loadShape("elements/SWITCH-TRUE.svg"));
        shapes.put("SWITCH-FALSE", sketch.loadShape("elements/SWITCH-FALSE.svg"));
        shapes.put("SPEAKER", sketch.loadShape("elements/SPEAKER.svg"));

        resize(scale);
    }

    /**
     * Resizes all shapes
     * @param s scale
     */
    public static void resize(float s){
        if (s != scale){
            scale = s;
        }
        //TODO: jeżeli będzie przeskalowany cała canva to trzeba wywoływać przeskalowanie każdego elementu
        for (PShape p : shapes.values()) {
            p.scale(scale);
        }
    }

    /**
     * Returns shape by name
     * @param shape name of shape
     * @return PShape object
     */
    public static PShape getShape(String shape){
        return shapes.get(shape);
    }
}

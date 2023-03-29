package pl.edu.pwr.student.Utility;

import processing.core.PApplet;
import processing.core.PShape;

import java.io.File;
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
        File dir = new File("src/main/resources/gates/");
        File[] files = dir.listFiles();

        assert files != null;
        for (File file : files) {
            shapes.put(file.getName().replaceFirst("[.][^.]+$", ""), sketch.loadShape(file.getAbsolutePath()));
        }

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

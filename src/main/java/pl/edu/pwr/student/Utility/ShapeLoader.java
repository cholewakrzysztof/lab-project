package pl.edu.pwr.student.Utility;

import processing.core.PApplet;
import processing.core.PShape;

import java.io.File;
import java.util.HashMap;

public class ShapeLoader {
    private static HashMap<String, PShape> shapes = new HashMap<String, PShape>();

    public ShapeLoader(){}

    public static void loadShapes(PApplet sketch){
        File dir = new File("src/main/resources/gates/");
        File[] files = dir.listFiles();

        assert files != null;
        for (File file : files) {
            shapes.put(file.getName().replaceFirst("[.][^.]+$", ""), sketch.loadShape(file.getAbsolutePath()));
        }
    }
    public static PShape getShape(String shape){
        return shapes.get(shape);
    }
}

package pl.edu.pwr.student.Utility.FileManagement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pl.edu.pwr.student.Gates.BasicGates.Compoundable;
import pl.edu.pwr.student.Gates.BasicGates.MultipleInput.AND;
import pl.edu.pwr.student.UI.Blocks.Drawable;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.Blocks.UiElement;
import processing.core.PVector;
import uibooster.UiBooster;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class DataWriterTest {
    Canvas canvas;
    static String path = "PlikTestowy.gss";
    static String directory = "";


    @BeforeEach
    void setUp() {
        try {
            canvas = new Canvas();
        } catch (Exception e) {
            new UiBooster().showException(
                    "An error occurred",
                    "Exception message",
                    e
            );
        }

        Drawable drawable = new UiElement("AND",canvas,new PVector(0f,0f),new AND());
        canvas.addElement(drawable);
    }

    /**
     * Clear project folder after tests
     */
    @AfterAll
    static void clearFolder(){
        File f = new File(path);
        if(f.exists())
            f.delete();
    }

    /**
     * Test saving canva to file from path
     * @throws IOException Throw simple IO exception
     */
    @Test
    void saveToFile() throws IOException {
        DataWriter.saveToFile(canvas, new File(directory));
        Scanner myReader = new Scanner(new File(path));

        String json  = myReader.nextLine();

        assertEquals("{\"message\":null,\"gateType\":\"AND\",\"position\":{\"x\":290.0,\"y\":172.0,\"z\":0.0},\"elName\":\"AND\",\"outputs\":[],\"hashCode\":1787826193,\"color\":null,\"logic\":null}",json);
    }

    /**
     * Test generating single JSON representation of AND gate
     */
    @Test
    void generateJSONfromUIElement_AND() {
        Compoundable gate = new AND();
        UiElement uiElement = new UiElement("AND",canvas,new PVector(0f,0f),gate);
        String jsonString;
        try{
            jsonString = DataWriter.generateJSONFromUIElement(uiElement);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertTrue(jsonString.contains("elName\":\"AND"));
    }
}
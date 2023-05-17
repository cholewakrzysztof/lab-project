package pl.edu.pwr.student.Utility.FileManagement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pl.edu.pwr.student.Gates.BasicGates.MultipleInput.AND;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.UiAvailable;
import pl.edu.pwr.student.UI.UiElement;
import processing.core.PVector;
import uibooster.UiBooster;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class DataWriterTest {
    Canvas canvas;
    static String path = "PlikTestowy.gss";


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

        UiElement uiElement1 = new UiElement("AND",canvas,new PVector(0f,0f),new AND());
        canvas.getElements().add(uiElement1);
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
        DataWriter.saveToFile(canvas, new File(path));
        Scanner myReader = new Scanner(new File(path));

        Pattern pattern = Pattern.compile("\\{\"position\":\\{\"x\":0.0,\"y\":0.0,\"z\":0.0},\"elName\":\"AND\",\"outputs\":\\[],\"hashCode\":\\d*,\"color\":null}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(myReader.next());

        assertTrue(matcher.matches());
    }

    /**
     * Test generating single JSON representation of AND gate
     */
    @Test
    void generateJSONfromUIElement_AND() {
        UiAvailable gate = new AND();
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
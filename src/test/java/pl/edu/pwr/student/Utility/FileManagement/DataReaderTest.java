package pl.edu.pwr.student.Utility.FileManagement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pwr.student.Gates.BasicGates.MultipleInput.AND;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.UiElement;
import processing.core.PVector;
import uibooster.UiBooster;

import java.io.File;
import java.io.FileWriter;

import static org.junit.jupiter.api.Assertions.*;

class DataReaderTest {
    Canvas canvas;
    UiElement uiElement;
    static String path = "PlikTestowy.gss";

    /**
     * Create new canva and add one basic gate AND
     */
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

        uiElement = new UiElement("AND",canvas,new PVector(0f,0f),new AND());
        canvas.getElements().add(uiElement);
    }
    @AfterAll
    static void clearFolder(){
        File f = new File(path);
        if(f.exists())
            f.delete();
    }

    /**
     * Safe one gate in file then read from this file
     * @throws Exception Throw when something with file gone wrong
     */
    @Test
    void readFromFile() throws Exception {
        FileWriter fileWriter = new FileWriter(path);
        fileWriter.write("{\"position\":{\"x\":0.0,\"y\":0.0,\"z\":0.0},\"elName\":\"AND\",\"outputs\":[],\"hashCode\":120345,\"color\":null}");
        fileWriter.close();
        canvas.getElements().clear();
        DataReader.readFromFile(new File(path),canvas);
        assertEquals(1, canvas.getElements().size());
    }

    /**
     * Test rebuilding UI element from generated JSON string of gate(AND)
     * @throws Exception Throw when something wrong happens
     */
    @Test
    void generateUIElementFormJSON() throws Exception {
        String jsonString = DataWriter.generateJSONfromUIElement(uiElement);
        UiElement element = DataReader.generateUIElementFromJSON(jsonString,canvas);

        assertSame(element.uiElem.getClass(), AND.class);
    }
}
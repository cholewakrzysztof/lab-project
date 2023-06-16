package pl.edu.pwr.student.Utility.FileManagement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pwr.student.Gates.BasicGates.MultipleInput.AND;
import pl.edu.pwr.student.UI.Blocks.Drawable;
import pl.edu.pwr.student.UI.Blocks.UiElement;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.Creator.GateCreator;
import processing.core.PVector;
import uibooster.UiBooster;

import java.io.File;
import java.io.FileWriter;

import static org.junit.jupiter.api.Assertions.*;

class DataReaderTest {
    Canvas canvas;
    Drawable drawableElement;
    static String path = "PlikTestowy.gss";

    /**
     * Create new canvas and add one basic gate AND
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

        drawableElement = new UiElement("AND",canvas,new PVector(0f,0f), GateCreator.create("AND"));
        canvas.addElement(drawableElement);
    }

    /**
     * After all tests delete files created within tests
     */
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
        fileWriter.write("{\"message\":null,\"gateType\":\"AND\",\"position\":{\"x\":290.0,\"y\":172.0,\"z\":0.0},\"elName\":\"AND\",\"outputs\":[],\"hashCode\":1787826193,\"color\":null,\"logic\":null}");
        fileWriter.close();
        canvas.clear();
        DataReader.readFromFile(new File(path),canvas);
        assertEquals(1, canvas.getElements().size());
        assertTrue(canvas.getElements().get(0).getGate() instanceof AND);
    }
}
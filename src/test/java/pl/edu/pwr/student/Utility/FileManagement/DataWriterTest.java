package pl.edu.pwr.student.Utility.FileManagement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pl.edu.pwr.student.Gates.BasicGates.Compoundable;
import pl.edu.pwr.student.Gates.BasicGates.MultipleInput.AND;
import pl.edu.pwr.student.Gates.CompoundGate;
import pl.edu.pwr.student.IO.Input.SignalSender;
import pl.edu.pwr.student.IO.Output.SignalReceiver;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.UiAvailable;
import pl.edu.pwr.student.UI.UiElement;
import processing.core.PVector;
import uibooster.UiBooster;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

class DataWriterTest {
        Canvas canvas;

    @BeforeEach
    void setUp() {
        HashSet<Compoundable> basicGates = new HashSet<>();
        HashSet<CompoundGate> compoundGates = new HashSet<>();
        HashMap<String, CompoundGate> savedCompoundGates = new HashMap<>();
        HashSet<SignalSender> userInputs = new HashSet<>();
        HashSet<SignalReceiver> systemOutputs = new HashSet<>();


        try {
            canvas = new Canvas(basicGates, compoundGates, savedCompoundGates, userInputs, systemOutputs);
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
        File f = new File("plik.txt");
        f.delete();
    }

    /**
     * Test saving canva to file plik.txt
     * @throws IOException Throw simple IO exception
     */
    @Test
    void safeToFile() throws IOException {
        DataWriter.safeToFile(canvas,"plik.txt");
        Scanner myReader = new Scanner(new File("plik.txt"));
        assertEquals("{\"position\":{\"x\":0.0,\"y\":0.0,\"z\":0.0},\"elName\":\"AND\",\"outputs\":[],\"color\":null}",myReader.next());
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
            jsonString = DataWriter.generateJSONfromUIElement(uiElement);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertTrue(jsonString.contains("elName\":\"AND"));
    }
}
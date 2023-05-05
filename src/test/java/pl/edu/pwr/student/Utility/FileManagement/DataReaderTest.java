package pl.edu.pwr.student.Utility.FileManagement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pwr.student.Gates.BasicGates.Compoundable;
import pl.edu.pwr.student.Gates.BasicGates.MultipleInput.AND;
import pl.edu.pwr.student.Gates.CompoundGate;
import pl.edu.pwr.student.IO.Input.SignalSender;
import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.IO.Output.LED;
import pl.edu.pwr.student.IO.Output.SignalReceiver;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.UiElement;
import processing.core.PVector;
import uibooster.UiBooster;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class DataReaderTest {
    Canvas canvas;
    UiElement uiElement;

    /**
     * Create new canva and add one basic gate AND
     */
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

        uiElement = new UiElement("AND",canvas,new PVector(0f,0f),new AND());
        canvas.elements.add(uiElement);
        canvas.basicGates.add(new AND());
    }
    @AfterAll
    static void clearFolder(){
        File f = new File("plik.txt");
        f.delete();
    }

    /**
     * Safe one gate in file then read from this file
     * @throws Exception Throw when something with file gone wrong
     */
    @Test
    void readFromFile() throws Exception {
        FileWriter fileWriter = new FileWriter("plik.txt");
        fileWriter.write("{\"position\":{\"x\":0.0,\"y\":0.0,\"z\":0.0},\"elName\":\"AND\",\"outputs\":[],\"inputs\":[],\"color\":null}");
        fileWriter.close();
        canvas.elements.clear();
        DataReader.readFromFile("plik.txt",canvas);
        assertEquals(1, canvas.elements.size());
    }

    /**
     * Test rebuilding UI element from generated JSON string of gate(AND)
     * @throws Exception Throw when something wrong happens
     */
    @Test
    void generateUIElementFormJSON() throws Exception {
        String jsonString = DataWriter.generateJSONfromUIElement(uiElement);
        UiElement element = DataReader.generateUIElementFormJSON(jsonString,canvas);

        assertSame(element.uiElem.getClass(), AND.class);
    }

    /**
     * Test update basicGates set
     */
    @Test
    void addToProperCanvasSetCaseBasicGate(){
        UiElement element = new UiElement("AND",canvas,new PVector(0f,0f),new AND());
        canvas.basicGates.clear();
        DataReader.addToProperCanvaSet(element.elName,canvas);
        assertEquals(1, canvas.basicGates.size());
    }
    /**
     * Test update systemOutputs set
     */
    @Test
    void addToProperCanvasSetCaseSystemOutputs(){
        UiElement element = new UiElement("LED",canvas,new PVector(0f,0f),new LED("",100));
        canvas.systemOutputs.clear();
        DataReader.addToProperCanvaSet(element.elName,canvas);
        assertEquals(1, canvas.systemOutputs.size());
    }
    /**
     * Test update systemInputs set
     */
    @Test
    void addToProperCanvasSetCaseSystemInputs(){
        UiElement element = new UiElement("SWITCH",canvas,new PVector(0f,0f),new Switch());
        canvas.userInputs.clear();
        DataReader.addToProperCanvaSet(element.elName,canvas);
        assertEquals(1, canvas.userInputs.size());
    }
    /**
     * Test sizes of other sets
     */
    @Test
    void addToProperCanvasSetCheckOtherSets(){
        UiElement element = new UiElement("SWITCH",canvas,new PVector(0f,0f),new Switch());
        canvas.userInputs.clear();
        canvas.systemOutputs.clear();
        canvas.basicGates.clear();
        DataReader.addToProperCanvaSet(element.elName,canvas);
        assertEquals(0, canvas.basicGates.size());
        assertEquals(0, canvas.systemOutputs.size());
        assertEquals(1, canvas.userInputs.size());
    }
}
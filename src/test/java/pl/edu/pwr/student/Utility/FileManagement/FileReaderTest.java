package pl.edu.pwr.student.Utility.FileManagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pwr.student.Gates.BasicGates.Compoundable;
import pl.edu.pwr.student.Gates.BasicGates.MultipleInput.AND;
import pl.edu.pwr.student.Gates.CompoundGate;
import pl.edu.pwr.student.IO.Input.SignalSender;
import pl.edu.pwr.student.IO.Output.SignalReceiver;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.UiElement;
import processing.core.PVector;
import uibooster.UiBooster;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderTest {
    Canvas canvas;
    UiElement uiElement;

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
    }
    @Test
    void readFromFile() {
    }

    @Test
    void generateUIElementFormJSON() throws Exception {
        String jsonString = FileWriter.generateJSONfromUIElement(uiElement);
        UiElement element = FileReader.generateUIElementFormJSON(jsonString,canvas);

        assertFalse(element.uiElem.getState());
    }
}
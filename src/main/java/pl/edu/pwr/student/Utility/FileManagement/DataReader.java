package pl.edu.pwr.student.Utility.FileManagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.pwr.student.Gates.BasicGates.MultipleInput.*;
import pl.edu.pwr.student.Gates.BasicGates.SingleInput.Delay;
import pl.edu.pwr.student.Gates.BasicGates.SingleInput.NOT;
import pl.edu.pwr.student.IO.Input.Clock;
import pl.edu.pwr.student.IO.Input.Switch;
import pl.edu.pwr.student.IO.Output.LED;
import pl.edu.pwr.student.IO.Output.SignalReceiver;
import pl.edu.pwr.student.IO.Output.Speaker;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.UiAvailable;
import pl.edu.pwr.student.UI.UiElement;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Class responsible for reading data from files and generating HashSet of UI elements
 */
public class DataReader {
    /**
     *
     * @param path Path to file with JSON string of UI Elements
     * @param canvas Canvas where all UI elements will be saved
     * @throws Exception when something wrong with path
     */
    public static void readFromFile(String path,final Canvas canvas ) throws Exception {
        Scanner myReader = new Scanner(new File(path));
        canvas.userInputs.clear();
        canvas.systemOutputs.clear();
        canvas.basicGates.clear();
        canvas.getElements().clear();


        HashMap<Integer,UiElement> elements = new HashMap<>();
        HashMap<Integer,JSONAvailable> jsonAvailables = new HashMap<>();

        while(myReader.hasNext()){
            String json = myReader.next();
            UiElement uiElement = DataReader.generateUIElementFormJSON(json,canvas);
            JSONAvailable jsonAvailable = generateJSONAvailableFromJSON(json);
            elements.put(jsonAvailable.hashCode,uiElement);
            jsonAvailables.put(jsonAvailable.hashCode,jsonAvailable);
            addToProperCanvaSet(uiElement.elName,canvas);
        }

        for(Map.Entry<Integer, JSONAvailable> entry : jsonAvailables.entrySet()) {
            Integer key = entry.getKey();
            JSONAvailable value = entry.getValue();
            UiElement el = elements.get(key);
            for (Integer hashCode: value.outputs) {
                for (Map.Entry<Integer, UiElement> candidate : elements.entrySet()) {
                    if(Objects.equals(hashCode, candidate.getKey())){
                        el.uiElem.connection((SignalReceiver) candidate.getValue().uiElem);
                    }
                }
            }
        }
    }
    private static SignalReceiver getSignalRecieverFromCanva(Integer index, final Canvas canvas){
        Integer currentIndex = 0;
        UiElement uiElement = null;
        for (UiElement element: canvas.getElements()) {
            if(currentIndex==index){
                uiElement = element;
                break;
            }
            currentIndex++;
        }
        return (SignalReceiver) uiElement.uiElem;
    }

    /**
     * Update one of sets of canva (basicGates, userInputs etc.)
     * @param elName Name of UI element
     * @param canvas Canva to update
     */
    public static void addToProperCanvaSet(String elName,final Canvas canvas){
        switch (elName) {
            case "AND" -> {
                AND temp = new AND();
                canvas.basicGates.add(temp);
            }
            case "NAND" -> {
                NAND temp = new NAND();
                canvas.basicGates.add(temp);
            }
            case "OR" -> {
                OR temp = new OR();
                canvas.basicGates.add(temp);
            }
            case "NOR" -> {
                NOR temp = new NOR();
                canvas.basicGates.add(temp);
            }
            case "XOR" -> {
                XOR temp = new XOR();
                canvas.basicGates.add(temp);
            }
            case "XNOR" -> {
                XNOR temp = new XNOR();
                canvas.basicGates.add(temp);
            }
            case "NOT" -> {
                NOT temp = new NOT();
                canvas.basicGates.add(temp);
            }
            case "SPEAKER" -> {
                Speaker temp = new Speaker();
                canvas.systemOutputs.add(temp);
            }
            case "LED" -> {
                LED temp = new LED("", 0);
                temp.toggle();
                canvas.systemOutputs.add(temp);
            }
            case "SWITCH" -> {
                Switch temp = new Switch();
                temp.toggle();
                canvas.userInputs.add(temp);
            }
            case "CLOCK" -> {
                Clock temp = new Clock(1000, 1000);
                temp.toggle();
                canvas.userInputs.add(temp);
            }
            case "DELAY" -> {
                Delay temp = new Delay(1000);
                canvas.userInputs.add(temp);
            }
        }
    }
    /**
     *
     * @param jsonString Raw JSON string of single JSONAvailable object
     * @param canvas Canvas connected with UI Element
     * @return new UiElement
     * @throws Exception when something wrong with jsonString
     */
    public static UiElement generateUIElementFormJSON(String jsonString, Canvas canvas) throws Exception {
        return new UiElement(DataReader.generateJSONAvailableFromJSON(jsonString),canvas);
    }
    private static JSONAvailable generateJSONAvailableFromJSON(String jsonString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString,JSONAvailable.class);
    }
}

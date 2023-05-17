package pl.edu.pwr.student.Utility.FileManagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.pwr.student.Gates.BasicGates.Compoundable;
import pl.edu.pwr.student.Gates.CompoundGate;
import pl.edu.pwr.student.IO.Output.*;
import pl.edu.pwr.student.UI.Canvas;
import pl.edu.pwr.student.UI.Creator.GateCreator;
import pl.edu.pwr.student.UI.UiAvailable;
import pl.edu.pwr.student.UI.UiElement;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Class responsible for reading data from files and generating HashSet of UI elements
 */
public class DataReader {
    /**
     * @param file File with JSON string of UI Elements
     * @param canvas Canvas where all UiElements will be saved
     * @throws Exception when something wrong with path
     */
    public static void readFromFile(File file, final Canvas canvas) throws Exception {
        if (file == null || !file.exists() || file.isDirectory()) {
            canvas.showPopup("File does not exist or path is wrong");
            return;
        }

        Scanner myReader = new Scanner(file);

        canvas.clear();

        HashMap<Integer, UiAvailable> gates = new HashMap<>();
        HashMap<Integer,JSONAvailable> jsonAvailableHashMap = new HashMap<>();

        while(myReader.hasNext()){
            String json = myReader.next();
            JSONAvailable jsonAvailable = generateJSONAvailableFromJSON(json);

            Integer id = jsonAvailable.hashCode;

            UiAvailable temp = GateCreator.create(jsonAvailable.elName);
            canvas.getElements().add(new UiElement(jsonAvailable.elName, canvas, jsonAvailable.position, temp));

            gates.put(id, temp);

            jsonAvailableHashMap.put(id, jsonAvailable);
        }

        for(Map.Entry<Integer, JSONAvailable> entry : jsonAvailableHashMap.entrySet()) {
            Integer key = entry.getKey();
            JSONAvailable value = entry.getValue();
            UiAvailable gate = gates.get(key);
            for (Integer hashCode: value.outputs) {
                for (Map.Entry<Integer, UiAvailable> candidate : gates.entrySet()) {
                    if(Objects.equals(hashCode, candidate.getKey())){
                        gate.connection((SignalReceiver) candidate.getValue());
                    }
                }
            }
        }
    }

    public static void readCompoundGateFromFile(File file, final Canvas canvas) throws IOException {
        if (file == null || !file.exists() || file.isDirectory()) {
            canvas.showPopup("File does not exist or path is wrong");
            return;
        }

        Scanner myReader = new Scanner(file);
        String message = myReader.next();

        HashMap<Integer, Compoundable> gates = new HashMap<>();
        HashMap<Integer,JSONAvailable> jsonAvailableHashMap = new HashMap<>();

        while(myReader.hasNext()){
            String json = myReader.next();
            JSONAvailable jsonAvailable = generateJSONAvailableFromJSON(json);

            Integer id = jsonAvailable.hashCode;
            gates.put(id, (Compoundable) GateCreator.create(jsonAvailable.elName));
            jsonAvailableHashMap.put(id, jsonAvailable);
        }

        for(Map.Entry<Integer, JSONAvailable> entry : jsonAvailableHashMap.entrySet()) {
            Integer key = entry.getKey();
            JSONAvailable value = entry.getValue();
            UiAvailable gate = gates.get(key);
            for (Integer hashCode: value.outputs) {
                for (Map.Entry<Integer, Compoundable> candidate : gates.entrySet()) {
                    if(Objects.equals(hashCode, candidate.getKey())){
                        gate.connection((SignalReceiver) candidate.getValue());
                    }
                }
            }
        }
        CompoundGate compoundGate = new CompoundGate(new HashSet<>(gates.values()));

        canvas.registerCompoundGate(file.getName().substring(0,file.getName().length()-4),message, compoundGate);
    }

    /**
     *
     * @param jsonString Raw JSON string of single JSONAvailable object
     * @param canvas Canvas connected with UI Element
     * @return UiElement
     * @throws Exception Throw when JSONAvailable object cannot be created
     */
    public static UiElement generateUIElementFormJSON(String jsonString, Canvas canvas) throws Exception {
        return new UiElement(DataReader.generateJSONAvailableFromJSON(jsonString),canvas);
    }

    /**
     * Generate JSONAvailable object from JSON string
     * @param jsonString Source string
     * @return  JSONAvailable object
     * @throws IOException Throw usually when fields don't match
     */
    private static JSONAvailable generateJSONAvailableFromJSON(String jsonString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString,JSONAvailable.class);
    }
}
